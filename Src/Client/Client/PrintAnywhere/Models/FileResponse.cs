using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PrintAnywhere.Models
{
    class FileResponse
    {
        public bool success { get; set; }
        public string message { get; set; }
        public string errors { get; set; }
        public FileModel data { get; set; }

        public override string ToString()
        {
            return data.ToString();
        }
    }

}
