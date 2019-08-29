
package com.trixtaro.trixtalogin.config;

import java.io.File;
import java.io.IOException;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config {
    
    public static File config;
    public static ConfigurationLoader<CommentedConfigurationNode> loader;
    public static CommentedConfigurationNode confNode;
    
    public static void setup(File file, ConfigurationLoader<CommentedConfigurationNode> loader){
        Config.config = file;
        Config.loader = loader;
    }
    
    public static void load(){
        
        try{
            
            if(!Config.config.exists()){

                Config.config.createNewFile();
                Config.confNode = Config.loader.load();
                addValues();
                Config.loader.save(confNode);
            }
        
            Config.confNode = Config.loader.load();
            
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }
    
    private static void addValues(){
        
        Config.confNode.getNode("Player","player1").setValue("Valor").setComment("This is a player value.");
        
    }
    
}
