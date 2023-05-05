package web.chat.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import web.chat.shared.Message;

import java.util.List;

@RemoteServiceRelativePath("ChatService")
public interface ChatService extends RemoteService {

    boolean enter(String nick);

    void leave(String nick);

    void say(String nick,String text);

    List<Message> getMessages(String nick);


    /**
     * Utility/Convenience class.
     * Use ChatService.App.getInstance() to access static instance of ChatServiceAsync
     */
    public static class App {
        private static ChatServiceAsync ourInstance = GWT.create(ChatService.class);

        public static synchronized ChatServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
