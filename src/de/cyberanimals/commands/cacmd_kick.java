package de.cyberanimals.commands;

import de.cyberanimals.bungee.cabungee;
import de.cyberanimals.chat.cachatfunctions;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class cacmd_kick
  extends Command
{
  private cabungee plugin;
  
  public cacmd_kick(String name, cabungee plugin)
  {
    super(name);
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("cyberanimals.bans.kick"))
    {
      sender.sendMessage(this.plugin.msgperm);
      return;
    }
    if (args.length == 0)
    {
      sender.sendMessage(this.plugin.syntax + "kick <Spieler> <Grund>");
      return;
    }
    String player = args[0];
    
    ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(player);
    if (pp == null)
    {
      sender.sendMessage(this.plugin.epfx + "Fehler. Der Spieler ist nicht online.");
      return;
    }
    if (args.length == 1)
    {
      pp.disconnect("§cDu wurdest von unserem Netzwerk gekickt.");
      sender.sendMessage(this.plugin.pfx + "Der Spieler wurde gekickt. §c(" + pp.getName() + ")");
      if ((sender instanceof ProxiedPlayer))
      {
        ProxiedPlayer p = (ProxiedPlayer)sender;
        this.plugin.chatfunctions.sendAdminChatMessage("CASERVER->" + p.getName(), "has kicked the Player " + pp.getName() + " without Reason");
      }
      else
      {
        this.plugin.chatfunctions.sendAdminChatMessage("CASERVER", "has kicked the Player " + pp.getName() + " without Reason");
      }
      return;
    }
    String msg = "";
    for (int i = 1; i < args.length; i++) {
      msg = msg + " " + args[i];
    }
    msg = ChatColor.translateAlternateColorCodes('&', msg);
    
    pp.disconnect(msg);
    sender.sendMessage(this.plugin.pfx + "Der Spieler wurde gekickt. §c(" + pp.getName() + ")");
    if ((sender instanceof ProxiedPlayer))
    {
      ProxiedPlayer p = (ProxiedPlayer)sender;
      this.plugin.chatfunctions.sendAdminChatMessage("CASERVER->" + p.getName(), "has kicked the Player " + pp.getName() + " with Reason: " + msg);
    }
    else
    {
      this.plugin.chatfunctions.sendAdminChatMessage("CASERVER", "has kicked the Player " + pp.getName() + " with Reason: " + msg);
    }
  }
}
