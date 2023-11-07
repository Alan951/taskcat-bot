package com.jalan.taskcatbot.exceptions;

public class PluginConfigNotFound extends Exception {
    public PluginConfigNotFound(String pluginName) {
        super("PluginConfig '" + pluginName + "' not found");
    }
}
