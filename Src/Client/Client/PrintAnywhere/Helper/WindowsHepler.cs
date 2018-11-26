using System;
using System.Runtime.InteropServices;

namespace PrintAnywhere.Helper
{
    public class WindowsHelper
    {
        const int GCL_STYLE = -26;
        const int CS_DROPSHADOW = 0x20000;

        public const int GWL_STYLE = -16;


        public const int WS_CAPTION = 0xC00000;

        public const int WS_CAPTION_2 = 0xC0000;


        [DllImport("User32.dll")]

        public static extern int SetWindowLong(IntPtr handle, int oldStyle, Int32 newStyle);


        [DllImport("user32.dll")]
        public static extern int GetWindowLong(IntPtr handle, int style);



        //Win32 API
        [DllImport("user32.dll", CharSet = CharSet.Auto)]
        public static extern int SetClassLong(IntPtr hwnd, int nIndex, int dwNewLong);

        [DllImport("user32.dll", CharSet = CharSet.Auto)]
        public static extern int GetClassLong(IntPtr hwnd, int nIndex);

        //=================================================================================


        /// <param name="hWnd"></param>
        public static void SetWindowNoBorder(IntPtr hWnd)
        {
            var oldstyle = GetWindowLong(hWnd, GWL_STYLE);

            SetWindowLong(hWnd, GWL_STYLE, oldstyle & (~(WS_CAPTION | WS_CAPTION_2)));
            //SetWindowLong(hWnd, GWL_EXSTYLE, WS_CHILD);
            SetClassLong(hWnd, GCL_STYLE, GetClassLong(hWnd, GCL_STYLE) | CS_DROPSHADOW);
        }
    }
}
