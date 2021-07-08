using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Text;

namespace Mongo
{
    public abstract class MongoDbRepository<T>
    {
        protected IMongoDatabase _db;
        protected readonly IMongoCollection<T> collection;

        public MongoDbRepository(IDatabaseContext databaseContext)
        {
            _db = databaseContext.Database;
            collection = _db.GetCollection<T>("EmailMessages");
        }
    }
}
