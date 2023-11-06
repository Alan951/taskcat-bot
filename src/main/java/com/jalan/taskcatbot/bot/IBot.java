package com.jalan.taskcatbot.bot;

public interface IBot {

    public Object getBot();

    public void init() throws Exception;

    public void start() throws Exception;

    public void stop();

    public void sendMessage(User user, String message);

    public default void sendAssists(Object assists, User user) {
        //do nothing
    }

    public boolean isEnabled();

}
