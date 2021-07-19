using System;
using System.Text;
using Confluent.Kafka;
using Newtonsoft.Json;

namespace KafkaClient
{
    internal sealed class KafkaDeserializer<T> : IDeserializer<T>
    {
        public T Deserialize(ReadOnlySpan<byte> data, bool isNull, SerializationContext context)
        {
            if (typeof(T) == typeof(Null))
            {
                if (data.Length > 0)
                    throw new ArgumentException("The data is null not null.");
                return default;
            }

            if (typeof(T) == typeof(Ignore))
                return default;

            var dataJson = Encoding.UTF8.GetString(data);
            try
            {
                return JsonConvert.DeserializeObject<T>(dataJson);
            } 
            catch(Exception e)
            {
                Console.WriteLine($"DATA ERROR" + e.Message);
                return default(T);
            }
        }
    }
}