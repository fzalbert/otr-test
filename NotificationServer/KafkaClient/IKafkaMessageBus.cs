using System.Threading.Tasks;

namespace KafkaClient
{
    public interface IKafkaMessageBus<Tk, Tv>
    {
        Task PublishAsync(Tk key, Tv message);
    }
}