
package com.trixtaro.trixtalogin;

import com.flowpowered.math.vector.Vector3d;
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
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
@Plugin(id = "trixtalogin", name = "TrixtaLogin", version = "1.0", description = "An authentication plugin for Sponge.")

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

        if(Config.confNode.getNode("Player", player.getName(),"password").isVirtual()){
            
            player.sendMessage(Text.of(TextColors.GOLD,"You need to register in the server."));
            player.sendMessage(Text.of(TextColors.GOLD,"Use /register password repeat_password"));
            
        } else {
            
            player.sendMessage(Text.of(TextColors.GOLD,"You need to login in the server."));
            player.sendMessage(Text.of(TextColors.GOLD,"Use /login password"));
            
        }
        
        new LoginTimer(player).start();
        
    }
    
    @Listener
    public void onPlayerExit(ClientConnectionEvent.Disconnect e){
        
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

    
}
