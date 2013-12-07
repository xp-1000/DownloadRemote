import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class Serveur_fils extends Thread {

    private Socket connexion;
	private int numero;
	private String lien;
	private Donnees donnees;
	static boolean loged = false;
	
	public Serveur_fils(Socket parConnexion, int parNumero, Donnees parDonnees)
	{
		connexion = parConnexion;
		numero=parNumero;
		donnees = parDonnees;
	}
	
	public void run()
	{
		
		try {
			BufferedReader entree = new BufferedReader(new InputStreamReader(connexion.getInputStream())) ;
			BufferedWriter sortie = new BufferedWriter(new OutputStreamWriter(connexion.getOutputStream()));
			//PrintWriter out = new PrintWriter(connexion.getOutputStream(),true) ;
			//out.println("Bonjour, Bienvenue sur mon serveur ...");
			while(true)
			{
			String ligne = entree.readLine();
			
			System.out.println(ligne);
			
			StringTokenizer tokenizer = new StringTokenizer(ligne);

			if(tokenizer.hasMoreTokens()) 
			{
				String token = tokenizer.nextToken();
			    if(token.equals("System"))
			    {
			    	if(tokenizer.hasMoreTokens())
			    	{
				    	token = tokenizer.nextToken();
				    	if(token.equals("Status"))
				    	{
				    		if(tokenizer.hasMoreTokens())
				    		{
					    		token = tokenizer.nextToken();
					    		if(token.equals("Connection"))
					    			sortie.write("servConnOk");
					    		else if(token.equals("Manager"))
					    		{
					    			if(tokenizer.hasMoreTokens())
						    		{
					    				token = tokenizer.nextToken();
						    			if(token.equals("PyLoad"))
						    			{
						    				String response=envoiCommande("pyLoadCli --username=kiwis --pw=Azertyui91! status",null);

						    				if(!response.toLowerCase().contains("Could not establish connection to".toLowerCase()))
						    					sortie.write("pyLoadOk");
						    				else
						    					sortie.write("pyLoadKo");
						    			}
						    			else
						    				sortie.write(token + "Ok");
							    	}
					    			else
					    				sortie.write("cmdAttempMgr");
					    		}
					    		else
					    			sortie.write("cmdNotFoundfff");
				    		}
				    		else
				    			sortie.write("cmdAttempArg");
				    	}
				    	else if (token.equals("Auth"))
				    	{
				    		System.err.println("COOOL");
				    		if(tokenizer.hasMoreTokens())
				    		{
					    		String username = tokenizer.nextToken();
					    		if(tokenizer.hasMoreTokens())
					    		{
					    			String password = tokenizer.nextToken();
					    			String test = donnees.auth(username, password);
					    			if(test.equals("success"))
					    				System.err.println("OKKKK");
					    			else
					    				System.err.println("KOOOO");	
					    			sortie.write(test);
					    		}
					    		else
					    			sortie.write("cmdAttempArgPass");
				    		}
					    	else
					    		sortie.write("cmdAttempArgUser");
				    		
				    	}
				    	else
				    		sortie.write("cmdNotFoundsss");
			    	}
			    	else
			    		sortie.write("cmdAttempArg");
			    }
			    else if(token.equals("Manager"))
			    {
			    	if(tokenizer.hasMoreTokens())
			    	{
			    		token = tokenizer.nextToken();
				    	if(token.equals("Add"))
				    	{
				    		String links = new String();
				    		while(tokenizer.hasMoreTokens())
				    		{
				    			links+= " " + tokenizer.nextToken();
				    		}
				    		System.err.println(links);
				    		//envoiCommande("pyLoadCli --username=kiwis --pw=Azertyui91! --address=192.168.100.253 --port=7227 add ", links);
				    		envoiCommande("pyLoadCli --username=kiwis --pw=Azertyui91! add ", links);
				    	}
			    	}
			    	else
			    		sortie.write("cmdAttempArg");
			    }
			    else
		    		sortie.write("cmdNotFound1");
			}
			
			sortie.flush() ;
			}
			sortie.close();
			entree.close();
			connexion.close();
		
		} catch (IOException e) {e.printStackTrace();}
	}
		
	private String envoiCommande(String commande, String arg)
	{
		String response = null;
		try { 
			 //creation du processus 
			String cmd = commande;
			if(arg!=null)
				cmd+=" " + arg;
			 Process p = Runtime.getRuntime().exec(cmd); 
			 InputStream in = p.getInputStream(); 

			 //on récupère le flux de sortie du programme 

			 StringBuilder build = new StringBuilder(); 
			 char c = (char) in.read(); 

			 while (c != (char) -1) { 
			 build.append(c); 
			 c = (char) in.read(); 
			 } 

			 response = build.toString(); 
			 
			 } 
			 catch (Exception e) { 
			 System.out.println("\n" + commande + ": commande inconnu "); 
			 } 
		return response; 
	}
}