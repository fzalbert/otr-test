using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace Mongo
{
    public interface IMongoBase
    {
        Task Save(ModelMessage model);
    }

    public class MongoBase : IMongoBase
    {
        private static string ConnectionString = "mongodb://localhost:27017";
        private static string DatabaseName = "EmailMessages";

        public async Task Save(ModelMessage model)
        {
            IDatabaseContext databaseContext = new DatabaseContext(ConnectionString, DatabaseName);
            ISaveInBaseRepository repository = new SaveInBaseRepository(databaseContext);

            await repository.Add(new ModelMessage {
                Content = model.Content,
                Email = model.Content,
                IsSend = true,
                RecipientName = model.RecipientName,
                SenderName = model.SenderName
            }); 

        }
    }
}
