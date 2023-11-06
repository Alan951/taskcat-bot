package com.jalan.taskcatbot;

import java.io.IOException;

import com.jalan.taskcatbot.bot.BotBootstrap;

public class App {

    public static void main(String[] args)throws IOException {
        String basePath = getArgValue("--basepath", args);
        String extFinder = getArgValue("--extFolder", args);

        new BotBootstrap(basePath, extFinder);
    }

    public static String getArgValue(String argName, String[] args) {
        if(args.length == 0) {
            return null;
        }

        for(int i = 0; i < args.length; i++){
            if(args[i].equals(argName)){
                if(i + 1 < args.length){
                    return args[i + 1];
                }
            }
        }
        return null;
    }
}
