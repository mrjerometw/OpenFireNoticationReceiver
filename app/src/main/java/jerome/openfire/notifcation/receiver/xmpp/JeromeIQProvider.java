package jerome.openfire.notifcation.receiver.xmpp;

import org.jivesoftware.smack.provider.IntrospectionProvider;


/**
 * Created by jerome on 2016/6/30.
 */
public class JeromeIQProvider extends IntrospectionProvider.IQIntrospectionProvider<JeromeIQ> {

    public JeromeIQProvider() {
        super(JeromeIQ.class);
    }

}

