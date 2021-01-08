package de.cyberanimals.commands;

import de.cyberanimals.bungee.cabungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class cacmd_broadcast
  extends Command
{
  private cabungee plugin;
  
  public cacmd_broadcast(String name, cabungee plugin)
  {
    super(name);
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("cyberanimals.broadcast"))
    {
      sender.sendMessage(this.plugin.msgperm);
      return;
    }
    if (args.length == 0)
    {
      sender.sendMessage(this.plugin.syntax + "broadcast <Nachricht>");
      return;
    }
    String msg = "";
    for (int i = 0; i < args.length; i++) {
      msg = msg + " " + args[i];
    }
    msg = ChatColor.translateAlternateColorCodes('&', msg);
    ProxyServer.getInstance().broadcast(this.plugin.pfx + "§c" + msg);
  }
}
