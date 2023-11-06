package com.jalan.taskcatbot.bot;

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
public class User {
    
    private Long idUser;
    private String name;
    private boolean authorized;
    private UserRole role;
    

}
