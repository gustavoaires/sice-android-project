package extensao.ufc.br.providers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alan on 11/22/15.
 */
public class Formatters {

    public static String getFormattedString(Date date) {
        if(date == null)
            return null;
        try{
            return new SimpleDateFormat("HH:mm dd/MM/yyyy").format(date);
        }catch (Exception e){
            return null;
        }
    }

}
