using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PrintAnywhere.Models
{
    class JwtResponse
    {
        public bool success { get; set; }
        public string message { get; set; }
        public string errors { get; set; }
        public Jwt data { get; set; }

        public override string ToString()
        {
            return data.ToString();
        }

    }
    class Jwt
    {
        public string token { get; set; }
        public override string ToString()
        {
            return token;
        }
    }
}
