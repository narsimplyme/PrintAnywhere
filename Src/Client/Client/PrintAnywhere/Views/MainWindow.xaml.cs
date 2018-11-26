using System.Windows;
using System.Windows.Input;

namespace PrintAnywhere.Views
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow
    {
        public MainWindow()
        {
            InitializeComponent();
            //DataContext = new MainWindowViewModel(() => Close());
            this.MouseLeftButtonDown += OnMouseLeftButtonDown;
        }

        private void MenuItem_Click(object sender, RoutedEventArgs e)
        {

        }
        private void OnMouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            this.DragMove();
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
