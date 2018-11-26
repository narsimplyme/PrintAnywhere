using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PrintAnywhere.Models
{
    public class FileList
    {
        public int FileID { get; set; }
        public string FileName { get; set; }
        public int FileType { get; set; }
        public int FileSize { get; set; }
        public string FileUploadDate { get; set; }


        public override string ToString()
        {
            return FileName;
        }

        public FileList(int id, string name, int type, int size, string date)
        {
            this.FileID = id;
            this.FileName = name;
            this.FileType = type;
            this.FileSize = size;
            this.FileUploadDate = date;
        }
    }
}
