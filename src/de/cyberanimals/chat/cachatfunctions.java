package de.cyberanimals.chat;

import java.io.PrintStream;

import de.cyberanimals.bungee.cabungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class cachatfunctions
{
  private cabungee plugin;
  public String acpfx = "§c§l[AC]§f";
  public String tcpfx = "§e§l[TC]§f";
  
  public cachatfunctions(cabungee plugin) {}
  
  public void sendAdminChatMessage(String pname, String msg)
  {
    msg = ChatColor.translateAlternateColorCodes('&', msg);
    
    System.out.println("[AdminChat] " + pname + ": " + msg);
    for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
      if (pp.hasPermission("cyberanimals.chat.adminchat")) {
        pp.sendMessage(this.acpfx + pname + ": §a" + msg);
      }
    }
  }
  
  public void sendTeamChatMessage(String pname, String msg)
  {
    msg = ChatColor.translateAlternateColorCodes('&', msg);
    
    System.out.println("[TeamChat] " + pname + ": " + msg);
    for (ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
      if (pp.hasPermission("cyberanimals.chat.teamchat")) {
        pp.sendMessage(this.tcpfx + pname + ": §a" + msg);
      }
    }
  }
}
