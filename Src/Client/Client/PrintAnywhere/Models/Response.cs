using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PrintAnywhere.Models
{
    class Response
    {
        public bool success { get; set; }
        public string message { get; set; }
        public string errors { get; set; }
        //public string token { get; set; }
        public data data { get; set; }

        public override string ToString()
        {
            return data.token;
        }

        public Response(bool success, string name, string message, string errors, data data)
        {
            this.success = success;
            this.message = message;
            this.errors = errors;
            //this.token = token;
            this.data = data;
        }

        public Response()
        {
        }
    }
    class data
    {
        public string token { get; set; }
        public override string ToString()
        {
            return token;
        }
    }
}
