import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class InterfacePreferences extends JFrame implements ActionListener
{
	JButton boutonSauver;
	JButton boutonAnnuler;
	JTextField champsIp;
	JTextField champsPort;
	JTextField champsUser;
	JTextField champsPass;
	JComboBox<String> comboLangue;
	JComboBox<String> comboLogiciel;
	
	public InterfacePreferences(JFrame interfaceLiens)
	{
		super("Préférences");
		Donnees.reloadSettings();
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		setContentPane(panel);
		
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new GridLayout(1,2));
		boutonSauver = new JButton("Sauver");
		boutonAnnuler = new JButton("Annuler");
		boutonSauver.addActionListener(this);
		boutonAnnuler.addActionListener(this);
		panelBoutons.add(boutonSauver);
		panelBoutons.add(boutonAnnuler);
		JLabel labelLangue = new JLabel("Langue : ");
		JLabel labelIp = new JLabel("Adresse IP : ");
		JLabel labelPort = new JLabel("Port TCP : ");
		JLabel labelLogiciel = new JLabel("Gestionnaire : ");
		JLabel labelUser = new JLabel("Utilisateur : ");
		JLabel labelPass = new JLabel("Mot de passe : ");
		comboLangue = new JComboBox<String>(Donnees.listeLangues);
		for (int i=0; i<Donnees.listeLangues.length; i++)
			if(Donnees.language.equals(Donnees.listeLangues[i]))
				comboLangue.setSelectedIndex(i);
		comboLogiciel = new JComboBox<String>(Donnees.listeLogiciels);
		for (int i=0; i<Donnees.listeLogiciels.length; i++)
			if(Donnees.manager.equals(Donnees.listeLogiciels[i]))
				comboLogiciel.setSelectedIndex(i);
		champsIp = new JTextField();
		champsIp.setDocument(new PlainDocumentStruct(15));
		champsIp.setText(Donnees.ip);
		champsPort = new JTextField();
		champsPort.setDocument(new PlainDocumentStruct(5));
		champsPort.setText(String.valueOf(Donnees.port));
		champsUser = new JTextField();
		champsUser.setText(Donnees.mgr_user);
		champsPass = new JTextField();
		champsPass.setText("****");
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(6,1));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(6,1));
		panel1.add(labelLangue);
		panel2.add(comboLangue);
		panel1.add(labelLogiciel);
		panel2.add(comboLogiciel);
		panel1.add(labelIp);
		panel2.add(champsIp);
		panel1.add(labelPort);
		panel2.add(champsPort);
		panel1.add(labelUser);
		panel2.add(champsUser);
		panel1.add(labelPass);
		panel2.add(champsPass);
		panel.add("West",panel1);
		panel.add("Center",panel2);
		panel.add("South", panelBoutons);
		
		setVisible(true);
		pack();
		setSize(getSize().width + 30,getSize().height + 20);
	    this.setLocationRelativeTo(interfaceLiens);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == boutonSauver)
		{	
			Pattern pattern = Pattern.compile("^\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b$");
	        Matcher matcher = pattern.matcher(champsIp.getText());
	        if(matcher.find())
	        {
	        	try {
	    			Integer.parseInt(champsPort.getText());
	    			if(champsPass.getText().equals("****"))
	    			{
	    				System.err.println("ùùùùùùù");
	    				Donnees.setSettings(champsIp.getText(), champsPort.getText(), Donnees.listeLangues[comboLangue.getSelectedIndex()], Donnees.listeLogiciels[comboLogiciel.getSelectedIndex()], champsUser.getText());
	    			}
	    			else
	    				Donnees.setSettings(champsIp.getText(), champsPort.getText(), Donnees.listeLangues[comboLangue.getSelectedIndex()], Donnees.listeLogiciels[comboLogiciel.getSelectedIndex()], champsUser.getText(), champsPass.getText());
	    			this.dispose();
	    		} catch (NumberFormatException e1){
	    			JOptionPane.showMessageDialog(null, "Le port TCP ne doit contenir que des chiffres" , "Erreur de synthaxe Port TCP", JOptionPane.ERROR_MESSAGE);
	    			champsPort.setText(String.valueOf(Donnees.port));
	    		}
	        }
	        else
	        {
	        	JOptionPane.showMessageDialog(null, "L'adresse IP saisie doit être de la forme \"XXX.XXX.XXX.XXX\"" , "Erreur de synthaxe IP", JOptionPane.ERROR_MESSAGE);
	        	champsIp.setText(Donnees.ip);
	        }
		}
		if(e.getSource() == boutonAnnuler)
			this.dispose();
	}
}
