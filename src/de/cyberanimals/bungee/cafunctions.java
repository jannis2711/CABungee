package de.cyberanimals.bungee;

import de.cyberanimals.chat.cachatfunctions;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class cafunctions
{
  private cabungee plugin;
  
  public cafunctions(cabungee mainclass)
  {
    this.plugin = mainclass;
  }
  
  public String getCurrentTime()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    Timestamp time = new Timestamp(System.currentTimeMillis());
    String formatteddate = sdf.format(time);
    
    return formatteddate;
  }
  
  public boolean userExits(String UUID)
  {
    try
    {
      ResultSet rs = this.plugin.CASQL.getResult("SELECT `playername` FROM `playerdata` WHERE uuid='" + UUID + "'");
      return rs.next();
    }
    catch (SQLException e)
    {
      System.out.println("Fehler bei der Datenverarbeitung.");
      e.printStackTrace();
    }
    return false;
  }
  
  public void createServerSettings(String playername)
  {
    String qrymsg = "INSERT INTO `serversettings`(`playername`, `setting`, `value`) VALUES ('" + playername + "','msgblock','false')";
    String qryvsb = "INSERT INTO `serversettings`(`playername`, `setting`, `value`) VALUES ('" + playername + "','playersvisible','true')";
    
    this.plugin.CASQL.update(qrymsg);
    this.plugin.CASQL.update(qryvsb);
    
    System.out.println("Serversettings for " + playername + " are created.");
  }
  
  public void createUserProfile(String UUID, String Playername, String address)
  {
    String time = getCurrentTime();
    
    String playerdataQuery = "INSERT INTO `playerdata`(`uuid`, `playername`, `lastlogin`, `lastloginfrom`) VALUES ('" + UUID + "','" + Playername + "','" + time + "','" + address + "')";
    
    this.plugin.CASQL.update(playerdataQuery);
    
    String playerstaticsQuery = "INSERT INTO `statistics`(`playername`, `level`, `points`, `rank`, `gamesplayed`, `kills`, `deaths`, `gameswon`) VALUES ('" + Playername + "','0','50','1','0','0','0','0')";
    
    this.plugin.CASQL.update(playerstaticsQuery);
    
    createServerSettings(Playername);
    
    System.out.println("Profiles for new Player are created.");
  }
  
  public void LastLoginUpdate(String UUID, String Address)
  {
    String time = getCurrentTime();
    String qry = "UPDATE `playerdata` SET `lastlogin`='" + time + "',`lastloginfrom`='" + Address + "' WHERE uuid='" + UUID + "'";
    
    this.plugin.CASQL.update(qry);
  }
  
  public boolean checkBan(String UUID)
  {
    String qry = "SELECT `id`, `uuid`, `ip`, `reason`, `remainingtime` FROM `playerbans` WHERE uuid='" + UUID + "'";
    try
    {
      ResultSet rs = this.plugin.CASQL.getResult(qry);
      return rs.next();
    }
    catch (SQLException e)
    {
      System.out.println("Fehler bei der Datenverarbeitung.");
      e.printStackTrace();
    }
    return false;
  }
  
  public String getBanReason(String UUID)
  {
    String qry = "SELECT `reason` FROM `playerbans` WHERE uuid='" + UUID + "'";
    try
    {
      ResultSet rs = this.plugin.CASQL.getResult(qry);
      if (rs.next()) {
        return rs.getString("reason");
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return "System Error.";
  }
  
  public boolean userExistsByUsername(String playername)
  {
    try
    {
      ResultSet rs = this.plugin.CASQL.getResult("SELECT `uuid` FROM `playerdata` WHERE playername='" + playername + "'");
      return rs.next();
    }
    catch (SQLException e)
    {
      System.out.println("Fehler bei der Datenverarbeitung.");
      e.printStackTrace();
    }
    return false;
  }
  
  public String getUUIDfromPlayer(String playername)
  {
    String qry = "SELECT `uuid` FROM `playerdata` WHERE playername='" + playername + "'";
    try
    {
      ResultSet rs = this.plugin.CASQL.getResult(qry);
      if (rs.next()) {
        return rs.getString("uuid");
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return "0";
  }
  
  public void banPlayerPermanently(String playername, String reason, String ip)
  {
    String UUID = getUUIDfromPlayer(playername);
    if (UUID == "0")
    {
      System.out.println("Systemfehler.");
      return;
    }
    String qry = "INSERT INTO `playerbans`(`uuid`, `ip`, `reason`) VALUES ('" + UUID + "','" + ip + "','" + reason + "')";
    this.plugin.CASQL.update(qry);
  }
  
  public void unbanPlayer(String playername)
  {
    String UUID = getUUIDfromPlayer(playername);
    if (UUID == "0")
    {
      System.out.println("Systemfehler.");
      return;
    }
    String qry = "DELETE FROM `playerbans` WHERE uuid='" + UUID + "'";
    this.plugin.CASQL.update(qry);
  }
  
  public void unbanPlayerFromUUID(String UUID)
  {
    String qry = "DELETE FROM `playerbans` WHERE uuid='" + UUID + "'";
    this.plugin.CASQL.update(qry);
  }
  
  public void adminBroadcast(String msg)
  {
    this.plugin.chatfunctions.sendAdminChatMessage("CASERVER", msg);
  }
  
  public int getCoins(String playername)
  {
    int coins = 0;
    try
    {
      ResultSet rs = this.plugin.CASQL.getResult("SELECT `points` FROM `statistics` WHERE playername='" + playername + "'");
      while (rs.next()) {
        coins = rs.getInt("points");
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return -1;
    }
    return coins;
  }
  
  public boolean checkMSGBlock(String playername)
  {
    String qry = "SELECT `value` FROM `serversettings` WHERE playername='" + playername + "' AND setting='msgblock'";
    try
    {
      ResultSet rs = this.plugin.CASQL.getResult(qry);
      if (rs.next()) {
        return Boolean.parseBoolean(rs.getString("value"));
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public void setMSGBlock(String playername, String state)
  {
    String qry = "UPDATE `serversettings` SET `value`='" + state + "' WHERE playername='" + playername + "' AND setting='msgblock'";
    this.plugin.CASQL.update(qry);
  }
  
  public boolean emailExists(String playername)
  {
    String qry = "SELECT `email` FROM `playerdata` WHERE playername='" + playername + "'";
    try
    {
      ResultSet rs = this.plugin.CASQL.getResult(qry);
      String email = "";
      while (rs.next()) {
        email = rs.getString("email");
      }
      if ((email == null) || (email == "null") || (email == "")) {
        return false;
      }
      return true;
    }
    catch (SQLException e) {}
    return false;
  }
  
  public void addEmail(String playername, String emailaddress)
  {
    String qry = "UPDATE `playerdata` SET `email`='" + emailaddress + "' WHERE playername='" + playername + "'";
    this.plugin.CASQL.update(qry);
  }
}
