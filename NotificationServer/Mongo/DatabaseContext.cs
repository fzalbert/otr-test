﻿using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Text;

namespace Mongo
{
    public interface IDatabaseContext
    {
        IMongoDatabase Database { get; }
        MongoClient MongoClient { get; }
    }
    
    public class DatabaseContext : IDatabaseContext
    {
        public IMongoDatabase Database { get; }
        public MongoClient MongoClient { get; }

        public DatabaseContext(string connectionString, string databaseName, string userName, string password)
        {
            var credential = MongoCredential.CreateCredential(databaseName, userName, password);

            var settings = new MongoClientSettings
            {
                Credentials = new[] { credential }
            };

            var mongoClient = new MongoClient(settings);

        }
    }
}
