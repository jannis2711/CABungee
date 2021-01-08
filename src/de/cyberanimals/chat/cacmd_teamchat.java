package de.cyberanimals.chat;

import de.cyberanimals.bungee.cabungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class cacmd_teamchat
  extends Command
{
  private cabungee plugin;
  
  public cacmd_teamchat(String name, cabungee plugin)
  {
    super(name);
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("cyberanimals.chat.teamchat"))
    {
      sender.sendMessage(this.plugin.msgperm);
      return;
    }
    if (args.length == 0)
    {
      sender.sendMessage(this.plugin.syntax + "tc <Nachricht>");
      return;
    }
    String msg = "";
    for (int i = 0; i < args.length; i++) {
      msg = msg + " " + args[i];
    }
    if ((sender instanceof ProxiedPlayer))
    {
      ProxiedPlayer p = (ProxiedPlayer)sender;
      this.plugin.chatfunctions.sendTeamChatMessage(p.getName(), msg);
    }
    else
    {
      this.plugin.chatfunctions.sendTeamChatMessage("CASERVER", msg);
    }
  }
}
