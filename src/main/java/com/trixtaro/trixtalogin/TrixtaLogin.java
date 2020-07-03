
package com.trixtaro.trixtalogin;
import com.google.inject.Inject;
import com.trixtaro.trixtalogin.config.Config;
import com.trixtaro.trixtalogin.login.Login;
import com.trixtaro.trixtalogin.login.LoginTimer;
import com.trixtaro.trixtalogin.login.Register;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

@Plugin(id = "trixtalogin", name = "TrixtaLogin", version = "1.3", description = "An authentication plugin for Sponge.")

public class TrixtaLogin {
    
    public static TrixtaLogin instance;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File file;
    
    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    
    @Inject
    Logger logger;
    
    @Listener
    public void onPreInit(GamePreInitializationEvent e){
        
        Config.setup(file, loader);
        Config.load();
        
    }
    
    @Listener
    public void reload(GameReloadEvent e){
        Config.load();
    }
    
    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join e){
        
        Player player = e.getTargetEntity();
        
        double locationX = player.getLocation().getX();
        double locationY = player.getLocation().getY();
        double locationZ = player.getLocation().getZ();
        Config.confNode.getNode("Player", player.getName(),"location-x").setValue(locationX);
        Config.confNode.getNode("Player", player.getName(),"location-y").setValue(locationY);
        Config.confNode.getNode("Player", player.getName(),"location-z").setValue(locationZ);
        Config.save();
        
        TrixtaLogin.blockFeatures(player);
        
        if(Config.confNode.getNode("Player", player.getName(),"password").isVirtual()){
            
            player.sendMessage(Text.of(TextColors.GOLD,Config.confNode.getNode("Messages", "player_not_registered_1").getString()));
            player.sendMessage(Text.of(TextColors.GOLD,Config.confNode.getNode("Messages", "player_not_registered_2").getString()));
            
        } else {
            
            player.sendMessage(Text.of(TextColors.GOLD,Config.confNode.getNode("Messages", "player_not_logged_1").getString()));
            player.sendMessage(Text.of(TextColors.GOLD,Config.confNode.getNode("Messages", "player_not_logged_2").getString()));
            
        }
        
        new LoginTimer(player).start();
        
    }
    
    @Listener
    public void onPlayerExit(ClientConnectionEvent.Disconnect e){
        
        Player player = e.getTargetEntity();
        
        if(Config.confNode.getNode("Player", player.getName(), "isLogged").getBoolean() == false)
            TrixtaLogin.unlockFeatures(player);
        
        Login.logout(e.getTargetEntity());
        
    }

    @Listener
    public void onGameInit(GameInitializationEvent e) {
        
        instance = this;
        
        logger.info("Successfully running TrixtaLogin!!!");

        Sponge.getCommandManager().register(instance, SendMessage.base(), "hello");
        Sponge.getCommandManager().register(instance, Register.base(), "register");
        Sponge.getCommandManager().register(instance, Login.base(), "login");

    }
    
    public static String hashWith256(String textToHash){
        
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] byteOfTextToHash = textToHash.getBytes(StandardCharsets.UTF_8);
            byte[] hashedByetArray = digest.digest(byteOfTextToHash);
            String encoded = Base64.getEncoder().encodeToString(hashedByetArray);
            return encoded;
        }catch(NoSuchAlgorithmException ex){
            
        }
        
        return "";
        
    }
    
    @Listener
    public void onSalto(SendCommandEvent event){
        //if()
    }
    
    public static void blockFeatures(Player player){
 
        player.offer(Keys.WALKING_SPEED, 0.0);
        
    }
    
    public static void unlockFeatures(Player player){
        
        double locationX = Config.confNode.getNode("Player", player.getName(),"location-x").getFloat();
        double locationY = Config.confNode.getNode("Player", player.getName(),"location-y").getFloat();
        double locationZ = Config.confNode.getNode("Player", player.getName(),"location-z").getFloat();
        player.setLocation(new Location(player.getWorld(), locationX, locationY, locationZ));
        
        player.offer(Keys.WALKING_SPEED, 0.1);
        
    }

    
}
