package br.com.hugo.victor.gistchallenge.activity.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

public class Util {

    // CLASSE RESPONSÁVEL POR ABRIR O LIKNEDIN (NAVEGADOR OU APP)
    public static void openLinkedinProfile(Context context, String perfilId) throws Exception {
        // CRIA UMA INTENT INFORMANDO QUE DESEJA ABRIR O LINKEDIN COM O PERFIL DESEJADO
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%@" + perfilId));

        // VERIFICA SE O LINKEDIN ESTPÁ INSTALADO (APP)
        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        // SE NÃO, USA O NAVEGADOR COMO ACESSO
        if (list.isEmpty()) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=" + perfilId));
        }

        // INICIA O COMPONENTE
        context.startActivity(intent);
    }

}
