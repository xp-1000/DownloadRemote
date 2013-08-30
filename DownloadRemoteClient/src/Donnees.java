
public abstract class Donnees 
{
	static Settings settings = new Settings();
	static String ip = settings.getIp();
	static String port = settings.getPort();
	static String language = settings.getLanguage();
	static String manager = settings.getManager();
	
	static String[] listeLangues = {"Français"};
	static String[] listeLogiciels = {"PyLoad", "Défaut"};
	
	public static void setSettings(String ip, String port, String language, String manager)
	{
		settings.setSettings(ip, port, language, manager);
		Donnees.ip = ip;
		Donnees.port = port;
		Donnees.language = language;
		Donnees.manager = manager;
	}
}
