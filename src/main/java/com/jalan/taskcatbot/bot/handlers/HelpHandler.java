package com.jalan.taskcatbot.bot.handlers;

import java.util.List;
import java.util.stream.Collectors;

import com.jalan.taskcatbot.bot.IBot;
import com.jalan.taskcatbot.bot.User;

public class HelpHandler implements IHandler, IHandlerSelector, IHandlerShared {
    
    private HandlerRegistry handlerRegistry;

    @Override
    public void handle(IBot bot, User user, String message) {
        List<String> checkHandlers = this.handlerRegistry.getHandlers()
            .stream()
            .filter((handler) -> handler.getName() != null && !handler.getName().equals(getName()))
            .map((handler) -> handler.getName()).collect(Collectors.toList());

        bot.sendAssists(checkHandlers, user);
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public boolean isSelectable(String message) {
        message = message.toLowerCase();

        return message.equals("ayuda") || message.equals("help") || message.equals("?");
    }

    @Override
    public void setHandlerRegistry(HandlerRegistry handlerRegistry) {
        this.handlerRegistry = handlerRegistry;
    }

}
