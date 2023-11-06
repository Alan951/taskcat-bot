package com.jalan.taskcatbot.bot.discord;

import com.jalan.taskcatbot.CentralMessageUnit;
import com.jalan.taskcatbot.bot.BotConfig;
import com.jalan.taskcatbot.bot.IBot;
import com.jalan.taskcatbot.bot.IBotConfig;
import com.jalan.taskcatbot.bot.User;

public class Discord implements IBot, IBotConfig {

    @Override
    public void setConfig(BotConfig config) {
        // TODO Auto-generated method stub
    }

    public Object getBot(){
        return null;
    }

    @Override
    public void setCentralMessageUnit(CentralMessageUnit centralMessageUnit) {
        // TODO Auto-generated method stub
    }

    @Override
    public void init() throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void start() throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void sendMessage(User user, String message) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
