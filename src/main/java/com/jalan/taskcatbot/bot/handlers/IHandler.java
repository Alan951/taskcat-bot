package com.jalan.taskcatbot.bot.handlers;

import com.jalan.taskcatbot.bot.IBot;
import com.jalan.taskcatbot.bot.User;

public interface IHandler {

    public void handle(IBot bot, User user, String message);

    public String getName();

}
