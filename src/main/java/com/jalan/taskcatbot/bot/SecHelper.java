package com.jalan.taskcatbot.bot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecHelper {
    
    private List<BotConfig> botConfigs;

    private Logger logger = LoggerFactory.getLogger(SecHelper.class);

    public SecHelper(List<BotConfig> botConfigs) {
        this.botConfigs = botConfigs;
    }

    public User isUser(Long idUser) {
        for (BotConfig botConfig : botConfigs) {
            for (User user : botConfig.getUsers()) {
                if (user.getIdUser().equals(idUser)) {
                    return user;
                }
            }
        }
        return null;
    }

    public boolean isUserAuthorized(User user){
        return user.isAuthorized();
    }

    public boolean isUserAuthorized(Long idUser) {
        User user = isUser(idUser);
        if (user != null) {
            return user.isAuthorized();
        }
        return false;
    }

    public User authorize(Long idUser) {
        if(idUser == null) {
            return null;
        }

        User user = isUser(idUser);
        if (user != null) {
            if(user.isAuthorized()) {
                return user;
            } else {
                this.logger.warn("authorize: user {} is not authorized", idUser);
            }
        } else {
            this.logger.warn("authorize: user {} not found", idUser);
        }

        return null;
    }

    public User authorize(User user) {
        if(user == null) {
            return null;
        }

        if(user.isAuthorized()) {
            return user;
        } else {
            this.logger.warn("authorize: user {} is not authorized", user.getIdUser());
        }

        return null;
    }

}
