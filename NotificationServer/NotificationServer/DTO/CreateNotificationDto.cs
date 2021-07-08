using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace NotificationServer.DTO
{
    public class CreateNotificationDto
    {
        [JsonProperty("content")]
        public string Content { get; set; }

        [JsonProperty("email")]
        public string Email { get; set; }

        [JsonProperty("subject")]
        public string Subject { get; set; }

        [JsonProperty("name")]
        public string Name { get; set; }
    }
}
