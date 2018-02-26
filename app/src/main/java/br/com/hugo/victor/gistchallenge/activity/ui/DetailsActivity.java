package br.com.hugo.victor.gistchallenge.activity.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import br.com.hugo.victor.gistchallenge.R;
import br.com.hugo.victor.gistchallenge.activity.data.database.FavoriteDB;
import br.com.hugo.victor.gistchallenge.activity.data.models.GistCatalog;
import br.com.hugo.victor.gistchallenge.activity.util.AsyncTaskCartExecutor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    // DECLARAÇÃO DE VARIÁVEIS
    GistCatalog mGist;

    // BIND DOS ELEMENTOS
    @BindView(R.id.tvOwner)
    TextView mTvOwner;
    @BindView(R.id.tvLanguage)
    TextView tvLanguage;
    @BindView(R.id.ivGistImage)
    ImageView mIvGistImage;
    @BindView(R.id.llHead)
    LinearLayout mLlHead;
    @BindView(R.id.wvDetails)
    WebView mWvDetails;
    @BindView(R.id.svDetails)
    ScrollView mSvDetails;
    @BindView(R.id.mfbFavorite)
    MaterialFavoriteButton mfbFavorite;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // INICIALIZAÇÃO DO BUTTER KNIFE
        ButterKnife.bind(this);

        // PEGA OS VALORES QUE ESTÃO NO EXTRA DA INTENT PASSADA
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }

        mGist = (GistCatalog) (extras != null ? extras.getSerializable("DETAILS") : null);
        if (mGist == null) {
            finish();
        }

        // PREENCHENDO OS DADOS NA VIEW
        String ownerName = getString(R.string.privateName);
        String gistLanguage = getString(R.string.unknownLanguage);

        if (mGist.owner != null) {
            ownerName = mGist.owner.login;

            if (!TextUtils.isEmpty(mGist.owner.avatar)) {
                Picasso.with(this).load(Uri.parse(mGist.owner.avatar)).fit().into(mIvGistImage);
            }
        }

        if (!mGist.gist.language.isEmpty()) {
            gistLanguage = mGist.gist.language;
        }

        tvLanguage.setText(gistLanguage);
        mTvOwner.setText(ownerName);

        mSvDetails.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mSvDetails.getViewTreeObserver().removeOnPreDrawListener(this);
                mSvDetails.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        int scrollY = mSvDetails.getScrollY();
                        mLlHead.setY((-scrollY + mLlHead.getY()) * 0.5f);
                    }
                });

                return false;
            }
        });

        // PEGA OS DADOS DA URL E COLOCA NO ELEMENTO WEBVIEW
        mWvDetails.getSettings().setJavaScriptEnabled(true);
        mWvDetails.loadUrl(mGist.gist.raw_url);
        mWvDetails.getSettings().setLoadWithOverviewMode(true);
        mWvDetails.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWvDetails.setScrollContainer(false);

        // SE ACHAR NA TABELA DE FAVORITOS DEFINE COMO FAVORITO
        // INSTANCIANDO E EXECUTANDO UMA ASYNCTASK
        try {
            AsyncTaskCartExecutor task = new AsyncTaskCartExecutor(getApplicationContext(), createFavoriteInstance(), "showOne", mfbFavorite);
            task.execute();
        }  catch (Exception error) {
            Log.e("Error", "Error at call AssyncTaskExecutor in " + getClass().getName() + ". " + error.getMessage());
        }

        // CRIANDO UM LISTENER PARA O CLICK DO FAVORITO
        mfbFavorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        // SE NÃO FOR FAVORITO ELE FAVORITA E SALVA NO BANCO COMO FAVORITO SE FOR FAVORITO
                        // ELE DESFAVORITA E REMOVE DO BANCO
                        if (mfbFavorite.isFavorite()) {
                            // INSTANCIANDO E EXECUTANDO UMA ASYNCTASK
                            AsyncTaskCartExecutor task = new AsyncTaskCartExecutor(getApplicationContext(), createFavoriteInstance(), "insert", mfbFavorite);
                            task.execute();
                        } else {
                            // INSTANCIANDO E EXECUTANDO UMA ASYNCTASK
                            AsyncTaskCartExecutor task = new AsyncTaskCartExecutor(getApplicationContext(), createFavoriteInstance(), "delete", mfbFavorite);
                            task.execute();
                        }
                    }
                });

    }

    public FavoriteDB createFavoriteInstance(){
        // DECLARAÇÃO DE VARIÁVEIS
        FavoriteDB fav = new FavoriteDB();

        // SETA OS VALORES NO OBJETO DE TIPO FAVORITEDB
        if (mGist.owner != null) {
            fav.setOwner(mGist.owner.login);
            fav.setAvatar(mGist.owner.avatar);
        } else {
            fav.setOwner(getString(R.string.privateName));
        }

        fav.setFilename(mGist.gist.filename);
        fav.setLanguage(mGist.gist.language);
        fav.setRawurl(mGist.gist.raw_url);

        return fav;
    }
}
