import java.io.*;
import java.net.Socket;

public class Serveur_fils extends Thread {

    private Socket connexion;
	private int numero;
	private String lien;
	
	public Serveur_fils(Socket parConnexion, int parNumero)
	{
		connexion = parConnexion;
		numero=parNumero;
	}
	
	public void run()
	{
		try {
			BufferedReader entree = new BufferedReader(new InputStreamReader(connexion.getInputStream())) ;
			BufferedWriter sortie = new BufferedWriter(new OutputStreamWriter(connexion.getOutputStream()));
			//PrintWriter out = new PrintWriter(connexion.getOutputStream(),true) ;
			//out.println("Bonjour, Bienvenue sur mon serveur ...");
			String ligne = entree.readLine();
			// System.out.println(ligne);
			sortie.write("success");
			sortie.flush() ;
			envoiCommande("pyLoadCli --username=kiwis --pw=Azertyui91! --address=192.168.100.253 --port=7227 add ", ligne);
			
			sortie.close();
			entree.close();
			connexion.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private void envoiCommande(String commande, String arg)
	{
		try { 
			 //creation du processus 
			 Process p = Runtime.getRuntime().exec(commande + " " + arg); 
			 InputStream in = p.getInputStream(); 

			 //on rÃ©cupÃ¨re le flux de sortie du programme 

			 StringBuilder build = new StringBuilder(); 
			 char c = (char) in.read(); 

			 while (c != (char) -1) { 
			 build.append(c); 
			 c = (char) in.read(); 
			 } 

			 String response = build.toString(); 

			 //on l'affiche 
			 System.out.println(response); 
			 } 
			 catch (Exception e) { 
			 System.out.println("\n" + commande + ": commande inconnu "); 
			 } 
	}
}