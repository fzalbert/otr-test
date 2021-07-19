﻿using Newtonsoft.Json.Linq;
using System.IO;
using System.Threading.Tasks;

namespace Mongo
{
    public interface IMongoBase
    {
        Task Save(ModelMessage model);
    }

    public class MongoBase : IMongoBase
    {
        public readonly static string CONNECTION_STRING;
        public readonly static string DATABASE_NAME;
        public readonly static string PASSWORD;
        public readonly static string USER_NAME;


        static MongoBase()
        {
            using (var r = new StreamReader("mongosettings.json"))
            {
                var json = r.ReadToEnd();
                dynamic d = JObject.Parse(json);

                PASSWORD = d.PASSWORD;
                CONNECTION_STRING = d.CONNECTION_STRING;
                DATABASE_NAME = d.DATABASE_NAME;
                USER_NAME = d.USER_NAME;
            }

        }

        public async Task Save(ModelMessage model)
        {
            IDatabaseContext databaseContext = new DatabaseContext(CONNECTION_STRING, DATABASE_NAME, USER_NAME, PASSWORD);
            ISaveInBaseRepository repository = new SaveInBaseRepository(databaseContext);

            await repository.Add(new ModelMessage
            {
                Content = model.Content,
                Email = model.Content,
                IsSend = true,
                RecipientName = model.RecipientName,
                SenderName = model.SenderName
            });

        }
    }
}
