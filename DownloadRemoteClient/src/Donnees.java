
public abstract class Donnees 
{
	static Settings settings = new Settings();
	static String ip = settings.getIp();
	static int port = Integer.parseInt(settings.getPort());
	static String language = settings.getLanguage();
	static String manager = settings.getManager();
	static String user = System.getProperties().getProperty ("user.name");
	static String mgr_user = settings.getManagerUser();
	static String mgr_pass = settings.getManagerPass();
	
	static String[] listeLangues = {"Francais"};
	static String[] listeLogiciels = {"PyLoad", "Defaut"};
	
	public static void setSettings(String ip, String port, String language, String manager, String user, String pass)
	{
		settings.setSettings(ip, port, language, manager, user, pass);
		Donnees.ip = ip;
		Donnees.port = Integer.parseInt(port);
		Donnees.language = language;
		Donnees.manager = manager;
		Donnees.mgr_user = user;
		Donnees.mgr_user = pass;
	}

	public static void setSettings(String ip, String port, String language, String manager, String user)
	{
		settings.setSettings(ip, port, language, manager, user);
		Donnees.ip = ip;
		Donnees.port = Integer.parseInt(port);
		Donnees.language = language;
		Donnees.manager = manager;
		Donnees.mgr_user = user;
	}

	public static void reloadSettings() {
		settings = new Settings();
		ip = settings.getIp();
 		port = Integer.parseInt(settings.getPort());
 		language = settings.getLanguage();
		manager = settings.getManager();
		mgr_user = settings.getManagerUser();
		mgr_pass = settings.getManagerPass();
	}
}
