using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Linq;
using System.Windows.Input;
using MvvmFoundation.Wpf;
using PrintAnywhere.Models;
using PrintAnywhere.Helper;

namespace PrintAnywhere
{
    public class MainWindowViewModel
    {
        public ObservableCollection<FileList> Items { get; private set; }
        public ICommand ShowSelectedCommand { get; private set; }


        private readonly Action _closeAction;
        private readonly UserModel _user;
        public string _userName { get; private set; }
        public int _userPoint { get; private set; }


        public MainWindowViewModel(Action closeAction, UserModel user)
        {
            this._closeAction = closeAction;
            this._user = user;
            this._userName = user.UserName;

            GetUserPoint();


            
            Items = new ObservableCollection<FileList>()
                        {
                            new FileList(213120,"File0.pdf","PDF",13123,"SSS"),
                            new FileList(112312,"File2.pdf","PDF",13123,"SSS"),
                            new FileList(123122,"File4.pdf","PDF",13123,"C:\\CAP.xml"),
                        };

            ShowSelectedCommand = new RelayCommand<IEnumerable<object>>(ShowSelected, CanShowSelected);

        }
        //todo
        public void GetUserPoint()
        {
            //authme로 해야함? 그러면 걍 user에서 주면 되긴 하는데
            this._userPoint = 1000;

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