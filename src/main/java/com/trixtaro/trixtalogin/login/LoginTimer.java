
package com.trixtaro.trixtalogin.login;

import com.trixtaro.trixtalogin.config.Config;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class LoginTimer extends Thread{
    
    Player player;
    
    public LoginTimer(Player player){
        this.player = player;
    }
    
    @Override
    public void run() {
        
        int wait_time = Config.confNode.getNode("Configuration", "wait_time").getInt();
        
        try{
            
            Thread.sleep(wait_time * 9000);
            
        }catch(InterruptedException ex){
            
        }
      
        if(Config.confNode.getNode("Player", player.getName(), "isLogged").getBoolean() == false){
            
            this.player.kick(Text.of("Tienes que registrarte/logearte antes del tiempo."));
            
        }
        
    }
    
}
