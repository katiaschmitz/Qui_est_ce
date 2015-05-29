import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Accueil extends JFrame {
	
	
	 private JTextField SaisiePseudo = new JTextField();
	 private JLabel Pseudo = new JLabel("PSEUDO");
	 private JTextField SaisiePassword = new JTextField();
	 private JLabel Password = new JLabel("MOT DE PASSE");
	 private JButton connexion = new JButton("Connexion");
	 private JButton inscription = new JButton("Inscription");
	 private ImagePanel PanelAccueil = new ImagePanel((new ImageIcon("fond.jpg").getImage()));
	 
	 GridBagConstraints gc = new GridBagConstraints();
	
	public Accueil(Joueur joueur){
		
		this.setPreferredSize(new Dimension(1100,550));
		PanelAccueil.setLayout(new GridBagLayout());
		SaisiePseudo.setPreferredSize(new Dimension (200,10));
		SaisiePassword.setPreferredSize(new Dimension (200,10));
		
		gc.insets = new Insets(3, 3, 3, 3);
		gc.ipady = gc.anchor = GridBagConstraints.CENTER; 
		
		
		gc.gridx = 0;
        gc.gridy = 0;        
        PanelAccueil.add(Pseudo, gc);
        
        gc.gridx = 1;
        gc.gridy = 0;
        PanelAccueil.add(SaisiePseudo, gc);
        
        gc.gridx = 0;
        gc.gridy = 1;
        PanelAccueil.add(Password, gc);
        
        gc.gridx = 1;
        gc.gridy = 1;
        PanelAccueil.add(SaisiePassword, gc);
        
        /************* Bouton Connexion *****************/
        
        connexion.addActionListener(joueur);
        gc.gridx = 0;
        gc.gridy = 2;
        PanelAccueil.add(connexion, gc);
        
        /************** Bouton Inscription ***************/
       
        inscription.addActionListener(joueur);
        gc.gridx = 1;
        gc.gridy = 2;
        PanelAccueil.add(inscription, gc);
        
        this.setResizable(false);
        this.pack(); 
        this.setContentPane(PanelAccueil);      
  		this.setVisible(true);
		
	}
	public JButton getBConnexion(){
		  return connexion;
	  }

	  public JButton getBInscription(){
		  return inscription;
	  }

	  public String getPseudo(){
		  return SaisiePseudo.getText();
	  }

	  public String getMdp(){
		  return SaisiePassword.getText();
	  }
	
}
