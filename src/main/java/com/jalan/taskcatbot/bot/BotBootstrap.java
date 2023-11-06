package com.jalan.taskcatbot.bot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jalan.taskcatbot.CentralMessageUnit;
import com.jalan.taskcatbot.bot.telegram.Telegram;
import com.jalan.taskcatbot.ext.ExtLoader;
import com.jalan.taskcatbot.ext.ExtensionFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotBootstrap {
    
    private List<BotConfig> botConfigs;
    private List<IBot> bots;
    private ConfigLoader configLoader;
    private CentralMessageUnit centralMessageUnit;
    
    private Logger logger = LoggerFactory.getLogger(BotBootstrap.class);

    public BotBootstrap(String basePathConfig, String extFinder) throws IOException {
        this.bots = new ArrayList<>();
        this.configLoader = new ConfigLoader();

        ExtLoader extLoader = new ExtLoader();
        extLoader.addFinderPath(extFinder);

        if(basePathConfig != null) {
            extLoader.addFinderPath(basePathConfig + File.separator + "plugins");
            configLoader.setBasePath(basePathConfig);
        }

        extLoader.load();

        try {
            this.botConfigs = Arrays.asList(configLoader.loadBotConfig());
            this.centralMessageUnit = new CentralMessageUnit(new SecHelper(botConfigs));

            for(ExtensionFile extFile : extLoader.getExtFiles()) {
                this.centralMessageUnit.addHandler(extFile.getHandler());
            }
            
            for(BotConfig conf : this.botConfigs) {
                this.logger.info("bot config: {} - {}", conf.getPlatform(), conf.getBot());
            }

            start();
        } catch (Exception e) {
            logger.error("Error on loading config file: {}", e.getMessage());
        }
    }

    public void start() {
        for(BotConfig botConfig : botConfigs) {
            if(botConfig.isEnabled()) {
                try{
                    switch(botConfig.getPlatform()) {
                        case TELEGRAM:
                            IBot telegram = new Telegram();
                            IBotConfig telegramConfig = (IBotConfig) telegram;
                            telegramConfig.setConfig(botConfig);
                            telegramConfig.setCentralMessageUnit(centralMessageUnit);
                            telegram.init();
                            telegram.start();
                            bots.add(telegram);
                            this.logger.info("{} bot started: {} ", botConfig.getPlatform(), botConfig.getBot());
                            break;
                        default:
                            break;
                    }
                } catch(Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

}
