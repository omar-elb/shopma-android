package com.shopma.data.remote;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import retrofit2.Response;

public class ApiErrorParser {

    public static String parse(Response<?> response) {
        try {
            if (response.errorBody() != null) {
                String json = response.errorBody().string();
                JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                if (obj.has("error")) {
                    return obj.get("error").getAsString();
                }
            }
        } catch (Exception ignored) {
        }
        return "Erreur serveur (" + response.code() + ")";
    }
}
