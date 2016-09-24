package extensao.ufc.br.network;

import android.content.Context;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import java.util.HashMap;

import extensao.ufc.br.model.Event;
import extensao.ufc.br.model.User;

/**
 * Created by alan on 11/22/15.
 */
public class NetworkController {

    private static final String SERVICE_BASE = "http://10.42.0.12:8080/sicewebservice/";
    private static final String LOGIN = SERVICE_BASE+"user/login";
    private static final String LOAD_EVENTS = SERVICE_BASE+"event/load";
    private static final String LOAD_SUBEVENTS = SERVICE_BASE+"subevent/load";
    private static final String PARTICIPATE = SERVICE_BASE+"/participation/insert";
    private Gson gson;

    Context context;
    public NetworkController(Context context){
        this.context = context;
        this.gson = new Gson();
    }

    public Answer doPostRequest(String url, HashMap<String, String> parameters){
        HttpRequest request = HttpRequest.post(url).form(parameters);

        Answer answer = gson.fromJson(request.body(), Answer.class);
        answer.setObject(gson.toJson(answer.getObject()));

        return answer;
    }

    public Answer login(User user){
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());

        return doPostRequest(LOGIN, parameters);
    }

    public Answer loadEvents(){
        return doPostRequest(LOAD_EVENTS, new HashMap<String, String>());
    }

    public Answer loadSubEvents(String eventId){
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("id_event", String.valueOf(eventId));

        return doPostRequest(LOAD_SUBEVENTS, parameters);
    }

    public Answer validatePresence(long subEventId, long userId) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("idUser", String.valueOf(userId));
        parameters.put("idSubEvent", String.valueOf(subEventId));

        return doPostRequest(PARTICIPATE, parameters);
    }
}
