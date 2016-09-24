package extensao.ufc.br.model;

import com.github.scribejava.core.model.OAuth2AccessToken;

/**
 * Created by alan on 11/22/15.
 */
public class User {

    public enum Levels{PARTICIPANT, ADMIN};

    private long id;
    private String email;
    private String password;
    private Levels level;
    private OAuth2AccessToken token;

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Levels getLevel() {
        return level;
    }

    public void setLevel(Levels level) {
        this.level = level;
    }

    public OAuth2AccessToken getToken() { return token; }

    public void setToken(OAuth2AccessToken token) { this.token = token; }
}
