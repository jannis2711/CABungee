package de.cyberanimals.commands;

import de.cyberanimals.bungee.cabungee;
import de.cyberanimals.bungee.cafunctions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class cacmd_blockmsg
  extends Command
{
  private cabungee plugin;
  
  public cacmd_blockmsg(String name, cabungee plugin)
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
    
    boolean blockmsg = this.plugin.functions.checkMSGBlock(p.getName());
    if (blockmsg)
    {
      this.plugin.functions.setMSGBlock(p.getName(), "false");
      p.sendMessage(this.plugin.pfx + "Du hast Privatnachrichten aktiviert.");
      return;
    }
    if (!blockmsg)
    {
      this.plugin.functions.setMSGBlock(p.getName(), "true");
      p.sendMessage(this.plugin.pfx + "Du hast Privatnachrichten deaktiviert.");
      return;
    }
  }
}
