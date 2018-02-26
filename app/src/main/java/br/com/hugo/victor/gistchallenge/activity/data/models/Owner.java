package br.com.hugo.victor.gistchallenge.activity.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Owner implements Serializable {

    @SerializedName("login")
    public String login;
    @SerializedName("avatar_url")
    public String avatar;

}
