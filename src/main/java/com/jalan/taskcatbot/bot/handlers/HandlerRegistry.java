package com.jalan.taskcatbot.bot.handlers;

import java.util.ArrayList;
import java.util.List;

public class HandlerRegistry {
    
    private List<IHandler> handlers;

    private static HandlerRegistry handlerRegistry;

    private HandlerRegistry() {
        this.handlers = new ArrayList<IHandler>();
    }

    public static HandlerRegistry getInstance() {
        if(handlerRegistry == null) {
            handlerRegistry = new HandlerRegistry();
        }

        return handlerRegistry;
    }

    public List<IHandler> getHandlers() {
        return handlers;
    }

    public List<IHandler> addHandler(IHandler handler) {
        this.handlers.add(handler);

        return this.handlers;
    }


    

}
