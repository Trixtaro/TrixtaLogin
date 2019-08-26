
package com.trixtaro.trixtalogin;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
@Plugin(id = "trixtalogin", name = "TrixtaLogin", version = "1.0", description = "An authentication plugin for Sponge.")

public class TrixtaLogin {
    
    public static TrixtaLogin instance;
    
    @Inject
    Logger logger;

    @Listener
    public void onGameInit(GameInitializationEvent e) {
        
        instance = this;
        
        logger.info("Successfully running TrixtaLogin!!!");
        
        CommandSpec command = CommandSpec.builder()
                .permission("trixtalogin.command.base")
                .description(Text.of("This is the base command"))
                .executor(new SendMessage())
                .build();
        
        Sponge.getCommandManager().register(instance, command, "hello");

    }

    
}
