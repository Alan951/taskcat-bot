package com.jalan.taskcatbot.bot;

public interface IPluginConfig {
    
    public String getStrConfig(String pluginName, String key) throws Exception;

    public Object getObjConfig(String pluginName, String key) throws Exception;

    public <T> T getConfig(String pluginName, String key) throws Exception;

}
