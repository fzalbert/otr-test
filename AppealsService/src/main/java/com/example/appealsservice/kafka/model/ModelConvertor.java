package com.example.appealsservice.kafka.model;

public class ModelConvertor {

    public static ModelMessage Convert(String email, String name, String subject, MessageType type)
    {
        ModelMessage message = new ModelMessage();
        message.setEmail(email);
        message.setName(name);
        message.setSubject(subject);

        switch (type)    {
            case TakeAppeal:
                message.setContent("Уважаемый " + name + "!" + "Ваше обращение начали рассматривать.");
                return message;

            case Accept:
                message.setContent("Уважаемый " + name + "!" + "Ваше обращение одобрили.");
                return message;

            case NeedUpdate:
                message.setContent("Уважаемый " + name + "!" + "Ваше обращение необходимо переделать.");
                return message;

            case Reject:
                message.setContent("Уважаемый " + name + "!" + "Ваше обращение отклонили.");
                return message;
            default:
                return  null;

        }
    }

}
