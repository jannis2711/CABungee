package de.cyberanimals.bungee;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class camysql
{
  public String host = "srv1.jalen-services.de";
  public String port = "3306";
  public String database = "c30mcserver";
  public String username = "c30mcserver";
  public String password = "Erfa4p2vXNEGQ3Ud";
  public Connection con;
  
  public void connect()
  {
    if (!isConnected()) {
      try
      {
        this.con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
        System.out.println("[MySQL] Verbindung zum Datenbankserver aufgebaut.");
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    } else {
      System.out.println("There is a Connection already.");
    }
  }
  
  public void disconnect()
  {
    if (isConnected()) {
      try
      {
        this.con.close();
        System.out.println("[MySQL] Verbindung zum Datenbankserver geschlossen.");
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public boolean isConnected()
  {
    try
    {
      return (this.con != null) || (this.con.isValid(1));
    }
    catch (Exception e) {}
    return false;
  }
  
  public void update(String qry)
  {
    if (!isConnected()) {
      connect();
    }
    try
    {
      PreparedStatement ps = this.con.prepareStatement(qry);
      ps.executeUpdate();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public ResultSet getResult(String qry)
  {
    if (!isConnected()) {
      connect();
    }
    try
    {
      PreparedStatement ps = this.con.prepareStatement(qry);
      return ps.executeQuery();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
