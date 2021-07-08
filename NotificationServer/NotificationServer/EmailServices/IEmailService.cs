using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace NotificationServer.EmailServices
{
    public interface IEmailService
    {
        Task<bool> SendMessage(string receiver, string subject, string text);
    }
}
