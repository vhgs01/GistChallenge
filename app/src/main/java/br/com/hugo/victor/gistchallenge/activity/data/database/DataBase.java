package br.com.hugo.victor.gistchallenge.activity.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.hugo.victor.gistchallenge.activity.data.dao.FavoriteDAO;

@Database(entities = {FavoriteDB.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    private static final String DB_NAME = "gistChallangeDB";
    private static DataBase INSTANCE;

    public abstract FavoriteDAO favoriteDAO();

    public static DataBase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DataBase.class, DB_NAME).build();
        }

        return INSTANCE;
    }

}
