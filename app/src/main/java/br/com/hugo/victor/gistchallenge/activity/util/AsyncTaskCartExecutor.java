package br.com.hugo.victor.gistchallenge.activity.util;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import br.com.hugo.victor.gistchallenge.activity.data.database.DataBase;
import br.com.hugo.victor.gistchallenge.activity.data.database.FavoriteDB;

// CLASSE QUE EXECUTA ALGUMAS FUNÇÕES DO BANCO DE DADOS ASSINCRONAMENTE
public class AsyncTaskCartExecutor extends AsyncTask<Void, Void, Boolean> {

    // DECLARAÇÃO DE VARIÁVEIS
    @SuppressLint("StaticFieldLeak")
    private Context mContext;
    private FavoriteDB mFavoriteDB;
    private String mMethod;
    @SuppressLint("StaticFieldLeak")
    private MaterialFavoriteButton mfbFavorite;

    public AsyncTaskCartExecutor(Context mContext, FavoriteDB mFavoriteDB, String mMethod, MaterialFavoriteButton mfbFavorite) {
        this.mContext = mContext;
        this.mFavoriteDB = mFavoriteDB;
        this.mMethod = mMethod;
        this.mfbFavorite = mfbFavorite;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        // INICIALIZANDO UMA INSTANCIA DO BANCO DE DADOS
        DataBase db = Room.databaseBuilder(mContext, DataBase.class, "favorites").build();

        // PARA CADA MÉTODO EXECUTA UMA AÇÃO DIFERENTE NO BANCO E RETORNA OS DADOS
        switch (mMethod) {
            case "showOne":
                try {
                    FavoriteDB fav = db.favoriteDAO().showFavoriteByOwner(mFavoriteDB.getOwner());
                    return fav != null;
                } catch (Exception error) {
                    Log.e("Error", "Error at showOne in database in " + getClass().getName() + ". " + error.getMessage());
                    return false;
                }
            case "insert":
                try {
                    FavoriteDB fav = db.favoriteDAO().showFavoriteByOwner(mFavoriteDB.getOwner());
                    if (fav == null){
                        db.favoriteDAO().insertFavorite(mFavoriteDB);
                    }
                    return true;
                } catch (Exception error) {
                    Log.e("Error", "Error at insert in database in " + getClass().getName() + ". " + error.getMessage());
                    return false;
                }
            case "delete":
                try {
                    db.favoriteDAO().deleteFavorite(mFavoriteDB);
                    return false;
                } catch (Exception error) {
                    Log.e("Error", "Error at delete in database in " + getClass().getName() + ". " + error.getMessage());
                    return false;
                }
            default:
                return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (!mMethod.equals("showOne")) {
            if (aBoolean) {
                mfbFavorite.setFavorite(true);
            } else {
                mfbFavorite.setFavorite(false);
            }
        } else {
            if (aBoolean) {
                mfbFavorite.setFavorite(true);
            }
        }

    }

}
