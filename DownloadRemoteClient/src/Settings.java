import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;
import javax.swing.JOptionPane;


public class Settings {
	
	String path;
	Properties props;
	
	public Settings()
	{
		// Get Cconfig file path
		Properties systemProperties = System.getProperties();
		path = systemProperties.getProperty("java.class.path");
		path = path.substring(0,path.lastIndexOf("\\")+1) + "conf.cnf";
		// Get settings from config file
		try {
			this.loadProperties (path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur Fichier", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur Fichier", JOptionPane.ERROR_MESSAGE);
		}
	}

	
	/**
	 * Cette méthode stocke le fichier Properties à l'emplacement spécifié
	 * 
	 * @param props Le fichier à stocker
	 * @param fileLocation L'emplacement où le fichier doit être stocké
	 * @param comments Commentaires à insérer en tête du fichier
	 * @throws FileNotFoundException
	 * @throws IOException si une erreur est survenue lors de l'écriture du fichier
	 */
	public void saveProperties(Properties props, String fileLocation, String comments) throws FileNotFoundException,
			IOException {
		OutputStream out = new FileOutputStream(fileLocation);
		props.store(out, comments);
		out.flush();
		out.close();
	}

	
	/**
	 * Cette méthode lit un fichier Properties à l'emplacement spécifié
	 * 
	 * @param propertiesFileLocation L'emplacemnt du fichier
	 * @return Le fichier Properties chargé
	 * @throws FileNotFoundException si le fichier n'a pas été trouvé
	 * @throws IOException si une erreur est survenue durant la lecture
	 */
	public void loadProperties(String propertiesFileLocation) throws FileNotFoundException, IOException {
		props = new Properties();
		props.load(new FileInputStream(propertiesFileLocation));
	}

	
	/**
	 * 
	 * Cette méthode affiche cahque paire [clé,valuer] d'un fichier Properties
	 * 
	 * @param props Le fichier à afficher
	 */
	public void displayProperties(Properties props) {
		Iterator<Object> it = props.keySet().iterator();
		while (it.hasNext()) {
			String propertyName = (String) it.next();
			String propertyValue = props.getProperty(propertyName);
			System.out.println(propertyName + "=" + propertyValue);
		}
	}
	
	public String getIp()
	{
		return props.getProperty("address");
	}
	
	public String getPort()
	{
		return props.getProperty("port");
	}
	
	public String getLanguage() {
		return props.getProperty("language");
	}

	public String getManager() {
		return props.getProperty("manager");
	}
	
	public String getManagerPass() {
		return props.getProperty("pass");
		/*String encrypted_pass = props.getProperty("pass");
		String decrypted_pass="";
        for (int i=0; i<encrypted_pass.length();i++)  {
            int c=encrypted_pass.charAt(i)^48; 
            decrypted_pass=decrypted_pass+(char)c;
        }
		return decrypted_pass;*/
	}
	
	public String getManagerUser() {
		return props.getProperty("user");
	}
	
	public void setSettings(String ip, String port, String language, String manager, String user, String pass) {
		props.setProperty("address", ip);
		props.setProperty("port", port);
		props.setProperty("language", language);
		props.setProperty("manager", manager);
		props.setProperty("user", user);
		String crypte="";
        for (int i=0; i<pass.length();i++)  {
            int c=pass.charAt(i)^48; 
            crypte=crypte+(char)c;
        }
		props.setProperty("pass", crypte);
		try {
			this.saveProperties(props, path, "Configuration DownloadRemoteClient");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSettings(String ip, String port, String language, String manager, String user) {
		props.setProperty("address", ip);
		props.setProperty("port", port);
		props.setProperty("language", language);
		props.setProperty("manager", manager);
		props.setProperty("user", user);
		try {
			this.saveProperties(props, path, "Configuration DownloadRemoteClient");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
