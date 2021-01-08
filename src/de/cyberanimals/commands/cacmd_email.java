package de.cyberanimals.commands;

import de.cyberanimals.bungee.cabungee;
import de.cyberanimals.bungee.cafunctions;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class cacmd_email
  extends Command
{
  private cabungee plugin;
  
  public cacmd_email(String name, cabungee plugin)
  {
    super(name);
    this.plugin = plugin;
  }
  
  public void execute(CommandSender sender, String[] args)
  {
    if (!(sender instanceof ProxiedPlayer))
    {
      sender.sendMessage(this.plugin.msgplayer);
      return;
    }
    ProxiedPlayer p = (ProxiedPlayer)sender;
    if (!this.plugin.functions.userExistsByUsername(p.getName()))
    {
      p.sendMessage(this.plugin.epfx + "Unlogisch aber wahr. Du existierst auf unserem Servernetzwerk nicht. :/");
      return;
    }
    if (this.plugin.functions.emailExists(p.getName()))
    {
      p.sendMessage(this.plugin.epfx + "Du hast bereits eine E-Mail Adresse hinterlegt. Falls du Probleme mit Zugängen von uns hast, wende dich bitte an unseren Support. https://cyberanimals.de/server/support/");
      return;
    }
    if (args.length == 0)
    {
      p.sendMessage(this.plugin.syntax + "email <Deine E-Mail-Adresse>");
      p.sendMessage(this.plugin.pfx + "Kleine Information: Wir nutzen diese E-Mail Adresse um Newsletter zu versenden, und um dich bei einem Vorfall kontaktieren zu können.");
      p.sendMessage(this.plugin.pfx + "Kein Mitarbeiter von uns bekommt deine E-Mail Adresse. Diese wird verschlüsselt auf unseren Servern gespeichert.");
      return;
    }
    if (args.length > 1)
    {
      p.sendMessage(this.plugin.syntax + "email <Deine E-Mail-Adresse>");
      p.sendMessage(this.plugin.pfx + "Eine E-Mail Adresse enthält keine Leerzeichen/Lücken.");
      return;
    }
    String email = args[0];
    if ((!email.contains("@")) || (!email.contains(".")))
    {
      p.sendMessage(this.plugin.epfx + "Fehler. Du hast eine ungültige E-Mail-Adresse angegeben.");
      p.sendMessage(this.plugin.epfx + "Eine E-Mail Adresse enthält immer ein @-Zeichen und einen Punkt.");
      return;
    }
    this.plugin.functions.addEmail(p.getName(), email);
    p.sendMessage(this.plugin.pfx + "Deine E-Mail Adresse wurde erfolgreich auf unseren Servern §lverschlüsselt §f§6hinterlegt. §c(" + email + ")");
  }
}
