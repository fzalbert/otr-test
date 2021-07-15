package com.example.appealsservice.kafka.model;

public class AppealAppointedMessage {
    public String login;
    public long appealId;

    public AppealAppointedMessage() {}

    public AppealAppointedMessage(String login, long appealId) {
        this.login = login;
        this.appealId = appealId;
    }

    public String getLogin() {
        return login;
    }

    public long getAppealId() {
        return appealId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAppealId(long appealId) {
        this.appealId = appealId;
    }
}