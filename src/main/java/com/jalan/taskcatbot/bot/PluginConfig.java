package com.jalan.taskcatbot.bot;

import java.util.Map;

import com.jalan.taskcatbot.exceptions.PluginConfigFieldNotFound;
import com.jalan.taskcatbot.exceptions.PluginConfigNotFound;

public class PluginConfig implements IPluginConfig {
    
    private ConfigLoader configLoader;

    
    public PluginConfig(ConfigLoader configLoader) {
        this.configLoader = configLoader;
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

        return (T)fields.get(key);
    }

    

}
