package com.trixtaro.trixtalogin.login;

import com.flowpowered.math.vector.Vector3d;
import com.trixtaro.trixtalogin.config.Config;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class noLoginTP extends Thread{

    Player player;

    public noLoginTP(Player player){
        this.player = player;
    }

    @Override
    public void run() {

        int backTime = 500;
        double locationX = Config.confNode.getNode("Player", player.getName(),"location-x").getDouble();
        double locationY = Config.confNode.getNode("Player", player.getName(),"location-y").getDouble();
        double locationZ = Config.confNode.getNode("Player", player.getName(),"location-z").getDouble();
        double locX = 0;
        double locY = 0;
        double locZ = 0;
        boolean variable = true;
        try{
            locX = player.getLocation().getX();
            locY = player.getLocation().getY();
            locZ = player.getLocation().getZ();
            variable = locationX != locX && locationY != locY && locationZ != locZ;
            while (variable == true) {
                    Thread.sleep(backTime);
                    player.sendMessage(Text.of(TextColors.GOLD, "Te has movido"));
            }

        }catch(InterruptedException ex){

        }

    }

}
