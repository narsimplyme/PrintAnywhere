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
using System.Text;
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
        private UserModel _user { get; set; }
        public string _userName { get; private set; }
        public string _userPoint { get; private set; }
        private string _jwt;


        public MainWindowViewModel(Action closeAction, string _jwt)
        {
            this._closeAction = closeAction;

            this._jwt = _jwt;

            GetUserData();



            Items = new ObservableCollection<FileList>()
                        {
                            new FileList(213120,"File0.pdf","PDF",13123,"SSS"),
                            new FileList(112312,"File2.pdf","PDF",13123,"SSS"),
                            new FileList(123122,"File4.pdf","PDF",13123,"C:\\CAP.xml"),
                        };

            ShowSelectedCommand = new RelayCommand<IEnumerable<object>>(ShowSelected, CanShowSelected);

        }
        //todo
        public void GetUserData()
        {
            Task.Run(async () =>
            {
                string uri = "http://xdkyu02.cafe24.com/show.do";
                //string uri2 = "http://xdkyu02.cafe24.com/signUp.do";
                //string uri3 = "http://xdkyu02.cafe24.com/jsonTest.do";




                using (var client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Add("x-auth-token", _jwt);
                    var response = await client.GetAsync(uri);
                    Console.WriteLine(response);
                    if (response.IsSuccessStatusCode)
                    {
                        var jsonresult = response.Content.ReadAsStringAsync().Result;

                        //deserialized 된 Json
                        _user = JsonConvert.DeserializeObject<UserModel>(jsonresult);
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
                    //writer.WriteLine(item.FileID);
                    //ProcessStartInfo info = new ProcessStartInfo();
                    //info.Verb = "print";
                    //info.FileName = @"c:\ERD_1.3.pdf";
                    //info.CreateNoWindow = true;
                    //info.WindowStyle = ProcessWindowStyle.Hidden;

                    //Process p = new Process();
                    //p.StartInfo = info;
                    //p.Start();

                    //p.WaitForInputIdle();
                    //System.Threading.Thread.Sleep(3000);
                    //if (false == p.CloseMainWindow())
                    //    p.Kill();

                    //Pdf.PrintPDFs(@"c:\ERD_1.3.pdf");
                    //Console.WriteLine("PDF");
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