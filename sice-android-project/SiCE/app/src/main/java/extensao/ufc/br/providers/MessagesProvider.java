package extensao.ufc.br.providers;

import android.app.Activity;
import android.content.Context;

import com.devspark.appmsg.AppMsg;

/**
 * Created by alan on 11/22/15.
 */
public class MessagesProvider {
    public static void showErrorMessage(Activity context, String message){
        AppMsg.makeText(context, message, AppMsg.STYLE_ALERT).show();
    }

    public static void showSuccessMessage(Activity context, String message){
        AppMsg.makeText(context, message, AppMsg.STYLE_CONFIRM).show();
    }
}
