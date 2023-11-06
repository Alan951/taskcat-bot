package com.jalan.taskcatbot.ext;

import java.io.File;

import com.jalan.taskcatbot.bot.handlers.IHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtensionFile {
    
    private File file;
    private String packageName;
    private IHandler handler;

}
