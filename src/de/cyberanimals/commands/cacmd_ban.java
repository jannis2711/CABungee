package de.cyberanimals.commands;

import de.cyberanimals.bungee.cabungee;
import de.cyberanimals.bungee.cafunctions;
import java.net.InetSocketAddress;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class cacmd_ban
  extends Command
{
  private cabungee plugin;
  
  public cacmd_ban(String name, cabungee plugin)
  {
    super(name);
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if ((sender instanceof ProxiedPlayer))
    {
      ProxiedPlayer p = (ProxiedPlayer)sender;
      if (!p.hasPermission("cyberanimals.bans.ban"))
      {
        p.sendMessage(this.plugin.msgperm);
        return;
      }
      if (args.length == 0) {
        p.sendMessage(this.plugin.pfx + "Syntax : /ban <Spieler> [Grund]");
      }
      if (args.length == 1)
      {
        String zplayer = args[0];
        if (!this.plugin.functions.userExistsByUsername(zplayer))
        {
          p.sendMessage(this.plugin.epfx + "Dieser Spieler existiert auf unserem Netzwerk nicht. §6(" + zplayer + ")");
          return;
        }
        String ip = "";
        ProxiedPlayer z = ProxyServer.getInstance().getPlayer(zplayer);
        
        String PUUID = this.plugin.functions.getUUIDfromPlayer(zplayer);
        if (this.plugin.functions.checkBan(PUUID))
        {
          p.sendMessage(this.plugin.epfx + "Der Spieler ist bereits gebannt.");
          return;
        }
        if (z != null)
        {
          ip = z.getAddress().getHostName();
          z.disconnect("§cDu bist permanent von unserem Servernetzwerk gebannt.");
        }
        this.plugin.functions.banPlayerPermanently(zplayer, "§cDu bist permanent von unserem Servernetzwerk gebannt.", ip);
        p.sendMessage(this.plugin.pfx + "Der Spieler §c" + zplayer + " §6wurde permanent gebannt.");
        this.plugin.functions.adminBroadcast("§7PLAYER §c" + zplayer + " §7was banned permanently. || BY: §c" + p.getName());
      }
      if (args.length > 1)
      {
        String zplayer = args[0];
        String reason = "";
        for (int i = 1; i < args.length; i++) {
          reason = reason + " " + args[i];
        }
        reason = ChatColor.translateAlternateColorCodes('&', reason);
        ProxiedPlayer z = ProxyServer.getInstance().getPlayer(zplayer);
        String ip = "";
        
        String PUUID = this.plugin.functions.getUUIDfromPlayer(zplayer);
        if (this.plugin.functions.checkBan(PUUID))
        {
          p.sendMessage(this.plugin.epfx + "Der Spieler ist bereits gebannt.");
          return;
        }
        if (z != null)
        {
          ip = z.getAddress().getHostName();
          z.disconnect(reason);
        }
        this.plugin.functions.banPlayerPermanently(zplayer, reason, ip);
        p.sendMessage(this.plugin.pfx + "Der Spieler §c" + zplayer + " §6wurde permanent gebannt.");
        this.plugin.functions.adminBroadcast("§7PLAYER §c" + zplayer + " §7was banned permanently with REASON: §c" + reason + "§7 || BY: §c" + p.getName());
      }
    }
  }
}
