import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Menu extends JFrame implements ActionListener {
	
	private ImagePanel PanelPrincipal = new ImagePanel((new ImageIcon("fond.jpg").getImage()));

	/************* Declaration des boutons ***************************/
	
	private JButton BoutonOrdinateur = new JButton("Ordinateur");
	private JButton BoutonJoueursConnectes = new JButton("Joueurs Connecte");
	private JButton BoutonAmis= new JButton("Amis");
	
	private JLabel Titre = new JLabel("Menu");
	private Joueur j;
	private GridBagConstraints gc = new GridBagConstraints();
	
	public Menu(Joueur j){
		this.j = j;
	
		
		PanelPrincipal.setLayout(new GridBagLayout());
		
		gc.insets = new Insets(3, 3, 3, 3);
		gc.ipady = gc.anchor = GridBagConstraints.CENTER; 
		
		BoutonOrdinateur.setPreferredSize(new Dimension(200,150));
		BoutonJoueursConnectes.setPreferredSize(new Dimension(200,150));
		BoutonAmis.setPreferredSize(new Dimension(200,150));
		Titre.setPreferredSize(new Dimension(200,150));	
		
		BoutonOrdinateur.addActionListener(this);
		BoutonJoueursConnectes.addActionListener(this);
		BoutonAmis.addActionListener(this);
		
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
	
	public void actionPerformed(ActionEvent arg0){
		if (arg0.getSource() == BoutonOrdinateur){
			new ChoixMode(null, "Questions", true);
		}
		if (arg0.getSource() == BoutonJoueursConnectes){
	
			new SelectionAdversaire(j);
			this.setVisible(false);
		}
		
	}
	
	 public boolean AfficherBoiteDialogue(String pseudo){
		    JOptionPane jop = new JOptionPane();
		    int option = jop.showConfirmDialog(null, pseudo+" vous invite � jouer une partie", "Invitation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		    if(option == JOptionPane.OK_OPTION){
		    	return true;
		    }
		    else return
		    		false;

	  }
	
}
