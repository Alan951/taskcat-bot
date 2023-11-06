package com.jalan.taskcatbot.bot.handlers;

import com.jalan.taskcatbot.bot.IBot;
import com.jalan.taskcatbot.bot.User;

public class RandomReplyHandler implements IHandler {

    public void handle(IBot bot, User user, String message) {
        bot.sendMessage(user, "Lo siento, no te entendí. ¿Qué quieres decir?");
    }

    public String getName() {
        return null;
    }
    
}
