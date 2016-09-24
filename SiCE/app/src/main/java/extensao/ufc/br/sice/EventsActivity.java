package extensao.ufc.br.sice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;

import net.glxn.qrgen.android.QRCode;
import net.glxn.qrgen.core.image.ImageType;

import java.util.ArrayList;
import java.util.List;

import extensao.ufc.br.adapters.EventListAdapter;
import extensao.ufc.br.bases.RequestActivity;
import extensao.ufc.br.interfaces.AnswerProcessor;
import extensao.ufc.br.interfaces.RequestReceiver;
import extensao.ufc.br.interfaces.RequestSender;
import extensao.ufc.br.model.Event;
import extensao.ufc.br.model.User;
import extensao.ufc.br.network.Answer;
import extensao.ufc.br.providers.AnimationProvider;
import extensao.ufc.br.providers.UserProvider;

public class EventsActivity extends RequestActivity {

    ListView eventsListView;
    ImageView qrCodeImageView;
    RelativeLayout qrCodeContainer;

    boolean showingImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        loadComponents();
        loadEvents();
    }

    @Override
    public void loadComponents() {
        super.loadComponents();
        this.eventsListView = (ListView) findViewById(R.id.activity_events_list);
        this.qrCodeContainer = (RelativeLayout) findViewById(R.id.activity_events_qrcode_container);
        this.qrCodeImageView = (ImageView) findViewById(R.id.activity_events_qrcode_image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.events_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.events_menu_generate_qr_code){
            generateQRCode();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadEvents(){
        doRequest(new RequestSender() {
                      @Override
                      public Object run() {
                          return getNetworkController().loadEvents();
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
                                List<Event> events = gson.fromJson(answer.getObject().toString(), new TypeToken<List<Event>>(){}.getType());

                                eventsListView.setAdapter(new EventListAdapter(EventsActivity.this, events));

                                eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        if(!showingImage){
                                            Intent intent = new Intent(EventsActivity.this, SubEventsActivity.class);
                                            intent.putExtra("id_event", String.valueOf(id));

                                            startActivity(intent);
                                        }
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

    public void generateQRCode(){
        User user = UserProvider.getUser(EventsActivity.this);

        Bitmap mBitmap = BitmapFactory.decodeFile(QRCode.from(String.valueOf(user.getId())).to(ImageType.PNG).withSize(500, 500).file().getAbsolutePath());

        qrCodeImageView.setImageBitmap(mBitmap);
        showingImage = true;

        AnimationProvider.doInAnimation(qrCodeContainer);
    }

    @Override
    public void onBackPressed() {
        if(showingImage){
            showingImage = false;
            AnimationProvider.doOutAnimation(qrCodeContainer);
        }else {
            super.onBackPressed();
        }
    }
}
