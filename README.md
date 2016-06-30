# OpenFireNoticationReceiver
If you want to receive message from openfire server. You must have to do ..

1. You have to setup a openfire server.

2. Download the operfire plugin for notifacation. (from https://github.com/mrjerometw/openfire-push-notification-plugin)

In Java Code: 
You want to receive the XMPP Payload from openfire sever side.
1. You must have a the subclass of IQ class (ex: JeromeIQ.java) and a subclass of IQIntrospectionProvider (ex:JeromeIQProvider.java)
2. addAsyncStanzaListener in your AbstractXMPPConnection class, (XMPPService.java)
