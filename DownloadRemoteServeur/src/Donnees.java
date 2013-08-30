import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Donnees {
	
	public String port;
	
	public Donnees()
	{
		Properties prop = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream("conf.cnf");
			prop.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		port = prop.getProperty("port");
		System.out.print(port);
	}
}
