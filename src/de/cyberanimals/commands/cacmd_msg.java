package de.cyberanimals.commands;

import de.cyberanimals.bungee.cabungee;
import de.cyberanimals.bungee.cafunctions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class cacmd_msg
  extends Command
{
  private cabungee plugin;
  
  public cacmd_msg(String name, cabungee plugin)
  {
    super(name);
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if (!(sender instanceof ProxiedPlayer)) {
      return;
    }
    ProxiedPlayer p = (ProxiedPlayer)sender;
    if ((args.length == 0) || (args.length == 1))
    {
      p.sendMessage(this.plugin.syntax + "msg <Spieler> <Nachricht>");
      return;
    }
    if ((args.length == 2) || (args.length > 2))
    {
      String zplayer = args[0];
      
      ProxiedPlayer z = ProxyServer.getInstance().getPlayer(zplayer);
      if (z == null)
      {
        p.sendMessage(this.plugin.epfx + "Fehler. Der Spieler §c" + zplayer + " §9ist nicht online!");
        return;
      }
      if (this.plugin.functions.checkMSGBlock(zplayer))
      {
        p.sendMessage(this.plugin.epfx + "Der Spieler §4" + zplayer + "§9 hat Privatnachrichten deaktiviert.");
        return;
      }
      String msg = "";
      for (int i = 1; i < args.length; i++) {
        msg = msg + " " + args[i];
      }
      String format = "§4[PN] §c" + p.getName() + "§7->§cmir §7>> §f" + msg;
      String format2 = "§4[PN] §cmir§7->§c" + zplayer + " §7>> §f" + msg;
      
      z.sendMessage(format);
      p.sendMessage(format2);
    }
  }
}
