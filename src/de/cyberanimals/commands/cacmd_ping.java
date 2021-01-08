package de.cyberanimals.commands;

import de.cyberanimals.bungee.cabungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class cacmd_ping
  extends Command
{
  private cabungee plugin;
  
  public cacmd_ping(String name, cabungee plugin)
  {
    super(name);
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if ((sender instanceof ProxiedPlayer))
    {
      ProxiedPlayer p = (ProxiedPlayer)sender;
      p.sendMessage(this.plugin.pfx + "Dein Ping zu CyberAnimals.de liegt bei §c" + p.getPing() + "ms");
    }
    else
    {
      sender.sendMessage(this.plugin.msgplayer);
    }
  }
}
