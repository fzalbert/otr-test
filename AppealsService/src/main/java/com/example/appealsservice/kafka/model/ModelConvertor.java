package com.example.appealsservice.kafka.model;

public class ModelConvertor {

    public static ModelMessage Convert(String email, String name, String subject, MessageType type)
    {
        ModelMessage message = new ModelMessage();
        message.setEmail(email);
        message.setName(name);
        message.setSubject(subject);

        switch (type)    {
            case TAKEAPPEAL:
                message.setContent("Здравствуйте, " + name + "!" + "Ваше обращение начали рассматривать.");
                return message;

            case ACCEPT:
                message.setContent(" " + name + "!" + "Ваше обращение одобрили.");
                return message;

            case APPEALCREATE:
                message.setContent(name + "! " + "Ваше обращение успешно создано.");
                return message;

            case REJECT:
                message.setContent("Уважаемый " + name + "!" + "Ваше обращение отклонили.");
                return message;
            default:
                return  null;

        }
    }

}
