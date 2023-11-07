package com.jalan.taskcatbot.exceptions;

public class PluginConfigFieldNotFound extends Exception {
    public PluginConfigFieldNotFound(String fieldName) {
        super("PluginField '" + fieldName + "' not found");
    }
}
