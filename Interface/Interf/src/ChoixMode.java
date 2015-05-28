import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ChoixMode extends JDialog implements ActionListener {
	
	/****** Declaration des boutons ********/
	
	JButton suivant ;
	JButton precedant ;
	JButton selectionner = new JButton ("Selectionner");
	
	/****** Declaration des Labels *****************/

	
	private ArrayList<ImageIcon> image = new ArrayList<ImageIcon>();
	ImageIcon img1 = new ImageIcon("dc.png");
	ImageIcon img2 = new ImageIcon("simpson2.png");
	ImageIcon img3 = new ImageIcon("marvel2.png");
	
	GridBagConstraints gc = new GridBagConstraints();
	
	BufferedImage droite;
	BufferedImage gauche;
	JLabel mode = new JLabel(img1);
	int nbClique = 0;
	
	public ChoixMode(JFrame parent, String title, boolean modal){

		
		
	    //On appelle le construteur de JDialog correspondant

	    super(parent, title, modal);
	
	    //On spécifie une taille
	    this.setLayout(new GridBagLayout());
	    
	    this.setSize(500, 250);

	    //La position

	    this.setLocationRelativeTo(null);
	    
	    try{
	    	droite = ImageIO.read(new File("droite.png"));
	    	gauche = ImageIO.read(new File("gauche.png"));
	    	suivant =new JButton(new ImageIcon(droite));
	    	precedant =new JButton(new ImageIcon(gauche));
	    	suivant.setBorder(BorderFactory.createEmptyBorder());
			suivant.setContentAreaFilled(false);
			suivant.addActionListener(this);
			precedant.setBorder(BorderFactory.createEmptyBorder());
			precedant.setContentAreaFilled(false);
			precedant.addActionListener(this);
	    }
	    catch (Exception ex) 
	    {
	    	System.out.println("Probleme dans la crÃ©ation des boutons");
	    }
	    
	    image.add(img1);
	    image.add(img2);
	    image.add(img3);
	    //La boîte ne devra pas être redimensionnable
	    
	    //suivant.setSize(50, 10);
	    gc.ipady = gc.anchor = GridBagConstraints.CENTER;
	    gc.insets = new Insets(3, 3, 3, 3);
	    
	    gc.gridx = 0;
        gc.gridy = 0;
	    add(precedant,gc);
	    
	    
	    majMode(0);
        
        
        
	    this.setResizable(false);

	    //Enfin on l'affiche
	   
	    
	    this.setVisible(true);

	    //Tout ceci ressemble à ce que nous faisons depuis le début avec notre JFrame.
	    
        
       
        
        Border incrustée = BorderFactory.createLoweredBevelBorder();
        Border cadre = BorderFactory.createLineBorder(new Color(0, 255, 0, 96), 5);
        
	  }
	
	public void majMode(int i)
	{
		remove(suivant);
		remove(selectionner);
		mode = new JLabel(image.get(i));
	   
	    gc.gridx = 1;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 1;
		add(mode, gc);
		
		gc.gridx = 3;
        gc.gridy = 0;
       //gc.gridwidth = GridBagConstraints.RELATIVE;
		add(suivant, gc);  
		
		gc.gridx = 0;
        gc.gridy = 1;
        gc.gridwidth = 3;
        gc.gridheight = GridBagConstraints.REMAINDER;
        add(selectionner, gc);
	}
	public void actionPerformed (ActionEvent arg0){
		
		if (arg0.getSource() == suivant){
			System.out.println("**************Suivant**************");
			
			if (nbClique<2){
				remove(mode);
				nbClique++;
				majMode(nbClique);	
			}
			System.out.println("clique = "+nbClique );
			revalidate();
            repaint();
		}
		if (arg0.getSource() == precedant){
			System.out.println("**************PRECEDANT**************");
			
			if (nbClique > 0){
				remove(mode);
				nbClique--;
				majMode(nbClique);
			}
			System.out.println("clique = "+nbClique );
			revalidate();
            repaint();
            
		}
	}
		

	}

