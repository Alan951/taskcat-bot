package com.jalan.taskcatbot.bot.handlers;

import com.jalan.taskcatbot.bot.IBot;
import com.jalan.taskcatbot.bot.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoHandler implements IHandler, IHandlerSelector {
    
    private final Logger logger = LoggerFactory.getLogger(EchoHandler.class);

    public String getName() {
        return "echo";
    }
    
    public void handle(IBot bot, User user, String message) {
        message = message.replace("echo", "").trim();
        this.logger.info("echo: {}", message);

        bot.sendMessage(user, message);
    }

    public boolean isSelectable(String message) {
        return message.startsWith("echo");
    }

}
