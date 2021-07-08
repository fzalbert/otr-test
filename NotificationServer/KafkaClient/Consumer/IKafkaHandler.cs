using System.Threading.Tasks;

namespace KafkaClient.Consumer
{
    public interface IKafkaHandler<Tk, Tv>
    {
        Task HandleAsync(Tk key, Tv value);
    }
}