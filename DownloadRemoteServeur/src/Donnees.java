import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class Donnees {
	
	String port;
	List users = new ArrayList(); 
	
	public Donnees()
	{
		// Get Cconfig file path
		Properties systemProperties = System.getProperties();
		String path = systemProperties.getProperty("java.class.path");
		String pathConf = path.substring(0,path.lastIndexOf("\\")+1) + "conf.cnf";
		String pathUser = path.substring(0,path.lastIndexOf("\\")+1) + "security.db";
				
		Properties prop = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(pathConf);
			prop.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		port = prop.getProperty("port");
		
		try{
			InputStream stream_in=new FileInputStream(pathUser); 
			InputStreamReader reader=new InputStreamReader(stream_in);
			BufferedReader br_in=new BufferedReader(reader);
			FileWriter writer = new FileWriter(pathUser + ".tmp", true); 
			BufferedWriter br_out=new BufferedWriter(writer);
			String ligne;
			while ((ligne=br_in.readLine())!=null){
				String [] pair = ligne.split(":=:");
				System.err.println(pair[1]);
				if(pair[1].substring(0,2).equals("$(") && pair[1].substring(pair[1].length()-1,pair[1].length()).equals(")"))
					pair[1] = pair[1].substring(2,pair[1].length()-1);
				else
					pair[1] = encrypt(pair[1]);
				br_out.write(pair[0] + ":=:$(" + pair[1] + ")\n");
				System.err.println(pair[1]);
				System.out.println(decrypt(pair[1]));
				users.add(pair);
			}
			//br_out.flush();
			br_in.close(); 
			reader.close();
			br_out.close();
			writer.close();
			System.out.println("1");
			File file = new File(pathUser);
			System.out.println("2");
			file.delete();
			file=new File(pathUser + ".tmp"); 
			file.renameTo(new File(pathUser)); 
			
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	private String decrypt(String encrypted_pass)
	{
		String decrypted_pass="";
        for (int i=0; i<encrypted_pass.length();i++)  {
            int c=encrypted_pass.charAt(i)^48; 
            decrypted_pass=decrypted_pass+(char)c;
        }
		return decrypted_pass;
	}
	
	private String encrypt(String pass)
	{
		String crypte="";
        for (int i=0; i<pass.length();i++)  {
            int c=pass.charAt(i)^48; 
            crypte=crypte+(char)c;
        }
		return crypte;
	}

	public String auth(String username, String password)
	{
		Iterator it = users.iterator();
		while (it.hasNext())
		{
		      String [] pair = (String[]) it.next();
		      if(pair[0].equals(username))
		      {
		    	  System.err.println(pair[1] + "    " + password);
		    	  if(pair[1].equals(password))
		       		  return "success";
		    	  else
		    		  return "authErrorWrongPass";
		      }  
		}
		return "authErrorUserNotExists";
	}
}
