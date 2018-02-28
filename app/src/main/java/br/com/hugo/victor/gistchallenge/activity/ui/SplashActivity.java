package br.com.hugo.victor.gistchallenge.activity.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import br.com.hugo.victor.gistchallenge.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    // BIND DOS ELEMENTOS
    @BindView(R.id.ivLogo)
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // INICIALIZAÇÃO DO BUTTER KNIFE
        ButterKnife.bind(this);

        // CHAMADA DA FUNÇÃO DE ANIMAÇÃO
        carregar();
    }

    public void carregar() {
        // CRIANDO UMA ANIMAÇÃO
        Animation animacao = AnimationUtils.loadAnimation(this, R.anim.animacao_splash);

        // CASO TENHA ALGUMA ANIMAÇÃO NA IMAGEM ELE LIMPA A MESMA
        ivLogo.clearAnimation();

        // INICIA UMA ANIMAÇÃO
        ivLogo.startAnimation(animacao);

        // TEMPORIZANDO E COLOCANDO A AÇÃO DE ABRIR OUTRA TELA APÓS O TEMPO DE SPLASH
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // CRIA UMA VARIÁVEL INTENT
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3500);

    }
}
