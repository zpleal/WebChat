package web.chat.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sun.jndi.toolkit.ctx.AtomicDirContext;
import web.chat.client.ChatService;
import web.chat.shared.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServiceImpl extends RemoteServiceServlet implements ChatService {
    Map<String, ArrayList<Message>> toDeliver = new HashMap<>();

    @Override
    public boolean enter(String nick) {
        ArrayList<Message> messages = new ArrayList<Message>();

        if(nick == null || "".equals(nick) || toDeliver.containsKey(nick))
            return false;
        else {
            toDeliver.put(nick, messages);
            return true;
        }
    }

    @Override
    public void leave(String nick) {
        toDeliver.remove(nick);
    }

    @Override
    synchronized public void say(String nick, String text) {
        Message message = new Message(nick,text);

        for(String to: toDeliver.keySet()) {
            List<Message> messages = toDeliver.get(to);

            messages.add(message);
        }

    }

    @Override
    public List<Message> getMessages(String nick) {
        if(toDeliver.containsKey(nick)) {
            List<Message> messages = toDeliver.get(nick);
            List<Message> copy = new ArrayList<>(messages);

            messages.clear();
            return copy;
        } else {
            return null;
        }
    }
}