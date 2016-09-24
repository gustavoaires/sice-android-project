package extensao.ufc.br.providers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by alan on 11/22/15.
 */
public class Formatters {

    public static String getFormattedString(Date date) {
        if(date == null)
            return null;
        try {
            return new SimpleDateFormat("HH:mm dd/MM/yyyy").format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Format the url adding parameters to get the protected resource
     * @param url to protected resource
     * @param parameters used to define the protected resource
     * @return new url with parameters
     */
    public static String getFormattedString(String url, HashMap<String, String> parameters) {
        try {
            if (url == null)
                throw new NullPointerException("URL não pode ser nula");
            Set set = parameters.entrySet();
            Iterator iterator = set.iterator();
            url += "?";
            while (iterator.hasNext()) {
                Map.Entry p = (Map.Entry) iterator.next();
                url += p.getKey() + "=" + p.getValue() + (iterator.hasNext() ? "&" : "");
            }
            return url;
        } catch (Exception e) {
            return null;
        }
    }

}
