package kg.geektech.anroid3.lesson_3.data.model;

import android.content.Context;

public class User {
    private String name;
    private String token;

    public User(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
