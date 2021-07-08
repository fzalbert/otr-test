using KafkaClient;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.OpenApi.Models;
using NotificationServer.Consumer;
using NotificationServer.DTO;
using System;

namespace NotificationServer
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {


            services.AddMvc(option => option.EnableEndpointRouting = false);


            services.AddKafkaConsumer<string, CreateNotificationDto, CreateNotificationHandler>(p =>
            {
                p.Topic = "EmailNotification";
                p.GroupId = "emails";
                p.BootstrapServers = "localhost:9092";
            });

            services.AddCors();

            services.AddHttpContextAccessor();

            services.AddBuisnessServices();


        }


        public void Configure(IApplicationBuilder app, Microsoft.AspNetCore.Hosting.IHostingEnvironment env, IServiceProvider serviceProvider)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }


            app.UseCors(builder =>
                builder.WithOrigins("*").AllowAnyHeader().AllowAnyMethod());

            app.UseAuthentication();
            app.UseMvc();
            app.UseRouting();
            //app.UseHttpsRedirection();

        }
    }
}
