package com.jalan.taskcatbot.bot;

import java.util.List;

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
public class BotConfig {
    
    private String bot;
    private BotPlatform platform;
    private List<User> users;
    private String key;
    private boolean enabled;

}
