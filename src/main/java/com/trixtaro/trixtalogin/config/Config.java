
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
                Config.loader.save(confNode);
            }
        
            Config.confNode = Config.loader.load();
            
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }
    
    public static void save(){
        
        try{
            Config.loader.save(confNode);
        }catch(Exception ex){
            
        }
    }
    
}
