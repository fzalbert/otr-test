package com.example.appealsservice.kafka.model;

public class ModelConvertor {

    public static ModelMessage Convert(String email, String name, String id, String subject, MessageType type)
    {
        ModelMessage message = new ModelMessage();
        message.setEmail(email);
        message.setName(name);
        message.setSubject(subject);

        switch (type)    {
            case TAKEAPPEAL:
                message.setContent(name +" " + "!" + "Ваше обращение "+ id + "начали рассматривать.");
                return message;

            case ACCEPT:
                message.setContent(" " + name + "!" + "Ваше обращение "+ id + " одобрили.");
                return message;

            case APPEALCREATE:
                message.setContent(name + "! " + "Ваше обращение "+ id + "успешно создано.");
                return message;

            case REJECT:
                message.setContent(name + " " + "!" + "Ваше обращение "+ id + " отклонили.");
                return message;

            case UPDATE:
                message.setContent( name + " " + "!" + "Ваше обращение"+ id + " обновили.");
                return message;
            default:
                return  null;

        }
    }

}
