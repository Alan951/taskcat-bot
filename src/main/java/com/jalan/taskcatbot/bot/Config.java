package com.jalan.taskcatbot.bot;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Config {
    
    @JsonProperty("BotConfig")
    private List<BotConfig> botConfig;

    @JsonProperty("PluginRegistry")
    private Map<String, Map<String, Object>> pluginRegistry;

}
