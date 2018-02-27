package br.com.hugo.victor.gistchallenge.activity.ui;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import br.com.hugo.victor.gistchallenge.R;
import br.com.hugo.victor.gistchallenge.activity.adapter.FavoriteAdapter;
import br.com.hugo.victor.gistchallenge.activity.data.database.DataBase;
import br.com.hugo.victor.gistchallenge.activity.data.database.FavoriteDB;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {
    // DECLARAÇÃO DE VARIÁVEIS
    private FavoriteAdapter mFavoriteAdapter;

    // BIND DOS ELEMENTOS
    @BindView(R.id.rvFavorites)
    RecyclerView rvFavorites;
    @BindView(R.id.pbListFavorites)
    ProgressBar pbListFavorites;
    @BindView(R.id.tvNotFavorites)
    TextView tvNotFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // INICIALIZAÇÃO DO BUTTER KNIFE
        ButterKnife.bind(this);

    }

    @Override
    protected void onStop() {
        super.onStop();

        // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
        pbListFavorites.setVisibility(View.VISIBLE);
        rvFavorites.setVisibility(View.GONE);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // INSTANCIANDO VARIÁVEIS
        showAllFavorites task = new showAllFavorites(this);
        FavoriteDB[] result = new FavoriteDB[0];

        // EXECUTANDO UMA ASYNCTASK E SALVANDO O RETORNO NUMA VARIÁVEL
        try {
            result = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (result.length > 0) {
            // INFLA O RECYCLERVIEW COM OS FAVORITOS
            mFavoriteAdapter = new FavoriteAdapter(this, result);
            rvFavorites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvFavorites.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            rvFavorites.setAdapter(mFavoriteAdapter);

            // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
            pbListFavorites.setVisibility(View.GONE);
            rvFavorites.setVisibility(View.VISIBLE);
        } else {
            // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
            pbListFavorites.setVisibility(View.GONE);
            tvNotFavorites.setVisibility(View.VISIBLE);
            rvFavorites.setVisibility(View.GONE);
        }

    }

    // CLASSE QUE EXECUTA UMA ASYNCTASK
    public class showAllFavorites extends AsyncTask<Void, Void, FavoriteDB[]> {
        private Context mContext;

        showAllFavorites(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected FavoriteDB[] doInBackground(Void... voids) {
            // INICIALIZANDO UMA INSTANCIA DO BANCO DE DADOS E RETORNANDO TODOS OS FAVORITOS
            DataBase db = Room.databaseBuilder(mContext, DataBase.class, "favorites").build();
            return db.favoriteDAO().showFavorites();
        }
    }
}
