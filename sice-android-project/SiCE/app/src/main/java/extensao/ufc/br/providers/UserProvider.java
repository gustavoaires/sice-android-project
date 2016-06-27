package extensao.ufc.br.providers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import extensao.ufc.br.model.User;

/**
 * Created by alan on 11/22/15.
 */
public class UserProvider {

    public static void save(User user, Context context){
        SharedPreferences.Editor edit = SettingsProvider.getEditor(context);
        edit.putString("user", new Gson().toJson(user));

        edit.commit();
    }

    public static User getUser(Context context){
        SharedPreferences preferences = SettingsProvider.getPreferences(context);
        return new Gson().fromJson(preferences.getString("user", null), User.class);
    }

}
