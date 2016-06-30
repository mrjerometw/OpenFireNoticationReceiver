package jerome.openfire.notifcation.receiver.xmpp;

import android.util.Log;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Jerome
 * Date on 2016/6/30.
 */
public class XMPPService {

    private final String TAG = "jerome";
    public AbstractXMPPConnection mXMPPTCPConnection;
    private SSLContext mSSLContext;

    public AbstractXMPPConnection initXMPPTCPConnection() {
        SmackConfiguration.DEBUG = true;
        try {
            mSSLContext = SSLContext.getInstance("TLS");
            mSSLContext.init(null, new TrustManager[]{new MyTrustManager()}, null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("connecting failed", e);
        }

        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        builder.setHost(XMPPConstants.HOST);
        builder.setServiceName(XMPPConstants.SERVICE_NAME);
        builder.setPort(XMPPConstants.PORT);
        builder.setCompressionEnabled(false);
        builder.setSecurityMode(ConnectionConfiguration.SecurityMode.required);
        builder.setCustomSSLContext(mSSLContext);
        SASLAuthentication.unBlacklistSASLMechanism("SCRAM-SHA-1");
        SASLAuthentication.unBlacklistSASLMechanism("PLAIN");
        SASLAuthentication.unBlacklistSASLMechanism("DIGEST-MD5");
        mXMPPTCPConnection = new XMPPTCPConnection(builder.build());
        return mXMPPTCPConnection;
    }



    public void login(final String userName, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String loginMsg;
                try {
                    if (!mXMPPTCPConnection.isConnected())
                        mXMPPTCPConnection.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                    mXMPPTCPConnection.disconnect();
                }
                Log.i("wxl", "XMPPService login connected=" + mXMPPTCPConnection.isConnected());
                if (mXMPPTCPConnection.isConnected()) {
                    try {
                        mXMPPTCPConnection.login(userName, password);

                        if (mXMPPTCPConnection.isAuthenticated())
                        {
                            ProviderManager.addIQProvider(XMPPConstants.ELEMENT, XMPPConstants.NAMESPACE, new JeromeIQProvider());
                            StanzaFilter filter = new AndFilter(new StanzaTypeFilter(IQ.class));
                            StanzaListener myListener = new StanzaListener() {
                                @Override
                                public void processPacket(Stanza packet) throws SmackException.NotConnectedException {
                                    if (packet instanceof JeromeIQ)
                                    {
                                        JeromeIQ iq = (JeromeIQ)packet;
                                        System.out.println("iq packet = [" + iq.getMessage()+ "]");

                                    }
                                }
                            };
                            mXMPPTCPConnection.addAsyncStanzaListener(myListener, filter);
                            loginMsg = "Login Sucessful";
                        } else {
                            loginMsg = "Login Failed";
                        }

                    } catch (Exception e) {
                        e.printStackTrace();//
                        loginMsg = "Login Failed=" + e.getMessage();
                    }
                } else {
                    loginMsg = "connect failed";
                }
                Log.i(TAG, loginMsg);
                mMessageCallback.show(loginMsg);
            }
        }).start();
    }



    private class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
                throws CertificateException {
            Log.i(TAG, "checkClientTrusted:" + s);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
                throws CertificateException {
            Log.i(TAG, "checkServerTrusted:" + s);
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            Log.i(TAG, "getAcceptedIssuers");
            return new X509Certificate[0];
        }

    }

    public MessageCallback mMessageCallback;

    public void setXMPPClickListener(MessageCallback xmppClickListener) {
        this.mMessageCallback = xmppClickListener;
    }

}
