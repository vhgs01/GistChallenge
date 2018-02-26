package br.com.hugo.victor.gistchallenge.activity.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import br.com.hugo.victor.gistchallenge.activity.data.models.GistFile;
import br.com.hugo.victor.gistchallenge.activity.data.models.GistFileObject;

public class GistMap implements JsonDeserializer<GistFileObject> {

    @Override
    public GistFileObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        // DECLARAÇÃO DE VARIÁVEIS
        JsonObject jsonObject = (JsonObject) json;
        GistFileObject obj = new GistFileObject();
        obj.gists = new ArrayList<>();

        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();

        // MAPEANDO PARA CADA ELEMENTO DO JSON
        for (Map.Entry<String, JsonElement> entry: entries) {
            JsonObject gistFileObject = (JsonObject) entry.getValue();
            GistFile file = new GistFile();

            file.filename = getValue(gistFileObject, "filename");
            file.language = getValue(gistFileObject, "language");
            file.raw_url = getValue(gistFileObject, "raw_url");

            obj.gists.add(file);
        }

        return obj;
    }

    private String getValue(JsonObject obj, String key){
        // RETORNA O VALOR CONTIDO NA CHAVE
        try{
            return obj.get(key).getAsString();
        }catch (Exception e) {
            return "";
        }
    }

}
