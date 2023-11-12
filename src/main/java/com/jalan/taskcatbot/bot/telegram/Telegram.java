package com.jalan.taskcatbot.bot.telegram;

import java.util.List;

import com.jalan.taskcatbot.CentralMessageUnit;
import com.jalan.taskcatbot.bot.BotConfig;
import com.jalan.taskcatbot.bot.IBot;
import com.jalan.taskcatbot.bot.IBotConfig;
import com.jalan.taskcatbot.bot.User;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Telegram implements IBot, IBotConfig {

    private TelegramBot telegramBot;
    private BotConfig botConfig;
    private boolean enabled;
    private CentralMessageUnit centralMessageUnit;

    private Logger logger = LoggerFactory.getLogger(Telegram.class);

    public Telegram(){
        enabled = false;
    }

    @Override
    public Object getBot() {
        return telegramBot;
    }

    @Override
    public void setConfig(BotConfig config) {
        this.botConfig = config;
    }

    @Override
    public void setCentralMessageUnit(CentralMessageUnit centralMessageUnit) {
        this.centralMessageUnit = centralMessageUnit;
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void start() throws Exception {
        if(this.botConfig == null) {
            throw new Exception("bot config is null");
        }

        this.telegramBot = new TelegramBot(this.botConfig.getKey());

        this.telegramBot.setUpdatesListener((updates) -> {
            for(Update update : updates) {
                if(update.message() == null || update.message().text().trim().isEmpty()) {
                    logger.debug("ignored empty message");
                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                }
                
                String messageText = update.message().text();
                
                logger.info("message received from "+ update.message().from().id() + " - \"" + messageText + "\"");
                this.centralMessageUnit.onNewMessage(this, update.message().chat().id(), messageText);
            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

        this.enabled = true;
    }

    @Override
    public void stop() {
        this.telegramBot.removeGetUpdatesListener();
        this.telegramBot = null;
        this.enabled = false;  
    }

    @Override
    public void sendMessage(User user, String message) {
        if(!isEnabled()) {
            this.logger.debug("telegram bot is not enabled");
            return;
        }

        this.telegramBot.execute(new SendMessage(user.getIdUser(), message));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sendAssists(Object someAssists, User user) {
        List<String> assists = (List<String>) someAssists;

        KeyboardButton[] buttons = new KeyboardButton[assists.size()];
        assists.forEach((action) -> {
            buttons[assists.indexOf(action)] = new KeyboardButton(action);
        });

        Keyboard keyboard = new ReplyKeyboardMarkup(buttons)
            .oneTimeKeyboard(true)
            .resizeKeyboard(true)
            .selective(true);
        telegramBot.execute(
            new SendMessage(user.getIdUser(), "Selecciona alguna opción")
                .replyMarkup(keyboard));
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    
}
