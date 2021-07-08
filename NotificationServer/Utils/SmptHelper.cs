using System;
using System.Net;
using System.Net.Mail;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Utils
{
    public static class SMTPHelper
    {
        public static string SMTP_SERVER = "smtp.gmail.com";
        public static int SMTP_PORT = 587;
        public static string SMTP_LOGIN = "chakalov228@gmail.com";
        public static string SMTP_PASSWORD = "spiridon3365276";

        /// <summary>
        /// Send Mail via SMTP method
        /// </summary>
        /// <param name="model">SendMailModel contents all required params for sending mail</param>
        public static async Task<bool> SendMail(SendMailModel model)
        {
            try
            {
                MailMessage mail = new MailMessage();
                mail.From = new MailAddress(SMTP_LOGIN);
                mail.To.Add(new MailAddress(model.MailTo));
                mail.Subject = model.Subject;
                mail.IsBodyHtml = true;
                mail.Body = model.Message;

                if (!string.IsNullOrEmpty(model.AttachFile))
                    mail.Attachments.Add(new Attachment(model.AttachFile));

                SmtpClient client = new SmtpClient(SMTP_SERVER, SMTP_PORT);

                client.UseDefaultCredentials = false;
                client.EnableSsl = true;

                var crd = new NetworkCredential(SMTP_LOGIN, SMTP_PASSWORD);
                client.Credentials = crd;
                client.DeliveryMethod = SmtpDeliveryMethod.Network;
                await client.SendMailAsync(mail);
                mail.Dispose();
                return true;
            }
            catch (Exception e)
            {
                Console.WriteLine(string.Format(@"Error message: {0}", e.Message));
                return false;
            }
        }

        static Regex ValidEmailRegex = CreateValidEmailRegex();


        private static Regex CreateValidEmailRegex()
        {
            string validEmailPattern = @"^(?!\.)(""([^""\r\\]|\\[""\r\\])*""|"
                + @"([-a-z0-9!#$%&'*+/=?^_`{|}~]|(?<!\.)\.)*)(?<!\.)"
                + @"@[a-z0-9][\w\.-]*[a-z0-9]\.[a-z][a-z\.]*[a-z]$";

            return new Regex(validEmailPattern, RegexOptions.IgnoreCase);
        }

        public static bool EmailIsValid(string emailAddress)
        {
            bool isValid = ValidEmailRegex.IsMatch(emailAddress);

            return isValid;
        }


        public class SendMailModel
        {
            /// <summary>
            /// E-mail receiver address
            /// </summary>
            public string MailTo { get; set; }
            /// <summary>
            /// E-mail subject
            /// </summary>
            public string Subject { get; set; }
            /// <summary>
            /// E-mail body message
            /// </summary>
            public string Message { get; set; }
            /// <summary>
            /// E-mail attach file content
            /// </summary>
            public string AttachFile { get; set; }
        }
    }
}
