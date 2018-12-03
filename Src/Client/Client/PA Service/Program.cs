using Microsoft.Extensions.PlatformAbstractions;
using System;
using System.IO;

namespace PeterKottas.DotNetCore.WindowsService.Example
{
	public class Program
    {
        public static void Main(string[] args)
        {
            ServiceRunner<ExampleService>.Run(config =>
            {
                var name = config.GetDefaultName();
                config.Service(serviceConfig =>
                {
                    serviceConfig.ServiceFactory((extraArguments, controller) =>
                    {
                        return new ExampleService(controller);
                    });

                    serviceConfig.OnStart((service, extraParams) =>
                    {
                        Console.WriteLine("Service {0} started", name);
                        service.Start();
                    });

                    serviceConfig.OnStop(service =>
                    {
                        Console.WriteLine("Service {0} stopped", name);
                        service.Stop();
                    });

                    serviceConfig.OnShutdown(service =>
                    {
                        Console.WriteLine("Service {0} shutdown", name);
                    });

                    serviceConfig.OnError(e =>
                    {
                        Console.WriteLine("Service {0} errored with exception : {1}", name, e.Message);
                    });
                });
                config.SetName("PA Service");
                config.SetDescription("PrintAnywhere Service");
            });
        }
    }
}
