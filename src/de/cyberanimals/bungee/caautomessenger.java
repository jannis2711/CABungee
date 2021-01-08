package de.cyberanimals.bungee;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;

public class caautomessenger
{
  private cabungee plugin;
  
  public caautomessenger(cabungee plugin)
  {
    this.plugin = plugin;
  }
  
  public ArrayList<String> messages = new ArrayList();
  int lastIndex = 0;
  ScheduledTask id = null;
  
  public void getMessages()
  {
    try
    {
      ResultSet rs = this.plugin.CASQL.getResult("SELECT `message` FROM `automessager`");
      while (rs.next()) {
        this.messages.add(rs.getString("message"));
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void startMessaging(int delay)
  {
    getMessages();
    this.id = ProxyServer.getInstance().getScheduler().schedule(this.plugin, new Runnable()
    {
      public void run()
      {
        if (caautomessenger.this.lastIndex == caautomessenger.this.messages.size()) {
          caautomessenger.this.lastIndex = 0;
        }
        if (caautomessenger.this.messages.size() == 0) {
          return;
        }
        System.out.println("Index:" + caautomessenger.this.lastIndex);
        ProxyServer.getInstance().broadcast(caautomessenger.this.plugin.pfx + (String)caautomessenger.this.messages.get(caautomessenger.this.lastIndex));
        caautomessenger.this.lastIndex += 1;
      }
    }, 0L, delay, TimeUnit.MINUTES);
  }
  
  public void stopMessaging()
  {
    this.id.cancel();
  }
  
  public void refreshMessages()
  {
    this.messages.clear();
    getMessages();
  }
}
