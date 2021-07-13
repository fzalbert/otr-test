using KafkaClient.Consumer;
using Mongo;
using NotificationServer.DTO;
using NotificationServer.EmailServices;
using System.Threading.Tasks;

namespace NotificationServer.Consumer
{
    public class CreateNotificationHandler : IKafkaHandler<string, CreateNotificationDto>
    {

        private IMongoBase MongoBase { get; set; }
        private IEmailService EmailService { get; set; }

        public CreateNotificationHandler(IMongoBase mongoBase, IEmailService emailService)
        {
            MongoBase = mongoBase;
            EmailService = emailService;
        }

        public async Task HandleAsync(string key, CreateNotificationDto model)
        {
            if (model == null)
                return;


            // доделать под сохранение в базу не отправленный сообщений с полем IsSend 

            var isSend = await EmailService.SendMessage(model.Email, model.Subject, model.Content);

            if (isSend)
            {
                var mongoModel = new ModelMessage
                {
                    RecipientName = model.Name,
                    Content = model.Content,
                    Email = model.Email
                };
                await MongoBase.Save(mongoModel);
            }
        }
    }
}