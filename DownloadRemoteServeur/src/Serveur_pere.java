import java.net.*;

import javax.swing.JOptionPane;

public class Serveur_pere {

	public static void main(String[] args)
	{
		Donnees donnees = new Donnees();

		int numero=1;
		try {
			ServerSocket service = new ServerSocket(Integer.parseInt(donnees.port));
			//ServerSocket service = new ServerSocket(5000);
			
			while(true)
			{
				Socket connexion = service.accept();
				numero++;
				new Serveur_fils(connexion, numero, donnees).start();				
			}
		} catch (Exception e) {e.printStackTrace();}
	}
}