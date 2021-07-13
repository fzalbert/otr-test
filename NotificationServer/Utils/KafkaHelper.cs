using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace Utils
{
    public static class KafkaHelper
    {

        public static string TOPIC;
        public static string GROUP_ID;
        public static string SERVER;

        static KafkaHelper()
        {
            using (var r = new StreamReader("kafkasettings.json"))
            {
                var json = r.ReadToEnd();
                dynamic d = JObject.Parse(json);

                TOPIC = d.TOPIC;
                GROUP_ID = d.GROUP_ID;
                SERVER = d.SERVER;
            }
        }

    }
}
