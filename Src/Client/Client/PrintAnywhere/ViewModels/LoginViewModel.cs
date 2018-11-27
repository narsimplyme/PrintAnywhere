using PrintAnywhere.Comm;
using PrintAnywhere.Models;
using PrintAnywhere.Views;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
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
            if (!VerifyUserInfoLocal()) return;

            IsLogging = true;
            IsShowResult = false;

            var result = false;
            var progressIndicator = new Progress<int>(ReportProgress);
            _cts = new CancellationTokenSource();


            Console.WriteLine("1");

            try
            {
                result = await ImplLogin(progressIndicator, _cts.Token);


                Console.WriteLine("2");
            }
            catch (TaskCanceledException ex)
            {
                ResultDescription = ex.Message;

                Console.WriteLine("ex");
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


            Console.WriteLine("3");

            if (result)
            {
                Console.WriteLine("4");
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


        private async Task<bool> ImplLogin(IProgress<int> progress, CancellationToken ct)
        {
            return await Task.Run(async () =>
            {
                string uri = "http://xdkyu02.cafe24.com/signIn.do";
                string uri2 = "http://xdkyu02.cafe24.com/signUp.do";

                string json = new JavaScriptSerializer().Serialize(new
                {
                    userID = "merom",
                    userPw = "123456",
                });

                //string requestJson = "{\"userId\":" + _userName + ", \"userPw\":" + _userPwd + "}";

                using (var client = new HttpClient())
                {
                    var response = await client.PostAsync(
                        uri,
                         new StringContent(json, Encoding.UTF8, "application/json"));
                    Console.WriteLine(response);
                }


                //var request = (HttpWebRequest)WebRequest.Create(uri2);
                //request.ContentType = "application/json";
                //request.Method = "POST";

                //using (var streamWriter = new StreamWriter(request.GetRequestStream()))
                //{
                //    string json = new JavaScriptSerializer().Serialize(new
                //    {
                //        userID = "merom",
                //        userPw = "123456",
                //        userPwConfirm = "123456",
                //        userName = "Merom",
                //        userNickName = "Merom",
                //        userMail = "narsimplyme@naver.com",
                //        userPhoneNumber="12345"
                //    });

                //    streamWriter.Write(json);
                //}

                //var response = (HttpWebResponse)request.GetResponse();
                //using (var streamReader = new StreamReader(response.GetResponseStream()))
                //{
                //    var result = streamReader.ReadToEnd();
                //    Console.WriteLine(result);
                //}



                var model = await GetUserInfoAsync(progress, ct);

                if (!model.UserName.Equals("test", StringComparison.OrdinalIgnoreCase)
                    || !UserPwd.Equals("test", StringComparison.OrdinalIgnoreCase))
                {
                    Console.WriteLine("false");
                    ResultDescription = "계정 정보 틀림！";
                    return false;
                }

                ResultDescription = String.Empty;
                Console.WriteLine("true");
                return true;
            }, ct);
        }

        /// <summary>
        /// 
        /// </summary>

        private async Task<UserModel> GetUserInfoAsync(IProgress<int> progress, CancellationToken ct)
        {
            return await Task.Run(async () =>
            {
                for (var i = 0; i < 100; i++)
                {
                    await Task.Delay(10, ct);
                    progress.Report(i);
                }

                return new UserModel { UserName = "test", UserPwd = "test" };
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
        private bool VerifyUserInfoLocal()
        {
            if (String.IsNullOrEmpty(UserName))
            {
                IsShowResult = true;
                ResultDescription = "아이디를 입력해주세요!";
                return false;
            }

            if (String.IsNullOrEmpty(UserPwd))
            {
                IsShowResult = true;
                ResultDescription = "비밀번호를 입력해주세요!";
                return false;
            }

            return true;
        }

        /// <summary>
        /// 
        /// </summary>
        private void ShowMainWindow()
        {
            var user = new UserModel
            {
                UserName = UserName,
                UserPwd = UserPwd
            };

            var winMain = new MainWindow();
            Application.Current.MainWindow = winMain;
            winMain.DataContext = new MainWindowViewModel(_closeAction, user);
            _closeAction.Invoke();
            winMain.Show();
        }


    }
}
