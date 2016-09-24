package extensao.ufc.br.providers;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuth2Authorization;
import com.github.scribejava.core.oauth.OAuth20Service;

import java.io.IOException;

/**
 * Created by gustavo on 9/1/16.
 */
public class OAuthProvider {
    private static final String API_KEY = "";
    private static final String API_SECRET = "";
    public final OAuth20Service service;

    public OAuthProvider() {
        service = new ServiceBuilder()
                .apiKey(API_KEY)
                .apiSecret(API_SECRET)
                .build(SiceApi.instance());
    }

    public OAuth2AccessToken getAccessToken(String uname, String password) {
        try {
            return service.getAccessTokenPasswordGrant(uname, password);
        } catch (IOException e) {
            return null;
        }
    }

    public static class SiceApi extends DefaultApi20 {

        private static final String AUTHORIZE_URL = "http://localhost:8080/oauth/authorize?token=%s";

        protected SiceApi() { }

        private static class InstanceHolder { private static final SiceApi INSTANCE = new SiceApi(); }

        public static SiceApi instance() { return InstanceHolder.INSTANCE; }

        @Override
        public String getAccessTokenEndpoint() { return "http://localhost:8080/oauth/access_token"; }

        @Override
        protected String getAuthorizationBaseUrl() { return this.AUTHORIZE_URL; }
    }
}
