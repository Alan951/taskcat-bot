package com.jalan.taskcatbot;

import com.jalan.taskcatbot.bot.IBot;
import com.jalan.taskcatbot.bot.SecHelper;
import com.jalan.taskcatbot.bot.User;
import com.jalan.taskcatbot.bot.handlers.EchoHandler;
import com.jalan.taskcatbot.bot.handlers.FeriadosHandler;
import com.jalan.taskcatbot.bot.handlers.HandlerRegistry;
import com.jalan.taskcatbot.bot.handlers.HelpHandler;
import com.jalan.taskcatbot.bot.handlers.IHandler;
import com.jalan.taskcatbot.bot.handlers.IHandlerSelector;
import com.jalan.taskcatbot.bot.handlers.IHandlerShared;
import com.jalan.taskcatbot.bot.handlers.RandomReplyHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CentralMessageUnit {
    
    private SecHelper secHelper;
    private HandlerRegistry handlerRegistry = HandlerRegistry.getInstance();

    private Logger logger = LoggerFactory.getLogger(CentralMessageUnit.class);

    public CentralMessageUnit(SecHelper secHelper) {
        this.secHelper = secHelper;

        this.addHandler(new EchoHandler());
        this.addHandler(new FeriadosHandler());
        this.addHandler(new HelpHandler());
    }

    public void addHandler(IHandler handler) {
        this.handlerRegistry.addHandler(handler);

        if(handler instanceof IHandlerShared) {
            ((IHandlerShared) handler).setHandlerRegistry(handlerRegistry);
        }
    }

    public IHandler evalHandler(String message) {
        for (IHandler handler : this.handlerRegistry.getHandlers()) {
            if(handler.getName().equals(message)) {
                return handler;
            } else if (handler instanceof IHandlerSelector && 
                    ((IHandlerSelector) handler).isSelectable(message)) {
                return handler;
            }
        } // handler for iterator
        
        //default handler
        return new RandomReplyHandler();
    }

    public void onNewMessage(IBot bot, User user, String message) {
        user = secHelper.authorize(user);

        if(user == null) {
            this.logger.warn("onNewMessage: user not valid");
            return;
        }

        this.evalHandler(message).handle(bot, user, message);
    }

    public void onNewMessage(IBot bot, Long idUser, String message) {
        User user = secHelper.authorize(idUser);

        if(user == null) {
            this.logger.warn("onNewMessage: idUser not valid");
            return;
        }

        onNewMessage(bot, user, message);
    }

}
