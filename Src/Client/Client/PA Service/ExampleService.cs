using Microsoft.Extensions.PlatformAbstractions;
using PeterKottas.DotNetCore.WindowsService.Interfaces;
using System;
using System.IO;
using System.Net.Http;
using System.Threading.Tasks;
using System.Timers;


namespace PeterKottas.DotNetCore.WindowsService.Example
{
    public class ExampleService : IMicroService
    {
        private IMicroServiceController controller;

		private Timer timer = new Timer(60000);

        public ExampleService()
        {
            controller = null;
        }

        public ExampleService(IMicroServiceController controller)
        {
            this.controller = controller;
        }
        
        public void Start()
        {


			timer.Elapsed += _timer_Elapsed;
			timer.Start();
        }

		private void _timer_Elapsed(object sender, ElapsedEventArgs e)
		{
            //GetFileData();

		}

		public void Stop()
        {
			timer.Stop();

        }

        //public void GetFileData()
        //{
        //    Task.Run(async () =>
        //    {
        //        string uri = "http://xdkyu02.cafe24.com/reserveFileForClient.do?clientId=3";

        //        using (var client = new HttpClient())
        //        {

        //            var response = await client.GetAsync(uri);
        //            Console.WriteLine(response);
        //            if (response.IsSuccessStatusCode)
        //            {
        //                var jsonresult = response.Content.ReadAsStringAsync().Result;
        //                Console.WriteLine(jsonresult);
        //                //deserialized 된 Json
        //                var jsonParse = JsonConvert.DeserializeObject<FileResponse>(jsonresult);
        //                _filelist = jsonParse.data.fileList;

        //                foreach (FileList item in _filelist)
        //                {
        //                    Items.Add(item);
        //                }

        //            }
        //            else
        //            {


        //            }
        //        }

        //    });

        //}

    }
}
