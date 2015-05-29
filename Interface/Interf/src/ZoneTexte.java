
/* On va se servir de cette classe afin de crée un arrière plan pour notre fenetre */
	import java.awt.Graphics;
import java.awt.Image; 

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ZoneTexte extends JTextArea {
	 
	   private Image img;
	     
	    public ZoneTexte(Image img){
	        this.img = img;
	        setOpaque(false);
	    }
	     
	    public void paintComponent(Graphics g) {
	        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	        super.paintComponent(g);
	    }
	}
