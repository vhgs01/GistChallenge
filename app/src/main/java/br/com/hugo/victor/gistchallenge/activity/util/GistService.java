package br.com.hugo.victor.gistchallenge.activity.util;

import java.util.List;

import br.com.hugo.victor.gistchallenge.activity.data.models.Gist;
import retrofit2.Call;
import retrofit2.http.GET;

// INTERFACE RESPONSÁVEL PELOS MÉTODOS USADOS NO RETROFIT
public interface GistService {

    // DECLARAÇÃO DE VARIÁVEIS
    String PER_PAGE = "100";
    String BASE_URL = "https://api.github.com/";

    // MÉTODO DE REQUEST
    @GET("gists/public?per_page=" + PER_PAGE)
    Call<List<Gist>> listGists();

}
