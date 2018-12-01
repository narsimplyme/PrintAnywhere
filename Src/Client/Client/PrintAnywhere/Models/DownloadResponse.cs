using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PrintAnywhere.Models
{
    class DownloadResponse
    {
        public bool success { get; set; }
        public string message { get; set; }
        public string errors { get; set; }
        public FileUrl data { get; set; }

        public override string ToString()
        {
            return data.ToString();
        }

    }
    class FileUrl
    {
        public string fileUrl { get; set; }
        public override string ToString()
        {
            return fileUrl;
        }
    }
}
