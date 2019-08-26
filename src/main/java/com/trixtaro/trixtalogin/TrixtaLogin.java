
package com.trixtaro.trixtalogin;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "trixtalogin", name = "TrixtaLogin", version = "1.0", description = "An authentication plugin for Sponge.")

public class TrixtaLogin {
    
     @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("Successfully running TrixtaLogin!!!");
    }

    
}
