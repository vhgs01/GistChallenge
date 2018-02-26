package br.com.hugo.victor.gistchallenge.activity.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteDB {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "owner")
    private String owner;

    @ColumnInfo(name = "avatar")
    private String avatar;

    @ColumnInfo(name = "filename")
    private String filename;

    @ColumnInfo(name = "language")
    private String language;

    @ColumnInfo(name = "rawurl")
    private String rawurl;

    public FavoriteDB() {}

    public FavoriteDB(int id, String owner, String avatar, String filename, String language, String rawurl) {
        this.id = id;
        this.owner = owner;
        this.avatar = avatar;
        this.filename = filename;
        this.language = language;
        this.rawurl = rawurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getRawurl() {
        return rawurl;
    }

    public void setRawurl(String rawurl) {
        this.rawurl = rawurl;
    }
}
