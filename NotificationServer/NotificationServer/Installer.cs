using Microsoft.Extensions.DependencyInjection;
using Mongo;
using NotificationServer.EmailServices;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace NotificationServer
{
    public static class Installer
    {
        public static void AddBuisnessServices(this IServiceCollection container)
        {
            container
                .AddScoped<IEmailService, EmailService>()
                .AddScoped<IMongoBase, MongoBase>();            

        }
    }
}
