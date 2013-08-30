import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class client_tcp 
{
	
	String adr_ip = Donnees.ip;
	int port = Integer.parseInt(Donnees.port);
	
	public client_tcp(String[] args) 
	{
		
		String liens = new String();
		
		if(args.length == 0)
			new InterfaceLiens(this);
		else
		{
			for (int i = 0; i < args.length ; i++)
				liens+=" " + args[i];
			envoiLiens(liens);
		}
	}
		public void envoiLiens(String liens)
		{
			try
			{
				Socket connexion = new Socket(adr_ip,port);
				BufferedReader entree = new BufferedReader(new InputStreamReader(connexion.getInputStream())) ;
				PrintWriter sortie = new PrintWriter(connexion.getOutputStream(), true); 
				if(Donnees.manager.equals("PyLoad"))	
					sortie.println(System.getProperties().getProperty ("user.name") + " " + liens);
				else
					sortie.println(liens);
				sortie.flush();
				String ligne = entree.readLine();
				if(!ligne.equals("success"))
					JOptionPane.showMessageDialog(null, "Erreur : paquet perdu" , "Erreur envoi/réception paquet", JOptionPane.ERROR_MESSAGE);
				else
				{
					System.out.println("Paquet bien envoyé");
					JOptionPane.showMessageDialog(null, "Paquet envoyé avec succès");
				}
				entree.close();
				sortie.close();
				connexion.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erreur : " + e, "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
			}
			//System.exit(0);
		}//fin de try
}
