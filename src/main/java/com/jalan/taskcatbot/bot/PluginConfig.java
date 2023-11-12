package com.jalan.taskcatbot.bot;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jalan.taskcatbot.exceptions.PluginConfigFieldNotFound;
import com.jalan.taskcatbot.exceptions.PluginConfigNotFound;

public class PluginConfig implements IPluginConfig {
    
    private ConfigLoader configLoader;
    private ObjectMapper objectMapper;
    
    public PluginConfig(ConfigLoader configLoader) {
        this.configLoader = configLoader;
        this.objectMapper = new ObjectMapper();
    }

    public void setConfigLoader(ConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    public Map<String, Object> findPluginConfig(String pluginName) throws PluginConfigNotFound {
        if(!this.configLoader.getConfig().getPluginRegistry().containsKey(pluginName)) throw new PluginConfigNotFound(pluginName);

        return this.configLoader.getConfig().getPluginRegistry().get(pluginName);
    }

    @Override
    public String getStrConfig(String pluginName, String key) throws Exception {
        Map<String, Object> fields = findPluginConfig(pluginName);

        if(!fields.containsKey(key)) throw new PluginConfigFieldNotFound(key);

        return fields.get(key).toString();
    }


    @Override
    public Object getObjConfig(String pluginName, String key) throws Exception {
        Map<String, Object> fields = findPluginConfig(pluginName);

        if(!fields.containsKey(key)) throw new PluginConfigFieldNotFound(key);

        return fields.get(key);
    }


    @Override
    public <T> T getConfig(String pluginName, String key) throws Exception {
        Map<String, Object> fields = findPluginConfig(pluginName);

        if(!fields.containsKey(key)) throw new PluginConfigFieldNotFound(key);

        return objectMapper.convertValue(fields.get(key), new TypeReference<T>() {});
    }

    @Override
    public String putConfig(String pluginName, String key, String value) throws Exception {
        Map<String, Object> fields = findPluginConfig(pluginName);

        fields.put(key, value);

        return (String)fields.get(key);
    }

    @Override
    public String getStrConfigOrValue(String pluginName, String key, String defaultStr) {
        try {
            return getStrConfig(pluginName, key);
        } catch(Exception ex) {
            return defaultStr;
        }
    }

    

}
