package extensao.ufc.br.bases;

import android.net.Network;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import extensao.ufc.br.interfaces.AnswerProcessor;
import extensao.ufc.br.interfaces.RequestReceiver;
import extensao.ufc.br.interfaces.RequestSender;
import extensao.ufc.br.network.NetworkController;
import extensao.ufc.br.providers.AnimationProvider;
import extensao.ufc.br.sice.R;

/**
 * Created by alan on 11/22/15.
 */
public abstract class RequestActivity extends AppCompatActivity {

    protected NetworkController networkController;
    protected RelativeLayout containerView;
    protected RelativeLayout loadingView;
    protected Gson gson;

    Thread mThread;
    Handler mHandler;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.request_layout);

        loadComponents();

        LayoutInflater inf = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inf.inflate(layoutResID, null);

        containerView.addView(view, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        loadVariables();
    }

    public void loadVariables() {
        this.mHandler = new Handler();
        this.networkController = new NetworkController(this);
        this.gson = new Gson();
    }

    public void loadComponents() {
        this.containerView = (RelativeLayout) findViewById(R.id.request_layout_container_view);
        this.loadingView = (RelativeLayout) findViewById(R.id.request_layout_progress_container);
    }

    public void doRequest(final RequestSender sender, final RequestReceiver receiver) {
        startLoading();
        mThread = new Thread() {
            @Override
            public void run() {
                final Object result = sender.run();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        receiver.run(result);
                    }
                });
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        stopLoading();
                    }
                });
            }
        };

        mThread.start();
    }

    public void startLoading(){
        AnimationProvider.doInAnimation(loadingView);
    }

    public void stopLoading(){
        AnimationProvider.doOutAnimation(loadingView);
    }

    public void processRequest(AnswerProcessor processor){
        processor.run();
    }

    public NetworkController getNetworkController(){
        return networkController;
    }

}
