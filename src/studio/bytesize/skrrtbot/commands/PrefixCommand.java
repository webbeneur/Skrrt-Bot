package studio.bytesize.skrrtbot.commands;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import studio.bytesize.skrrtbot.util.Help;
import studio.bytesize.skrrtbot.Main;
import studio.bytesize.skrrtbot.util.CommandHelper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PrefixCommand implements Command {
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length == 0) {
            CommandHelper.sendTagMessage("You didn't provide a prefix...", event);
            return;
        }

        List<Role> roles = event.getMember().getRoles();
        for(Role role : roles) {
            if(role.getName().contains("admin")){
                try {
                    Files.write(Paths.get("prefix.txt"), args[0].getBytes());
                    CommandHelper.sendTagMessage("Bot prefix successfully changed to: " +args[0], event);
                    Main.PREFIX = args[0];
                    return;
                } catch (Exception e) {
                    CommandHelper.sendTagMessage(e.getMessage(), event);
                }
            }
        }

        CommandHelper.sendTagMessage("Nice try, but you're not an admin...", event);
    }

    public String help() {
        return Help.str("prefix <prefix>\nWill change the bot's command prefix. Only admins can do this.");
    }

    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }
}