﻿using System;
using System.Collections.Generic;
using System.Data;
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
        public DataTable data { get; set; }

    }

}
