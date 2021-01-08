package de.cyberanimals.chat;

import de.cyberanimals.bungee.cabungee;
import de.cyberanimals.bungee.cafunctions;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class calis_join
  implements Listener
{
  private cabungee plugin;
  
  public calis_join(cabungee plugin)
  {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onJoin(ServerConnectedEvent e)
  {
	   
	    if (e.getPlayer().hasPermission("cyberanimals.chat.teamjoin")) {
	        this.plugin.chatfunctions.sendTeamChatMessage(e.getPlayer().getName(), "hat den Server #" + e.getServer().getInfo().getName() + " betreten.");
	      }
	      if (!this.plugin.functions.emailExists(e.getPlayer().getName()))
	      {
	        e.getPlayer().sendMessage(this.plugin.pfx + "Du hast deine E-Mail Adresse noch nicht hinterlegt. Bitte tue dies um die Meldung auszublenden und den Kontakt zu uns zu verbessern.");
	        e.getPlayer().sendMessage(this.plugin.syntax + "email <Deine E-Mail-Adresse>");
	      }
  }
  

}
