package jerome.openfire.notifcation.receiver.xmpp;

import org.jivesoftware.smack.packet.IQ;

/**
 * Created by jerome on 2016/6/30.
 */
public class JeromeIQ extends IQ {

    private String mID = "";
    private String mApiKey = "";
    private String mTitle = "";
    private String mMessage = "";
    private String mURI = "";
    public JeromeIQ() {
        super(XMPPConstants.ELEMENT, XMPPConstants.NAMESPACE);
    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder buf) {
        buf.rightAngleBracket();

//        if (utc != null) {
//            buf.append("<utc>").append(utc).append("</utc>");
//            buf.append("<tzo>").append(tzo).append("</tzo>");
//        } else {
        buf.setEmptyElement();
//        }

        return buf;
    }

    public String getId() {
        return mID;
    }

    public void setId(String id) {
        mID = id;
    }

    public String getApiKey()
    {
        return mApiKey;
    }

    public void setApiKey(String apiKey)
    {
        mApiKey = apiKey;
    }

    public String getTitle()
    {
        return mTitle;
    }
    public void setTitle(String title)
    {
        mTitle = title;
    }
    public String getMessage()
    {
        return mMessage;
    }
    public void setMessage(String message)
    {
        mMessage = message;
    }
    public String getUri()
    {
        return mURI;
    }
    public void setUri(String uri)
    {
        mURI = uri;
    }
}
