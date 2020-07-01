
package com.trixtaro.trixtalogin.login;

import com.trixtaro.trixtalogin.TrixtaLogin;
import com.trixtaro.trixtalogin.config.Config;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

public class LoginTimer extends Thread{
    
    Player player;
    
    public LoginTimer(Player player){
        this.player = player;
    }
    
    @Override
    public void run() {
        
        int wait_time = Config.confNode.getNode("Configuration", "wait_time").getInt();
        
        try{
            
            Thread.sleep(wait_time * 1000);
            
            if(Config.confNode.getNode("Player", player.getName(), "isLogged").getBoolean() == false){

                Task.Builder taskBuilder = Task.builder();
                
                taskBuilder.execute(new Runnable() {
                    @Override
                    public void run() {
                        TrixtaLogin.unlockFeatures(player);
                    }
                }).submit(TrixtaLogin.instance);
                
                Thread.sleep(100);
  
                this.player.kick(Text.of(Config.confNode.getNode("Messages", "kick_message").getString()));

            }
            
        }catch(InterruptedException ex){
            
        }
      
        
        
    }
    
}
