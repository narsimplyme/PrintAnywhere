using Newtonsoft.Json;
using PrintAnywhere.Comm;
using PrintAnywhere.Models;
using PrintAnywhere.Views;
using System;
using System.Data;
using System.Net.Http;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using System.Windows;
using System.Windows.Input;

namespace PrintAnywhere.ViewModels
{
    public class LoginViewModel : ViewModelBase
    {

        private static readonly HttpClient client = new HttpClient();


        private readonly Action _closeAction;

        private string _jwt { get; set; }


        private CancellationTokenSource _cts;


        private string _userName;

        public string UserName
        {
            get { return _userName; }
            set
            {
                _userName = value;
                OnPropertyChanged("UserName");
            }
        }

        private string _userPwd;

        public string UserPwd
        {
            get { return _userPwd; }
            set
            {
                _userPwd = value;
                OnPropertyChanged("UserPwd");
            }
        }


        private bool _isLogging;

        public bool IsLogging
        {
            get { return _isLogging; }
            set
            {
                _isLogging = value;
                OnPropertyChanged("IsLogging");
                OnPropertyChanged("IsNotLogging");
            }
        }

        public bool IsNotLogging
        {
            get { return !_isLogging; }
        }

        private int _progressValue;

        public int ProgressValue
        {
            get { return _progressValue; }
            set
            {
                _progressValue = value;
                OnPropertyChanged("ProgressValue");
            }
        }

        private bool _isShowResult;

        public bool IsShowResult
        {
            get { return _isShowResult; }
            set
            {
                _isShowResult = value;
                OnPropertyChanged("IsShowResult");
            }
        }

        private string _resultDescription;

        public string ResultDescription
        {
            get { return _resultDescription; }
            set
            {
                _resultDescription = value;
                OnPropertyChanged("ResultDescription");
            }
        }


        private ICommand _loginCmd;

        public ICommand LoginCmd
        {
            get
            {
                if (_loginCmd == null)
                {
                    return new RelayCommand(p => UserLogging());
                }

                return _loginCmd;
            }
        }

        private ICommand _cancelCmd;

        public ICommand CancelCmd
        {
            get
            {
                if (_cancelCmd == null)
                {
                    return new RelayCommand(p => _cts.Cancel());
                }

                return _cancelCmd;
            }
        }

        private RelayCommand _hideResultCmd;

        public ICommand HideResultCmd
        {
            get
            {
                if (_hideResultCmd == null)
                {
                    return new RelayCommand(p => HideLoginResult(), p => IsShowResult);
                }

                return _hideResultCmd;
            }
        }

        public LoginViewModel(Action closeAction)
        {
            this._closeAction = closeAction;
        }


        /// <summary>
        /// 
        /// </summary>
        private async void UserLogging()
        {

            IsLogging = true;
            IsShowResult = false;

            var result = false;
            var progressIndicator = new Progress<int>(ReportProgress);
            _cts = new CancellationTokenSource();



            try
            {
                result = await Login(progressIndicator, _cts.Token);


            }
            catch (TaskCanceledException ex)
            {
                ResultDescription = ex.Message;

            }

            catch (Exception ex)
            {
                ResultDescription = ex.Message;
            }
            finally
            {
                _cts.Dispose();
            }

            IsLogging = false;


            if (result)
            {
                ShowMainWindow();
            }
            else
            {
                ProgressValue = 0;
                if (!_cts.IsCancellationRequested)
                {
                    IsShowResult = true;
                }
            }
        }


        private async Task<bool> Login(IProgress<int> progress, CancellationToken ct)
        {
            return await Task.Run(async () =>
            {
                string uri = "http://xdkyu02.cafe24.com/signIn.do";

                string json = new JavaScriptSerializer().Serialize(new
                {
                    userId = _userName,
                    userPw = _userPwd
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

            }, ct);
        }



        /// <summary>
        /// 
        /// </summary>
        /// <param name="value"></param>
        private void ReportProgress(int value)
        {
            ProgressValue = value;
        }

        /// <summary>
        /// 
        /// </summary>
        private void HideLoginResult()
        {
            ResultDescription = String.Empty;
            IsShowResult = false;
        }

        /// <summary>
        /// 
        /// </summary>
        private void ShowMainWindow()
        {


            var winMain = new MainWindow();
            Application.Current.MainWindow = winMain;
            winMain.DataContext = new MainWindowViewModel(_closeAction, _jwt);
            _closeAction.Invoke();
            winMain.Show();
        }


    }
}
