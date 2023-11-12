package com.jalan.taskcatbot.bot;

public interface IPluginConfig {
    
    public String getStrConfig(String pluginName, String key) throws Exception;

    public String getStrConfigOrValue(String pluginName, String key, String defaultStr);

    public Object getObjConfig(String pluginName, String key) throws Exception;

    public <T> T getConfig(String pluginName, String key) throws Exception;

    public String putConfig(String pluginName, String key, String value) throws Exception;

}
