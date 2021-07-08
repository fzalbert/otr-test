using System;

using Confluent.Kafka;

using Microsoft.Extensions.Options;
using Microsoft.Extensions.DependencyInjection;
using KafkaClient.Consumer;
using KafkaClient.Producer;

namespace KafkaClient
{
    public static class RegisterServiceExtensions
    {
        public static IServiceCollection AddKafkaMessageBus(this IServiceCollection serviceCollection)
            => serviceCollection.AddSingleton(typeof(IKafkaMessageBus<,>), typeof(KafkaMessageBus<,>));

        public static IServiceCollection AddKafkaConsumer<Tk, Tv, THandler>(this IServiceCollection services,
            Action<KafkaConsumerConfig<Tk, Tv>> configAction) where THandler : class, IKafkaHandler<Tk, Tv>
        {
            services.AddScoped<IKafkaHandler<Tk, Tv>, THandler>();

            services.Configure(configAction);

            services.AddHostedService<BackGroundKafkaConsumer<Tk, Tv>>();

            return services;
        }

        public static IServiceCollection AddKafkaProducer<Tk, Tv>(this IServiceCollection services,
            Action<KafkaProducerConfig<Tk, Tv>> configAction)
        {
            services.AddConfluentKafkaProducer<Tk, Tv>();

            services.AddSingleton<KafkaProducer<Tk, Tv>>();

            services.Configure(configAction);

            return services;
        }

        private static IServiceCollection AddConfluentKafkaProducer<Tk, Tv>(this IServiceCollection services)
        {
            services.AddSingleton(
                sp =>
                {
                    var config = sp.GetRequiredService<IOptions<KafkaProducerConfig<Tk, Tv>>>();

                    var builder = new ProducerBuilder<Tk, Tv>(config.Value).SetValueSerializer(new KafkaSerializer<Tv>());

                    return builder.Build();
                });

            return services;
        }
    }
}