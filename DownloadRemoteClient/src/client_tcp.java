import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class client_tcp 
{
	Socket connection;
	BufferedReader in;
	PrintWriter out;
	
	public client_tcp(String[] args) 
	{
		// Get All links
		String liens = new String();
		// No parameters, we run GUI
		if(args.length == 0)
			new InterfaceLiens(this);
		else
		{
			// Else, we concate all links
			for (int i = 0; i < args.length ; i++)
				liens+=" " + args[i];
			// Then, we send it to the server
			envoiLiens(liens);
		}
	}
	
	private boolean connect()
	{
		boolean loged = false;
		// Create connection socket, in and out flux
		try {
			connection = new Socket(Donnees.ip,Donnees.port);
			in = new BufferedReader(new InputStreamReader(connection.getInputStream())) ;
			out = new PrintWriter(connection.getOutputStream(), true);
			out.println("System Auth " + Donnees.mgr_user + " " + Donnees.mgr_pass);
			//out.println("System Auth quentin QJUB");
			out.flush();
			String ligne = null;
			ligne = in.readLine();
			System.out.println(ligne);
			if(ligne.equals("success"))
				loged = true;
			else
				JOptionPane.showMessageDialog(null, "Erreur : Utilisateur ou mot de passe invalide", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur : " + e, "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur : " + e, "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
		}
		return loged;
	}
	
	private void disconnect()
	{
		try {
			in.close();
			out.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur : " + e, "Erreur de déconnexion", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public boolean getServerStatus()
	{
		boolean connected = false;
		if(this.connect())
		{
			try {
				// Send test directive to the server
				out.println("System Status Connection");
				out.flush();
				// Get test result from the server
				String ligne = null;
				ligne = in.readLine();
				System.out.println(ligne);
				if(!ligne.equals("servConnOk"))
				{
					connected = false;
				}
				else
				{
					connected = true;
				}
			} catch (IOException e) {
				connected = false;
				e.printStackTrace();
			}
			this.disconnect();
			return connected;
		}
		return false;
	}
	
	public boolean getManagerStatus()
	{
		boolean runned = false;
		this.connect();
		try {
			// Send test directive to the server
			out.println("System Status Manager " + Donnees.manager);
			out.flush();
			// Get test result from the server
			String ligne = null;
			ligne = in.readLine();
			System.out.println(ligne);
			if(!ligne.equals("pyLoadOk"))
			{
				runned = false;
			}
			else
			{
				runned = true;
			}
		} catch (IOException e) {
			runned = false;
			e.printStackTrace();
		}
		
		this.disconnect();
		return runned;
	}
	
	public void envoiLiens(String liens)
	{
		this.connect();
		if(Donnees.manager.equals("PyLoad"))	
			out.println("Manager Add " + Donnees.user + " " + liens);
		else
			out.println(liens);
		out.flush();
		this.disconnect();
	}
}
