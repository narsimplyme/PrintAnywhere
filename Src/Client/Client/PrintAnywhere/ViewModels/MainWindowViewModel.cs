using MvvmFoundation.Wpf;
using Newtonsoft.Json;
using PrintAnywhere.Helper;
using PrintAnywhere.Models;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using System.Windows.Input;

namespace PrintAnywhere
{
    public class MainWindowViewModel
    {
        public ObservableCollection<FileList> Items { get; private set; }
        public ICommand ShowSelectedCommand { get; private set; }
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

            //            {
            //                new FileList(213120,"File0.pdf","PDF",13123,"SSS"),
            //                new FileList(112312,"File2.pdf","PDF",13123,"SSS"),
            //                new FileList(123122,"File4.pdf","PDF",13123,"C:\\CAP.xml"),
            //            };

            ShowSelectedCommand = new RelayCommand<IEnumerable<object>>(ShowSelected, CanShowSelected);

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
                        Logout();

                    }
                }

            });

        }

        public void Logout()
        {
            Console.WriteLine("Logout");
        }

        private void ShowSelected(IEnumerable<object> items)
        {
            if (items != null)
            {
                StringWriter writer = new StringWriter();
                foreach (FileList item in items)
                {

                }
                string[] files = Directory.GetFiles(@"c:\");
                foreach (string file in files.Where(
                            file => file.ToUpper().Contains(".PDF")))
                {
                    Pdf.PrintPDFs(file);
                    Console.WriteLine("PDF");
                }

                //MessageBox.Show(writer.ToString());
            }
        }

        private bool CanShowSelected(IEnumerable<object> items)
        {
            return items != null && items.Count() > 0;
        }
    }
}