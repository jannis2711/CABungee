package de.cyberanimals.commands;

import de.cyberanimals.bungee.cabungee;
import de.cyberanimals.bungee.cafunctions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class cacmd_coins
  extends Command
{
  private cabungee plugin;
  
  public cacmd_coins(String name, cabungee plugin)
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
    p.sendMessage(this.plugin.pfx + "Du hast §c" + this.plugin.functions.getCoins(p.getName()) + " §6Coins!");
  }
}
