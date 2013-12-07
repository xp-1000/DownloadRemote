import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultEditorKit;


@SuppressWarnings("serial")
public class InterfaceLiens extends JFrame implements ActionListener
{

	client_tcp client_tcp;
	//JTextField champsLiens = new JTextField();
	JTextArea champsLiens = new JTextArea();
	JButton boutonEnvoyer = new JButton("Envoyer");
	JButton boutonAnnuler = new JButton("Annuler");
	JButton testConn = new JButton("Tester la connexion");
	JButton testMngr = new JButton("Tester le gestionnaire");
	JLabel label = new JLabel("Copiez vos liens ici :");
	JLabel liStatus = new JLabel("Déconnecté");
	JLabel liManager = new JLabel(Donnees.manager);
	JPanel panel = new JPanel();
	JFrame interfaceLiens = this;
	
	public InterfaceLiens(client_tcp parclient_tcp)
	{
		super("Envoyer des liens");
		setContentPane(panel);
		client_tcp = parclient_tcp;
		panel.setLayout(new BorderLayout());
		panel.add("North",label);
		//champsLiens.setPreferredSize(new Dimension(500, 300));
		JScrollPane scrollpane = new JScrollPane(champsLiens);
		panel.add("Center",scrollpane);
		JPanel panelBoutons = new JPanel();
		panelBoutons.add(boutonAnnuler);
		panelBoutons.add(testConn);
		panelBoutons.add(testMngr);
		panelBoutons.add(boutonEnvoyer);
		panel.add("South",panelBoutons);
		boutonEnvoyer.addActionListener(this);
		boutonAnnuler.addActionListener(this);
		testMngr.addActionListener(this);
		testConn.addActionListener(this);
		JPanel panelInfos = new JPanel();
		panelInfos.setLayout(new GridLayout(5, 2));
		JLabel lcStatus = new JLabel("Statut :");
		liStatus.setForeground(Color.red);
		JLabel lcAddress = new JLabel("Adresse :");
		JLabel liAddress = new JLabel(Donnees.ip);
		JLabel lcPort = new JLabel("Port :");
		JLabel liPort = new JLabel(String.valueOf(Donnees.port));
		JLabel lcManager = new JLabel("Gestionnaire :");
		JLabel lcUser = new JLabel("Utilisateur :");
		JLabel liUser = new JLabel(Donnees.user);
		panelInfos.add(lcStatus);
		panelInfos.add(liStatus);
		panelInfos.add(lcAddress);
		panelInfos.add(liAddress);
		panelInfos.add(lcPort);
		panelInfos.add(liPort);
		panelInfos.add(lcManager);
		panelInfos.add(liManager);
		panelInfos.add(lcUser);
		panelInfos.add(liUser);
		panel.add("East",panelInfos);
		
		JPopupMenu menu = new JPopupMenu();
		ActionMap actionMap = champsLiens.getActionMap();
		actionMap.get(DefaultEditorKit.copyAction);
		JMenuItem itemCopier = new JMenuItem("Copier");
		menu.add(itemCopier);
		itemCopier.addActionListener(new ActionListener() 
		{public void actionPerformed(ActionEvent e) 
			{champsLiens.copy();}});
		JMenuItem itemCouper = new JMenuItem("Couper");
		menu.add(itemCouper);
		itemCouper.addActionListener(new ActionListener() 
		{public void actionPerformed(ActionEvent e) 
			{champsLiens.cut();}});
		JMenuItem itemColler = new JMenuItem("Coller");
		menu.add(itemColler);
		itemColler.addActionListener(new ActionListener() 
		{public void actionPerformed(ActionEvent e) 
			{champsLiens.paste();}});
		champsLiens.setComponentPopupMenu(menu);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFichier = new JMenu("Fichier");
		menuBar.add(menuFichier);
		JMenuItem itemQuitter = new JMenuItem("Quitter");
		menuFichier.add(itemQuitter);
		itemQuitter.addActionListener(new ActionListener() 
		{public void actionPerformed(ActionEvent e) 
			{System.exit(0);}});
		JMenu menuEdition = new JMenu("Edition");
		menuBar.add(menuEdition);
		JMenuItem itemPreferences = new JMenuItem("Préférences");
		menuEdition.add(itemPreferences);
		itemPreferences.addActionListener(new ActionListener() 
		{public void actionPerformed(ActionEvent e) 
			{
				new InterfacePreferences(interfaceLiens);
			}});
		JMenu menuAide = new JMenu("?");
		menuBar.add(menuAide);
		JMenuItem itemAPropos = new JMenuItem("A propos");
		menuAide.add(itemAPropos);
		itemAPropos.addActionListener(new ActionListener() 
		{public void actionPerformed(ActionEvent e) 
			{
				new InterfaceAPropos(interfaceLiens);
			}});
		this.setJMenuBar(menuBar);
				
		setVisible(true);
		pack();
		setSize(getSize().width + 20,getSize().height + 20);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        testConnection();
        testManager();
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == boutonAnnuler)
			System.exit(0);
		if(arg0.getSource() == boutonEnvoyer)
		{
			String liens = new String(" ");
			String texte = champsLiens.getText();
			int test = 0;
			for (int i = 0; i < texte.length(); ++i ) 
			{
			       char c = texte.charAt(i);
			       int j = (int) c;
			       if(test == 4)
			       {
			    	   if(j == 10)
			    	   {
			    		   c = (char) 32;
			    		   test = 0;
			    	   }
	    			   liens += c;
			       }
				    	   
			       if(c == 'h' && test == 0)
			    	   test ++;
			       else
			    	   if(c == 't' && (test == 1 || test == 2))
			    		   test ++;
			    	   else
			    		   if(c == 'p' && test == 3)
			    		   {
			    			   test ++;
			    			   liens += "http";
			    		   }
			       
			       if(c == 'w' && (test == 0 || test == 1 || test == 2))
			    	   test ++;
			       else
			    	   if(c == '.' && test == 3)
			    	   {
			    		   test ++;
			    		   liens += "www.";
		    		   }
	       }
			if(client_tcp.getServerStatus())
			{
				if(client_tcp.getManagerStatus())
				{
					client_tcp.envoiLiens(liens);
					this.dispose();
				}
				else
					JOptionPane.showMessageDialog(null, "Impossible d'envoyer les liens car " + Donnees.manager + " ne répond pas sur le serveur", "Erreur de gestionnaire externe", JOptionPane.ERROR_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(null, "Impossible d'envoyer les liens car le serveur DownloadRemote ne répond pas", "Erreur de gestionnaire externe", JOptionPane.ERROR_MESSAGE);
		}
		if (arg0.getSource() == testConn)
		{
			testConnection();
		}
		if (arg0.getSource() == testMngr)
		{
			testManager();
		}
		
	}

	private void testManager() {
		if(client_tcp.getManagerStatus())
			liManager.setForeground(new Color(0,150,50));
		else
			liManager.setForeground(Color.red);
		
	}

	private void testConnection() {
		if(client_tcp.getServerStatus())
		{
			liStatus.setForeground(new Color(0,150,50));
			liStatus.setText("Connecté");
		}
		else
		{
			liStatus.setForeground(Color.red);
			liStatus.setText("Déconnecté");
		}
	}
	
}
