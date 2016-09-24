package extensao.ufc.br.network;

import android.content.Context;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import extensao.ufc.br.model.User;
import extensao.ufc.br.providers.Formatters;
import extensao.ufc.br.providers.OAuthProvider;
import extensao.ufc.br.providers.UserProvider;

/**
 * Created by alan on 11/22/15.
 */
public class NetworkController {

    private static final String SERVICE_BASE = "http://localhost:8080/sicewebservice/";
    private static final String LOGIN = SERVICE_BASE+"user/login";
    private static final String LOAD_EVENTS = SERVICE_BASE+"event/load";
    private static final String LOAD_SUBEVENTS = SERVICE_BASE+"subevent/load";
    private static final String PARTICIPATE = SERVICE_BASE+"/participation/insert";

    private Gson gson;
    private OAuthProvider mOAuthProvider;

    Context context;

    public NetworkController(Context context){
        this.context = context;
        this.gson = new Gson();
        this.mOAuthProvider = new OAuthProvider();
    }

    public Answer doPostRequest(String url, HashMap<String, String> parameters) {
        String formattedUrl = Formatters.getFormattedString(url, parameters);

        OAuthRequest request = new OAuthRequest(Verb.POST, formattedUrl, mOAuthProvider.service);
        mOAuthProvider.service.signRequest(UserProvider.getUser(context).getToken(), request);
        Response response = request.send();

        Answer answer = null;
        try {
            answer = gson.fromJson(response.getBody(), Answer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }

    public Answer login(User user) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("email", user.getEmail());
        parameters.put("password", user.getPassword());

        return doPostRequest(LOGIN, parameters);
//        Fazer autenticacao no servidor enviando email e senha
//        Pegar resultado e salvar o token no usuario
//        OAuth2AccessToken accessToken = mOAuthProvider.getAccessToken(user.getEmail(), user.getPassword());
//        user.setToken(accessToken);
    }

    public Answer loadEvents() {
        return doPostRequest(LOAD_EVENTS, new HashMap<String, String>());
    }

    public Answer loadSubEvents(String eventId) {
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
