package extensao.ufc.br.sice;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import extensao.ufc.br.bases.RequestActivity;
import extensao.ufc.br.interfaces.AnswerProcessor;
import extensao.ufc.br.interfaces.RequestReceiver;
import extensao.ufc.br.interfaces.RequestSender;
import extensao.ufc.br.model.User;
import extensao.ufc.br.network.Answer;
import extensao.ufc.br.providers.MessagesProvider;
import extensao.ufc.br.providers.UserProvider;

public class LoginActivity extends RequestActivity {

    private static final int SHOW_EVENTS = 1;
    EditText loginEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadComponents();
        testUserAlreadySaved();
    }

    public void testUserAlreadySaved(){
        User user = UserProvider.getUser(this);
        if(user != null){
            doLogin(user);
        }
    }

    @Override
    public void loadComponents() {
        super.loadComponents();

        this.loginEditText = (EditText) findViewById(R.id.activity_login_email);
        this.passwordEditText = (EditText) findViewById(R.id.activity_login_password);
    }

    public void loginClick(View view){
        String email = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        User user = new User(email, password);
        doLogin(user);
    }

    public void doLogin(final User user){
        doRequest(new RequestSender() {
                      @Override
                      public Object run() {
                          Answer answer = getNetworkController().login(user);
                          return answer;
                      }
                  },
                new RequestReceiver() {
                    @Override
                    public void run(Object object) {
                        processRequest(new AnswerProcessor(object) {
                            @Override
                            public void onInternalError(Answer answer) {

                            }

                            @Override
                            public void onError(Answer answer) {
                                MessagesProvider.showErrorMessage(LoginActivity.this, getString(R.string.email_password_incorrect));
                            }

                            @Override
                            public void onSuccess(Answer answer) {
                                User user = gson.fromJson(answer.getObject().toString(), User.class);
                                UserProvider.save(user, LoginActivity.this);

                                Intent intent = new Intent(LoginActivity.this, EventsActivity.class);
                                startActivityForResult(intent, SHOW_EVENTS);
                            }

                            @Override
                            public void onRequestFailure() {

                            }
                        });
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SHOW_EVENTS){
            if(data != null && data.getBooleanExtra("logout", false)){

            }else{
                finish();
            }
        }
    }
}
