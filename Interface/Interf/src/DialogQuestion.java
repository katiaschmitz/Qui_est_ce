import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.Border;

public class DialogQuestion extends JDialog {
	
	JButton bouton1 = new JButton ("Question 1");
	JButton bouton2 = new JButton ("Question 2");
	JButton bouton3 = new JButton ("Question 3");
	JButton bouton4 = new JButton ("Question 4");
	JButton bouton5 = new JButton ("Question 5");
	JButton bouton6 = new JButton ("Question 6");
	JButton bouton7 = new JButton ("Question 7");
	JButton bouton8 = new JButton ("Question 8");
	public DialogQuestion(JFrame parent, String title, boolean modal){

	    //On appelle le construteur de JDialog correspondant

	    super(parent, title, modal);
	
	    //On spécifie une taille

	    this.setSize(800, 400);

	    //La position

	    this.setLocationRelativeTo(null);

	    //La boîte ne devra pas être redimensionnable
	    
	    bouton1.setSize(300, 20);
	    setLayout(new GridLayout(8,1));
        add(bouton1);
        add(bouton2);
        add(bouton3);
        add(bouton4);
        add(bouton6);
        add(bouton7);
        add(bouton8);

	    this.setResizable(false);

	    //Enfin on l'affiche

	    this.setVisible(true);

	    //Tout ceci ressemble à ce que nous faisons depuis le début avec notre JFrame.
	    
	    setLayout(new GridLayout(8,1));
        add(bouton1);
        add(bouton2);
        add(bouton3);
        add(bouton4);
        add(bouton6);
        add(bouton7);
        add(bouton8);
        
        Border incrustée = BorderFactory.createLoweredBevelBorder();
        Border cadre = BorderFactory.createLineBorder(new Color(0, 255, 0, 96), 5);
        
	  }

	}

