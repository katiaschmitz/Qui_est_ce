import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Menu extends JFrame {
	
	private ImagePanel PanelPrincipal = new ImagePanel((new ImageIcon("fond.jpg").getImage()));

	/************* Declaration des boutons ***************************/
	
	private JButton BoutonOrdinateur = new JButton("Ordinateur");
	private JButton BoutonJoueursConnectes = new JButton("Joueurs Connecte");
	private JButton BoutonAmis= new JButton("Amis");
	
	private JLabel Titre = new JLabel("Menu");
	
	private GridBagConstraints gc = new GridBagConstraints();
	
	public Menu(){
		
		
		PanelPrincipal.setLayout(new GridBagLayout());
		
		gc.insets = new Insets(3, 3, 3, 3);
		gc.ipady = gc.anchor = GridBagConstraints.CENTER; 
		
		BoutonOrdinateur.setPreferredSize(new Dimension(200,150));
		BoutonJoueursConnectes.setPreferredSize(new Dimension(200,150));
		BoutonAmis.setPreferredSize(new Dimension(200,150));
		Titre.setPreferredSize(new Dimension(200,150));	
		
		
		
		gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        
        PanelPrincipal.add(Titre,gc);
        
        
		gc.gridx = 0;
        gc.gridy = 1;
        gc.gridwidth = 2;
        
        PanelPrincipal.add(BoutonOrdinateur,gc);
        
        
        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 1;
        
        PanelPrincipal.add(BoutonJoueursConnectes,gc);
		
		
        gc.gridx = 1;
        gc.gridy = 2;
     
        PanelPrincipal.add(BoutonAmis,gc);
		
		
		
		
		
        this.setResizable(false);
		this.setMinimumSize(new Dimension(1100,550));
		this.setPreferredSize(new Dimension(1100,550));
		this.add(PanelPrincipal);
		this.setContentPane(PanelPrincipal);
		this.setVisible(true);
		
	}
	
	
}
