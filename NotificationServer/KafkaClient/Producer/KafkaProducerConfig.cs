using Confluent.Kafka;

namespace KafkaClient.Producer
{
    public class KafkaProducerConfig<Tk, Tv> : ProducerConfig
    {
        public string Topic { get; set; }
    }
}