package extensao.ufc.br.sice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import extensao.ufc.br.adapters.EventListAdapter;
import extensao.ufc.br.bases.RequestActivity;
import extensao.ufc.br.interfaces.AnswerProcessor;
import extensao.ufc.br.interfaces.RequestReceiver;
import extensao.ufc.br.interfaces.RequestSender;
import extensao.ufc.br.model.Event;
import extensao.ufc.br.model.SubEvent;
import extensao.ufc.br.model.User;
import extensao.ufc.br.network.Answer;
import extensao.ufc.br.providers.MessagesProvider;

public class SubEventsActivity extends RequestActivity {

    ListView subEventsListView;
    String eventId;
    long subEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_events);

        this.eventId = getIntent().getStringExtra("id_event");
        loadComponents();
        loadEvents();
    }

    @Override
    public void loadComponents() {
        super.loadComponents();
        this.subEventsListView = (ListView) findViewById(R.id.activity_subevents_list);
    }

    public void loadEvents(){
        doRequest(new RequestSender() {
                      @Override
                      public Object run() {
                          return getNetworkController().loadSubEvents(eventId);
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

                            }

                            @Override
                            public void onSuccess(Answer answer) {
                                List<Event> events = gson.fromJson(answer.getObject().toString(), new TypeToken<List<SubEvent>>() {
                                }.getType());

                                subEventsListView.setAdapter(new EventListAdapter(SubEventsActivity.this, events));

                                subEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    subEventId = id;
                                        scanQRCode();

                                    }
                                });
                            }

                            @Override
                            public void onRequestFailure() {

                            }
                        });
                    }
                });
    }

    public void scanQRCode(){
        IntentIntegrator intent = new IntentIntegrator(SubEventsActivity.this);
        intent.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            final long userId = Long.parseLong(result.getContents());

            doRequest(new RequestSender() {
                @Override
                public Object run() {
                    return getNetworkController().validatePresence(subEventId, userId);
                }
            }, new RequestReceiver() {
                @Override
                public void run(Object object) {
                    processRequest(new AnswerProcessor(object) {
                        @Override
                        public void onInternalError(Answer answer) {

                        }

                        @Override
                        public void onError(Answer answer) {

                        }

                        @Override
                        public void onSuccess(Answer answer) {
                            User user = gson.fromJson(answer.getObject().toString(), User.class);
                            MessagesProvider.showSuccessMessage(SubEventsActivity.this, user.getEmail()+" inserted successfully!");
                        }

                        @Override
                        public void onRequestFailure() {

                        }
                    });
                }
            });
        }
    }
}
