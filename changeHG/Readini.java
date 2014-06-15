package changeHG;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Readini
{
  protected HashMap sections = new HashMap();
  private transient String currentSecion;
  private transient Properties current;

  public Readini(String filename)
    throws IOException
  {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    read(reader);
    reader.close();
  }

  protected void read(BufferedReader reader) throws IOException
  {
    String line;
    while ((line = reader.readLine()) != null)
      parseLine(line);
  }

  protected void parseLine(String line)
  {
    line = line.trim();
    if (line.matches("\\[.*\\]")) {
      this.currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
      this.current = new Properties();
      this.sections.put(this.currentSecion, this.current);
    } else if ((line.matches(".*=.*")) && 
      (this.current != null)) {
      int i = line.indexOf(61);
      String name = line.substring(0, i);
      String value = line.substring(i + 1);
      this.current.setProperty(name, value);
    }
  }

  public String getValue(String section, String name)
  {
    Properties p = (Properties)this.sections.get(section);

    if (p == null) {
      return null;
    }

    String value = p.getProperty(name);
    return value;
  }
}
