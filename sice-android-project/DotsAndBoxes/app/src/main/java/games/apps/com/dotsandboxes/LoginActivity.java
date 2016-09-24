package games.apps.com.dotsandboxes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import games.apps.com.dotsandboxes.activities.NetworkActivity;
import games.apps.com.dotsandboxes.model.Player;
import games.apps.com.dotsandboxes.providers.PlayerProvider;
import games.apps.com.dotsandboxes.requests.LoginRequest;

public class LoginActivity extends NetworkActivity {

    private EditText usernameEdit;
    private EditText passwordEdit;
    private static final int GAMES_CALL = 1;
    public static final String LOGOUT_CALL = "LOGOUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadComponents();
        checkPlayerLogged();
    }

    public void loadComponents(){
        this.usernameEdit = (EditText) findViewById(R.id.activity_login_username);
        this.passwordEdit = (EditText) findViewById(R.id.activity_login_password);
    }

    public void signIn(View view){
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        doRequest(new LoginRequest(new Player(username, password)), new NetworkRequest() {

            @Override
            public void onSuccess(String answer) {
                ArrayList<Player> res = new Gson().fromJson(answer, new TypeToken<ArrayList<Player>>() {}.getType());
                if(res.size() > 0){
                    PlayerProvider.savePlayer(LoginActivity.this, res.get(0));
                    checkPlayerLogged();
                }else{
                    Toast.makeText(LoginActivity.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkPlayerLogged(){
        if(PlayerProvider.getPlayer(LoginActivity.this) != null){
            Intent intent = new Intent(LoginActivity.this, GamesActivity.class);
            startActivityForResult(intent, GAMES_CALL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GAMES_CALL){
            if(data.getBooleanExtra(LOGOUT_CALL, false)){
                PlayerProvider.clear(LoginActivity.this);
            }
        }
    }
}
