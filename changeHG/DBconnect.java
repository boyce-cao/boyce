package changeHG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

class DBConnet
{
  private Connection con = null;
  private Statement sta = null;
  private ResultSet res = null;
  private String clname;
  private String jdbcURL;

  public DBConnet()
  {
    try
    {
      File directory = new File("");
      InputStream is = new FileInputStream(directory.getCanonicalPath() + "\\ChangeHG.ini");
      Properties per = new Properties();
      per.load(is);
      this.clname = per.getProperty("clname");
      this.jdbcURL = per.getProperty("jdbcURL");
      Class.forName(this.clname);
      this.con = DriverManager.getConnection(this.jdbcURL, "sa", "123456");
    }
    catch (SQLException sqle)
    {
      sqle.printStackTrace();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public int insert(String sql) {
    int count = -1;
    try {
      this.sta = this.con.createStatement();
      count = this.sta.executeUpdate(sql);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  public int upadate(String sql) {
    int count = -1;
    try {
      this.sta = this.con.createStatement();
      count = this.sta.executeUpdate(sql);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  public int delete(String sql) {
    int count = -1;
    try {
      this.sta = this.con.createStatement();
      count = this.sta.executeUpdate(sql);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }

  public ResultSet query(String sql) {
    try {
      this.sta = this.con.createStatement();
      this.res = this.sta.executeQuery(sql);
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return this.res;
  }

  public void closeConn()
  {
    try {
      if (this.res != null)
      {
        this.res.close();
      }
      if (this.sta != null)
      {
        this.sta.close();
      }
      this.con.close();
    }
    catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }
}