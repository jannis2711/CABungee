package de.cyberanimals.bungee;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import de.cyberanimals.chat.cachatfunctions;
import de.cyberanimals.chat.cacmd_adminchat;
import de.cyberanimals.chat.cacmd_teamchat;
import de.cyberanimals.chat.calis_join;
import de.cyberanimals.commands.cacmd_ban;
import de.cyberanimals.commands.cacmd_blockmsg;
import de.cyberanimals.commands.cacmd_broadcast;
import de.cyberanimals.commands.cacmd_coins;
import de.cyberanimals.commands.cacmd_email;
import de.cyberanimals.commands.cacmd_isbanned;
import de.cyberanimals.commands.cacmd_kick;
import de.cyberanimals.commands.cacmd_msg;
import de.cyberanimals.commands.cacmd_ping;
import de.cyberanimals.commands.cacmd_rfamsg;
import de.cyberanimals.commands.cacmd_unban;
import de.cyberanimals.listener.caloginlistener;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class cabungee
  extends Plugin
{
  public String pfx = "§bCANetzwerk §f|| §6";
  public String apfx = "§4Admin-Chat §f|| §c";
  public String cpfx = "[CyberAnimals]";
  public String epfx = "§c[CyberAnimals] §9";
  public String syntax = this.pfx + "Syntax: /";
  public String msgperm = this.epfx + "Fehler. Du verfügst nicht über die nötigen Berechtigungen.";
  public String msgplayer = this.epfx + "Du musst ein Spieler sein!";
  public camysql CASQL = new camysql();
  public cafunctions functions = new cafunctions(this);
  public caautomessenger automsg = new caautomessenger(this);
  public cachatfunctions chatfunctions = new cachatfunctions(this);
  public int reconnectschedulerid;
  
  public void onEnable()
  {
    getLogger().info(this.cpfx + "BungeeCord wird geladen.");
    loadCABungeeCord();
    getLogger().info(this.cpfx + "BungeeCord wurde erfolgreich geladen.");
  }
  
  public void onDisable()
  {
    this.CASQL.disconnect();
  }
  
  public void loadCABungeeCord()
  {
    this.CASQL.connect();
    PluginManager pm = ProxyServer.getInstance().getPluginManager();
    pm.registerListener(this, new caloginlistener(this));
    pm.registerCommand(this, new cacmd_ban("ban", this));
    pm.registerCommand(this, new cacmd_unban("unban", this));
    pm.registerCommand(this, new cacmd_isbanned("isbanned", this));
    pm.registerCommand(this, new cacmd_coins("coins", this));
    pm.registerCommand(this, new cacmd_msg("msg", this));
    pm.registerCommand(this, new cacmd_blockmsg("blockmsg", this));
    pm.registerCommand(this, new cacmd_broadcast("broadcast", this));
    pm.registerCommand(this, new cacmd_rfamsg("rfamsg", this));
    pm.registerListener(this, new calis_join(this));
    pm.registerCommand(this, new cacmd_teamchat("tc", this));
    pm.registerCommand(this, new cacmd_adminchat("ac", this));
    pm.registerCommand(this, new cacmd_kick("kick", this));
    pm.registerCommand(this, new cacmd_ping("ping", this));
    pm.registerCommand(this, new cacmd_email("email", this));
    this.automsg.startMessaging(10);
    this.onReconnectScheduler();
  }
  
  
  public void sqlReconnect() {
	  if(CASQL.con != null) {
		  try {
			  CASQL.con.close();
			  CASQL.con = null;
			  System.out.println("MySQL verbindung getrennt.");
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
	  }
	  
	  BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {

		@Override
		public void run() {
			CASQL.connect();
			
		}
		  
	  }, 10, TimeUnit.MILLISECONDS);

	  
	  
  }
  
  private void onReconnectScheduler() {
	  System.out.println("MYSQL RECONNECT SCHEDULER STARTED!");
	  BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {

		@Override
		public void run() {
			sqlReconnect();
			
		}
		  
	  }, 1, 2, TimeUnit.HOURS);
  }
}
