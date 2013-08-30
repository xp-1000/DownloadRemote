import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class InterfaceAPropos extends JFrame
{
	public InterfaceAPropos(JFrame interfaceLiens)
	{
		super("A propos");
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		setContentPane(panel);
		
		JLabel labImage = new JLabel(new ImageIcon("download_box.png"));
		JLabel labelTitre = new JLabel("DownloadRemote v1.2.1");
		JLabel labelDescription1 = new JLabel("Le logiciel pour commander vos");
		JLabel labelDescription2 = new JLabel("téléchargements à distance.");
		JLabel labelC1 = new JLabel("Quentin Manfroi :");
		JLabel labelC2 = new JLabel("Tous droits réservés");
		labelTitre.setSize(500, 500);
		this.ajouteComposant(labImage,0,0,1,6);
		this.ajouteComposant(labelTitre,1,0,1,1);
		this.ajouteComposant(labelDescription1,1,1,1,1);
		this.ajouteComposant(labelDescription2,1,2,1,1);
		this.ajouteComposant(labelC1,1,3,1,1);
		this.ajouteComposant(labelC2,1,4,1,1);
		
		setVisible(true);
		pack();
		setSize(getSize().width + 100,getSize().height + 10);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(interfaceLiens);
	}
	
	private void ajouteComposant(Component parComposant,
			int parColonne, int parLigne, int parLargeur, int parHauteur)
	{
		GridBagConstraints contraintes = new GridBagConstraints();
		contraintes.anchor = GridBagConstraints.NORTHWEST;
		contraintes.gridx=parColonne;
		contraintes.gridy=parLigne;
		contraintes.gridwidth=parLargeur;
		contraintes.gridheight=parHauteur;
		contraintes.weightx=1;
		contraintes.weighty=1;
		contraintes.fill=GridBagConstraints.NORTH;
		contraintes.insets = new Insets(10,10,0,0);
		this.add(parComposant, contraintes);
	}

}
