
package com.trixtaro.trixtalogin;

import com.google.inject.Inject;
import com.trixtaro.trixtalogin.config.Config;
import com.trixtaro.trixtalogin.login.Register;
import java.io.File;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
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
        
        e.getTargetEntity().sendMessage(Text.of(TextColors.GOLD,"12345"));
        
    }

    @Listener
    public void onGameInit(GameInitializationEvent e) {
        
        instance = this;
        
        logger.info("Successfully running TrixtaLogin!!!");

        Sponge.getCommandManager().register(instance, SendMessage.base(), "hello");
        Sponge.getCommandManager().register(instance, Register.base(), "register");

    }

    
}
