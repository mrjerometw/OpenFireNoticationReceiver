package jerome.openfire.notifcation.receiver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.jerome.openfirenoticationreceiver.R;

import jerome.openfire.notifcation.receiver.xmpp.MessageCallback;
import jerome.openfire.notifcation.receiver.xmpp.XMPPConstants;
import jerome.openfire.notifcation.receiver.xmpp.XMPPService;
/**
 * Created by jerome on 2016/6/30.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, MessageCallback {
    private XMPPService mXMPPService;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mXMPPService = new XMPPService();
        mXMPPService.initXMPPTCPConnection();
        mXMPPService.setXMPPClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        mTextView = (TextView)findViewById(R.id.textView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                mXMPPService.login(XMPPConstants.USER_NAME, XMPPConstants.USER_PASSWORD);
                break;
            default:

                break;
        }
    }

    @Override
    public void show(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.append(msg+"\n");
            }
        });
    }

}
