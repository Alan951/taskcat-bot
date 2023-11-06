package com.jalan.taskcatbot.bot;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLoader {
    
    private Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    private String basePath;
    private String botConfig;

    public ConfigLoader() {
        basePath = "./";
        botConfig = "config.json";

        try{
            basePath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath();
            logger.info("basePath: {}", basePath);
        } catch(SecurityException e) {
            logger.error("SecurityException on loading config file: " + e.getMessage());
        }
    }

    public Config loadConfig() throws JsonProcessingException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        Config config = mapper.readValue(new File(basePath + File.separator + botConfig), Config.class);

        return config;
    }

    public List<BotConfig> loadBotConfig() throws JsonProcessingException, JsonMappingException, IOException {
        return this.loadConfig().getBotConfig();
    }

    public boolean saveBotConfig(BotConfig[] config) throws JsonProcessingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(basePath + File.separator + botConfig), config);

        return true;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

}
