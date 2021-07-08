using Mongo;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Utils;
using static Utils.SMTPHelper;

namespace NotificationServer.EmailServices
{
    public class EmailService : IEmailService
    {
        private IMongoBase MongoBase { get; set; }


        public EmailService(IMongoBase mongoBase)
        {
            MongoBase = mongoBase;
        }

        public async Task<bool> SendMessage(string receiver, string subject, string text)
        {
            var message = new SendMailModel
            {
                MailTo = receiver,
                Subject = subject,
                Message = text
            };

            return await SMTPHelper.SendMail(message);
        }
    }
}
