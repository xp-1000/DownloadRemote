import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultEditorKit;

import org.omg.CORBA.INTERNAL;


public class InterfaceLiens extends JFrame implements ActionListener
{

	client_tcp client_tcp;
	//JTextField champsLiens = new JTextField();
	JTextArea champsLiens = new JTextArea();
	JButton boutonEnvoyer = new JButton("Envoyer");
	JButton boutonAnnuler = new JButton("Annuler");
	JLabel label = new JLabel("Copier vos liens ici :");
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
		panelBoutons.add(boutonEnvoyer);
		panelBoutons.add(boutonAnnuler);
		panel.add("South",panelBoutons);
		boutonEnvoyer.addActionListener(this);
		boutonAnnuler.addActionListener(this);
		
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
			InterfacePreferences interfacePreferences = new InterfacePreferences(interfaceLiens);
			}});
		JMenu menuAide = new JMenu("?");
		menuBar.add(menuAide);
		JMenuItem itemAPropos = new JMenuItem("A propos");
		menuAide.add(itemAPropos);
		itemAPropos.addActionListener(new ActionListener() 
		{public void actionPerformed(ActionEvent e) 
			{
				InterfaceAPropos interfaceAPropos = new InterfaceAPropos(interfaceLiens);
			}});
		this.setJMenuBar(menuBar);
				
		setVisible(true);
		pack();
		setSize(getSize().width + 500,getSize().height + 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
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
			this.dispose();
			client_tcp.envoiLiens(liens);
		}
	}
	
}
