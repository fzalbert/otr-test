using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;

namespace Mongo
{
    public class ModelMessage
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }

        public string Email { get; set; }

        public string SenderName { get; set; }

        public string RecipientName { get; set; }

        public string Content { get; set; }

        public bool IsSend { get; set; }
    }
}
