package de.cyberanimals.commands;

import de.cyberanimals.bungee.caautomessenger;
import de.cyberanimals.bungee.cabungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class cacmd_rfamsg
  extends Command
{
  private cabungee plugin;
  
  public cacmd_rfamsg(String name, cabungee plugin)
  {
    super(name);
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if (!sender.hasPermission("cyberanimals.automsg.refresh"))
    {
      sender.sendMessage(this.plugin.msgperm);
      return;
    }
    this.plugin.automsg.refreshMessages();
    sender.sendMessage(this.plugin.pfx + "Die Nachrichten des Automessagers wurden mit der Datenbank synchronisiert.");
  }
}
