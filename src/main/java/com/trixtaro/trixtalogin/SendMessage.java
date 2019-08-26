
package com.trixtaro.trixtalogin;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class SendMessage implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        
        if(src instanceof Player){
            
            Player player = (Player) src;
            player.sendMessage(Text.of(TextColors.AQUA, TextStyles.BOLD, "Hello, " + player.getName()));
            
        } else {
            
            src.sendMessage(Text.of(TextColors.RED, "Only a player can execute this command."));
            
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base(){
        return CommandSpec.builder()
                .permission("trixtalogin.command.base")
                .child(Greeting.base(), "greeting")
                .child(Message.base(), "message")
                .description(Text.of("This is the base command"))
                .executor(new SendMessage())
                .build();
    }
    
}
