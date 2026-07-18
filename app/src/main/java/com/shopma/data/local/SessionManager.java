package com.shopma.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "shopma_session";
    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveSession(String token, long userId, String name, String email) {
        prefs.edit()
                .putString("jwt", token)
                .putLong("user_id", userId)
                .putString("name", name)
                .putString("email", email)
                .apply();
    }

    public String  getToken()   { return prefs.getString("jwt", null); }
    public long    getUserId()  { return prefs.getLong("user_id", -1); }
    public String  getName()    { return prefs.getString("name", ""); }
    public String  getEmail()   { return prefs.getString("email", ""); }
    public boolean isLoggedIn() { return getToken() != null; }

    public void logout() {
        prefs.edit().clear().apply();
    }
}
