package de.cyberanimals.listener;

import de.cyberanimals.bungee.cabungee;
import de.cyberanimals.bungee.cafunctions;
import java.net.InetSocketAddress;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class caloginlistener
  implements Listener
{
  private cabungee plugin;
  
  public caloginlistener(cabungee mainclass)
  {
    this.plugin = mainclass;
  }
  
  @EventHandler
  public void onPreJoin(PreLoginEvent e) {}
  
  @EventHandler
  public void onLogin(LoginEvent e)
  {
    if (!this.plugin.functions.userExits(e.getConnection().getUUID()))
    {
      this.plugin.functions.createUserProfile(e.getConnection().getUUID(), e.getConnection().getName(), e.getConnection().getAddress().getHostName());
    }
    else
    {
      if (!this.plugin.functions.checkBan(e.getConnection().getUUID()))
      {
        this.plugin.functions.LastLoginUpdate(e.getConnection().getUUID(), e.getConnection().getAddress().getHostName());
        return;
      }
      String reason = this.plugin.functions.getBanReason(e.getConnection().getUUID());
      
      e.getConnection().disconnect(reason);
      e.setCancelled(true);
    }
   
  }
}
