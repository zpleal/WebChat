package web.chat.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import web.chat.shared.Message;

import java.util.List;

public interface ChatServiceAsync {

    void enter(String nick, AsyncCallback<Boolean> async);

    void leave(String nick, AsyncCallback<Void> async);

    void say(String nick, String text, AsyncCallback<Void> async);

    void getMessages(String nick, AsyncCallback<List<Message>> async);
}
