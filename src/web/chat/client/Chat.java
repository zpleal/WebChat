package web.chat.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import web.chat.shared.Message;

import java.sql.Time;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Chat implements EntryPoint {



    ChatServiceAsync rpc = ChatService.App.getInstance();

    public void onModuleLoad() {
        VerticalPanel top = new VerticalPanel();

        HorizontalPanel nickPanel = new HorizontalPanel();
        TextBox nickBox = new TextBox();
        Button enterButton = new Button("Enter");
        Button leaveButton = new Button("Leave");

        VerticalPanel talk = new VerticalPanel();
        ScrollPanel scroll = new ScrollPanel(talk);

        HorizontalPanel sayPanel = new HorizontalPanel();
        TextBox sayBox = new TextBox();
        Button sayButton = new Button("Say");

        nickPanel.setSpacing(10);
        nickPanel.add(new Label("Nick:"));
        nickPanel.add(nickBox);
        nickPanel.add(enterButton);
        nickPanel.add(leaveButton);

        leaveButton.setEnabled(false);

        scroll.setSize("30em", "20em");

        sayPanel.add( sayBox);
        sayPanel.add( sayButton );
        sayPanel.setSpacing(10);

        top.add(nickPanel);
        top.add(scroll);
        top.add(sayPanel);
        top.setSpacing(10);

        sayBox.setEnabled(false);

        Style scrollStyle = scroll.getElement().getStyle();
        scrollStyle.setBorderStyle(Style.BorderStyle.SOLID);
        scrollStyle.setBorderWidth(1, Style.Unit.PX);

        enterButton.addClickHandler( e -> {
            rpc.enter(nickBox.getText(), new AsyncCallback<Boolean>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                @Override
                public void onSuccess(Boolean ok) {
                    if(ok) {
                        nickBox.setEnabled(false);
                        enterButton.setEnabled(false);
                        leaveButton.setEnabled(true);
                        sayBox.setEnabled(true);
                    } else
                        Window.alert("Invalid nick");
                }
            });
        });

        leaveButton.addClickHandler( e -> {
            rpc.leave(nickBox.getText(), new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                @Override
                public void onSuccess(Void result) {
                    nickBox.setEnabled(true);
                    enterButton.setEnabled(true);
                    leaveButton.setEnabled(false);
                    sayBox.setEnabled(true);
                }
            });
        });

        sayButton.addClickHandler( e -> {
            rpc.say(nickBox.getText(), sayBox.getText(), new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                @Override
                public void onSuccess(Void result) {
                    sayBox.setText("");
                }
            });
        });


        new Timer() {
            public void run() {
                if(sayBox.isEnabled())
                    rpc.getMessages(nickBox.getText(), new AsyncCallback<List<Message>>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                        }

                        @Override
                        public void onSuccess(List<Message> messages) {
                            if(messages != null)
                                for(Message message: messages)
                                    talk.add(new Label(message.getFrom()+":"+message.getText()));
                        }
                    });
            }
        }.scheduleRepeating(1000);


        RootPanel.get().add(top);
    }



}
