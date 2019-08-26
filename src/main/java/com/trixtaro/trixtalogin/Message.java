
package com.trixtaro.trixtalogin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Message implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        
        Player player = args.<Player>getOne(Text.of("player")).get();
        String message = args.<String>getOne(Text.of("message")).get();
        
        if (src instanceof Player) {
            
            Text messageText = TextSerializers.FORMATTING_CODE.deserialize("&e["+src.getName()+"] &c -> &e["+player.getName()+"]&c "+message);

            src.sendMessage(messageText);
        } else{
        
            Text messageText = TextSerializers.FORMATTING_CODE.deserialize("&e[Console] &c -> &e["+player.getName()+"]&c "+message);
            
            src.sendMessage(messageText);
        }

        return CommandResult.success();
    }
    
    public static CommandSpec base(){
        return CommandSpec.builder()
                .permission("trixtalogin.command.message")
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.remainingJoinedStrings(Text.of("message"))))
                .description(Text.of("This is the base command"))
                .executor(new Message())
                .build();
    }
    
}
