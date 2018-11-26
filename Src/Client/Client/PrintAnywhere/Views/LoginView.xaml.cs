using System;
using System.Diagnostics;
using System.Reflection;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Interop;
using System.Windows.Media;
using GlowingWindow;
using PrintAnywhere.Helper;
using PrintAnywhere.ViewModels;

namespace PrintAnywhere.Views
{

    public partial class LoginView
    {
        public LoginView()
        {
            InitializeComponent();

            DataContext = new LoginViewModel(() => Close());
            this.MouseLeftButtonDown += OnMouseLeftButtonDown;
        }


        private void OnMouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            this.DragMove();
        }


        private void PbPwd_OnPasswordChanged(object sender, RoutedEventArgs e)
        {
            var passwordtext = (PasswordBox)sender;
            SetPasswordBoxSelection(passwordtext, passwordtext.Password.Length + 1, passwordtext.Password.Length + 1);
        }

        private static void SetPasswordBoxSelection(PasswordBox passwordBox, int start, int length)
        {
            var select = passwordBox.GetType().GetMethod("Select",
                BindingFlags.Instance | BindingFlags.NonPublic);

            select.Invoke(passwordBox, new object[] { start, length });
        }

        private void Close_OnClick(object sender, RoutedEventArgs e)
        {
            this.Close();
        }

        private void Min_OnClick(object sender, RoutedEventArgs e)
        {
            this.WindowState = WindowState.Minimized;
        }
    }
}
