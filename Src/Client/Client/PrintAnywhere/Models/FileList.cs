using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PrintAnywhere.Models
{

    public class FileModel
    {
        public FileList[] fileList {get;set;}
    }

    public class FileList
    {
        public int fileId { get; set; }
        public string fileHash { get; set; }
        public string fileName { get; set; }
        public int fileType { get; set; }
        public string fileDate { get; set; }
        public string fileSize { get; set; }
        public string userId { get; set; }
        public int endFilePoint { get; set; }

    }
}
