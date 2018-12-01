using MvvmFoundation.Wpf;
using Newtonsoft.Json;
using PrintAnywhere.Helper;
using PrintAnywhere.Models;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using System.Windows.Input;

namespace PrintAnywhere
{
    public class MainWindowViewModel
    {
        public ObservableCollection<FileList> Items { get; private set; }
        public ICommand _Print { get; private set; }
        public ICommand _RefreshFileList { get; private set; }
        public ICommand _RefreshToken { get; private set; }
        private static readonly HttpClient client = new HttpClient();


        private readonly Action _closeAction;
        private User _user { get; set; }
        public string _userName { get; private set; }
        public string _userPoint { get; private set; }
        private string _jwt;
        public FileList[] _filelist;


        public MainWindowViewModel(Action closeAction, string _jwt)
        {
            this._closeAction = closeAction;
            Items = new ObservableCollection<FileList>();

            this._jwt = _jwt;

            GetUserData();

            GetFileData();



            _Print = new RelayCommand<IEnumerable<object>>(PrintJob, CanShowSelected);
            _RefreshFileList = new RelayCommand<IEnumerable<object>>(RefreshFileList, CanShowSelected);
            _RefreshToken = new RelayCommand<IEnumerable<object>>(RefreshToken, CanShowSelected);

        }


        //todo
        public void GetUserData()
        {
            Task.Run(async () =>
            {
                string uri = "http://xdkyu02.cafe24.com/show.do";

                Console.WriteLine(_jwt);

                using (var client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Add("x-access-token", _jwt);
                    var response = await client.GetAsync(uri);
                    Console.WriteLine(response);
                    if (response.IsSuccessStatusCode)
                    {
                        var jsonresult = response.Content.ReadAsStringAsync().Result;
                        Console.WriteLine(jsonresult);
                        //deserialized 된 Json
                        var jsonParse = JsonConvert.DeserializeObject<UserResponse>(jsonresult);

                        _user = jsonParse.data.user;
                        _userName = _user.userName;
                        _userPoint = _user.userPoint;
                    }
                    else
                    {
                        Logout();

                    }
                }

            });

        }
        public void GetFileData()
        {
            Task.Run(async () =>
            {
                string uri = "http://xdkyu02.cafe24.com/fileList.do?sizeOfList=10";



                Console.WriteLine(_jwt);

                using (var client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Add("x-access-token", _jwt);

                    var response = await client.GetAsync(uri);
                    Console.WriteLine(response);
                    if (response.IsSuccessStatusCode)
                    {
                        var jsonresult = response.Content.ReadAsStringAsync().Result;
                        Console.WriteLine(jsonresult);
                        //deserialized 된 Json
                        var jsonParse = JsonConvert.DeserializeObject<FileResponse>(jsonresult);
                        _filelist = jsonParse.data.fileList;

                        foreach (FileList item in _filelist)
                        {
                            Items.Add(item);
                        }

                    }
                    else
                    {
                       

                    }
                }

            });

        }
       

        private bool Logout()
        {
            this._jwt = null;
            this.Items.Clear();
            if ((this._jwt == null) && this.Items.Count == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        private void PrintJob(IEnumerable<object> items)
        {
            if (items != null)
            {
                StringWriter writer = new StringWriter();
                foreach (FileList item in items)
                {
                    Task.Run(async () =>
                    {
                        string uri = "http://xdkyu02.cafe24.com/fileDownload.do?fileId=" + item.fileId.ToString();

                        Console.WriteLine(_jwt);
                        Console.WriteLine(uri);

                        using (var client = new HttpClient())
                        {
                            client.DefaultRequestHeaders.Add("x-access-token", _jwt);

                            var response = await client.GetAsync(uri);
                            Console.WriteLine(response);
                            if (response.IsSuccessStatusCode)
                            {
                                var jsonresult = response.Content.ReadAsStringAsync().Result;
                                Console.WriteLine(jsonresult);
                                //deserialized 된 Json
                                var jsonParse = JsonConvert.DeserializeObject<DownloadResponse>(jsonresult);
                                var fileUrl = jsonParse.data.fileUrl;
                                using (var webclient = new WebClient())
                                {
                                    webclient.DownloadFile(fileUrl, "C:\\temp\\"+item.fileName);
                                }


                            }
                            else
                            {


                            }
                        }

                    });
                }
                string[] files = Directory.GetFiles(@"c:\temp");
                foreach (string file in files.Where(
                            file => file.ToUpper().Contains(".PDF")))
                {
                    bool succ = Pdf.PrintPDFs(file);
                    if (succ)
                    {
                        Task.Run(async () =>
                        {
                            string uri = "http://xdkyu02.cafe24.com/signIn.do";

                            string json = new JavaScriptSerializer().Serialize(new
                            {
                                printCount = _userName,
                                ClientId = _userPwd
                                userId = 
                                fileId = 
                            });


                            using (var client = new HttpClient())
                            {
                                Console.WriteLine("httpclient");
                                var response = await client.PostAsync(
                                    uri,
                                     new StringContent(json, Encoding.UTF8, "application/json"));
                                if (response.IsSuccessStatusCode)
                                {
                                    var jsonresult = response.Content.ReadAsStringAsync().Result;

                                    //deserialized 된 Json
                                    var jsonparse = JsonConvert.DeserializeObject<JwtResponse>(jsonresult);

                                    _jwt = jsonparse.ToString();

                                    if (_jwt != "") return true;
                                    else return false;
                                }
                                else
                                {
                                    return false;

                                }
                            }

                        });
                    }
                    Console.WriteLine("PDF");
                }

                //MessageBox.Show(writer.ToString());
            }
        }
        private void RefreshToken(IEnumerable<object> items)
        {

            Console.WriteLine("Refresh Token");
        }
        private void RefreshFileList(IEnumerable<object> items)
        {
            Console.WriteLine("Refresh File List");
        }
        private bool CanShowSelected(IEnumerable<object> items)
        {
            return items != null && items.Count() > 0;
        }
    }
}