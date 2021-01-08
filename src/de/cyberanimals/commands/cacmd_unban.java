package de.cyberanimals.commands;

import de.cyberanimals.bungee.cabungee;
import de.cyberanimals.bungee.cafunctions;
import java.io.PrintStream;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class cacmd_unban
  extends Command
{
  private cabungee plugin;
  
  public cacmd_unban(String name, cabungee plugin)
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
    if (!p.hasPermission("cyberanimals.bans.unban"))
    {
      p.sendMessage(this.plugin.msgperm);
      return;
    }
    if (args.length == 0)
    {
      p.sendMessage(this.plugin.pfx + "Syntax : /unban <Spieler>");
      return;
    }
    String player = args[0];
    if (!this.plugin.functions.userExistsByUsername(player))
    {
      p.sendMessage(this.plugin.epfx + "Dieser Spieler existiert auf unserem Netzwerk nicht. §6(" + player + ")");
      return;
    }
    String UUID = this.plugin.functions.getUUIDfromPlayer(player);
    if (UUID == "0")
    {
      System.out.println("Systemfehler.");
      p.sendMessage(this.plugin.epfx + "Systemfehler.");
      return;
    }
    if (!this.plugin.functions.checkBan(UUID))
    {
      p.sendMessage(this.plugin.epfx + "Der Spieler ist nicht gebannt. §6(" + player + "," + UUID + ")");
      return;
    }
    this.plugin.functions.unbanPlayerFromUUID(UUID);
    p.sendMessage(this.plugin.pfx + "Der Spieler §c" + player + "§6 wurde entbannt.");
    this.plugin.functions.adminBroadcast("§7PLAYER §c" + player + " §7was unbanned || BY: §c" + p.getName());
  }
}
