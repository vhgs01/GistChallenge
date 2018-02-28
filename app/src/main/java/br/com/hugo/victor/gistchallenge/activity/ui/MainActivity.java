package br.com.hugo.victor.gistchallenge.activity.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import br.com.hugo.victor.gistchallenge.R;
import br.com.hugo.victor.gistchallenge.activity.adapter.GistAdapter;
import br.com.hugo.victor.gistchallenge.activity.data.models.Gist;
import br.com.hugo.victor.gistchallenge.activity.data.models.GistFileObject;
import br.com.hugo.victor.gistchallenge.activity.util.GistMap;
import br.com.hugo.victor.gistchallenge.activity.util.GistService;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // DECLARAÇÃO DE VARIÁVEIS
    private List<Gist> mGists = new ArrayList<>();
    private GistAdapter mGistAdapter;

    // BIND DOS ELEMENTOS
    @BindView(R.id.rvGists)
    RecyclerView rvGists;
    @BindView(R.id.pbList)
    ProgressBar pbList;
    @BindView(R.id.tvNotGists)
    TextView tvNotGists;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentHome);
                    finish();
                    return true;
                case R.id.nav_favorite:
                    Intent intentFavorites = new Intent(getApplicationContext(), FavoritesActivity.class);
                    startActivity(intentFavorites);
                    return true;
                case R.id.nav_about:
                    Intent intentAbout = new Intent(getApplicationContext(), InfoActivity.class);
                    startActivity(intentAbout);
                    return true;
                default:
                    return false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // INICIALIZAÇÃO DO BUTTER KNIFE
        ButterKnife.bind(this);

        // ADICIONANDO UM LISTENER DE ITEM SELECIONADO
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // TRAZ OS ITENS DA API ETERNA
        getGists(this);

    }

    // CLASSE RESPONSÁVEL POR TRAZER OS PRODUTOS DA API E INFLAR O RECYCLERVIEW
    public void getGists(final Context context) {

        // CRIA UMA INSTANCIA DO RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GistService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .registerTypeAdapter(GistFileObject.class, new GistMap())
                                .create()
                        )
                ).build();

        GistService service = retrofit.create(GistService.class);
        Call<List<Gist>> requestGist = service.listGists();

        // PARA CADA ELEMENTO QUE TROUXER DA API ELE INFLA O RECYCLERVIEW
        requestGist.enqueue(new Callback<List<Gist>>() {
            @Override
            public void onResponse(@NonNull Call<List<Gist>> call, @NonNull Response<List<Gist>> response) {

                if (!response.isSuccessful()) {
                    // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
                    pbList.setVisibility(View.GONE);
                    tvNotGists.setVisibility(View.VISIBLE);
                } else {
                    // CRIA UMA LISTA DE GISTCATALOG COM O RESPONSE DO REQUEST
                    mGists = response.body();

                    // CRIA UM NOVO GISTADAPTER COLOCANDO UM LISTENER DE CLICK QUE QUANDO SELECIONAR UM ITEM COLOCA NO BUNDLE
                    // AS INFORMAÇÕES NECESSÁRIAS PARA NA OUTRA ACTIVITY CARREGAR CORRETAMENTE
                    mGistAdapter = new GistAdapter(context, mGists, new GistAdapter.OnGistClickListener() {
                        @Override
                        public void onClicked(int position) {
                            Bundle bundle = new Bundle(1);
                            bundle.putSerializable("DETAILS", mGists.get(position));

                            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                            intent.putExtras(bundle);

                            startActivity(intent);
                        }
                    });

                    // INFLA O RECYCLERVIEW COM OS GISTS
                    rvGists.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    rvGists.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                    rvGists.setAdapter(mGistAdapter);

                    // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
                    pbList.setVisibility(View.GONE);
                    rvGists.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Gist>> call, @NonNull Throwable t) {
                // SETANDO COMO VISÍVEL OU INVISÍVEL OS ELEMENTOS
                pbList.setVisibility(View.GONE);
                tvNotGists.setVisibility(View.VISIBLE);
            }
        });
    }
}
