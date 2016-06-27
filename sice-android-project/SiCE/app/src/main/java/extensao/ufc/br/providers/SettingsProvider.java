package extensao.ufc.br.providers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alan on 11/22/15.
 */
public class SettingsProvider {

    public static final String PREFERENCES = "sice";

    public static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context){
        return SettingsProvider.getPreferences(context).edit();
    }

}
