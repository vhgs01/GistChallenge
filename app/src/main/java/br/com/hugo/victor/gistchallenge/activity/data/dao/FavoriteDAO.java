package br.com.hugo.victor.gistchallenge.activity.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import br.com.hugo.victor.gistchallenge.activity.data.database.FavoriteDB;

@Dao
public interface FavoriteDAO {

    @Query("SELECT * FROM favorites")
    FavoriteDB[] showFavorites();

    @Query("SELECT * from favorites WHERE owner = :owner")
    FavoriteDB showFavoriteByOwner(String owner);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteDB favoriteDB);

    @Delete
    void deleteFavorite(FavoriteDB favoriteDB);

}
