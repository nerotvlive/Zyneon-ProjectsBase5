package com.zyneonstudios.nerotvlive.projectsbase.commands;

import com.zyneonstudios.nerotvlive.projectsbase.utils.Communicator;
import com.zyneonstudios.nerotvlive.projectsbase.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s instanceof Player) {
            Communicator.sendError(s,Strings.needConsole);
        } else {
            if(args.length == 0) {
                Communicator.sendError(s,"/say [Nachricht...]");
            } else {
                String m="";
                for(int i=0;i<args.length;i++) {
                    m=m+args[i]+" ";
                }
                Communicator.broadcastRaw("§8[§4Server§8] §fKonsole§8: §7"+m.replace("&&","%and%").replace("&","§").replace("%and%","&"));
            }
        }
        return false;
    }
}
