using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace Mongo
{
    public interface ISaveInBaseRepository
    {
        Task<ModelMessage> Add(ModelMessage model);
    }
    public class SaveInBaseRepository : MongoDbRepository<ModelMessage>, ISaveInBaseRepository
    {
        public SaveInBaseRepository(IDatabaseContext databaseContext) : base(databaseContext) { }
        public async Task<ModelMessage> Add(ModelMessage model)
        {
            await collection.InsertOneAsync(model);
            return model;
        }
    }
}
