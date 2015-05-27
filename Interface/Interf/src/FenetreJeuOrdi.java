

	import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Handler;

	import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

	/** Cette classe correspond à la fenetre de jeu 
	 * Dans celle ci sera 
	 * -Cree les differents boutons personnages
	 * -Le Tchat
	 *                                                */
	public class FenetreJeuOrdi extends JFrame implements ActionListener {
		
		
		
		
		/*************** Case Personnage choisie ********************/
		
		
		 ImageIcon main = new ImageIcon("choix.png");
		 JLabel choix = new JLabel(main);
		 
		
		/*************** Declaration Panel avec fond d'ecran********************/
		
	
		private ImagePanel PanelPerso = new ImagePanel((new ImageIcon("fond.jpg").getImage()));
		
		/*************** Declaration Case Personnages********************/
		
	
		private JButton boutonAux ;
		BufferedImage buttonIcon3;	
		private ArrayList<JButton> boutonPersonnage = new ArrayList<JButton>();
		private ArrayList<JButton> boutonPersonnageAdv = new ArrayList<JButton>();
		
		
		/*************** Declaration Bouton Question, proposition********************/
		
		private JButton question=new JButton("Question") ;
		
		private JButton proposition=new JButton("Proposition") ;

		
		/*************** Mini Panel********************/
		
		private JPanel miniPanel = new JPanel();
		
		
		/*************** Jeux Adversaire ********************/
		
		private ImagePanel JeuxAdversaire =  new ImagePanel((new ImageIcon("orange.jpg").getImage()));
		
		/*************** Declaration bouton valider ********************/
		//private JButton validerChoix=new JButton("Valider Choix") ;
		
		
		/**** Le gridBagConstraints va définir la position et la taille des éléments ****/
		GridBagConstraints gc = new GridBagConstraints();
		GridBagConstraints gc2 = new GridBagConstraints();
		GridBagConstraints gc3 = new GridBagConstraints();
	
		public FenetreJeuOrdi (){
			
			
			this.setPreferredSize(new Dimension(1100,550));
			
			/****  GridLayout servira à bien organiser notre grille de personnage *****/
			
			PanelPerso.setLayout(new GridBagLayout());
			miniPanel.setLayout(new GridBagLayout());
			miniPanel.setBackground(new Color(0,0,0,0));
			JeuxAdversaire.setLayout(new GridBagLayout());
			JeuxAdversaire.setBackground(Color.yellow);
			JeuxAdversaire.setBorder(BorderFactory.createLineBorder(Color.red));
			
			/**** Definition de la marge entre les composant new Insets(margeSupérieure, margeGauche, margeInférieur, margeDroite) */
			
			gc.insets = new Insets(3, 3, 3, 3);
			gc2.insets = new Insets(3, 3, 3, 3);
			gc3.insets = new Insets(3, 3, 3, 3);
			
			gc.ipady = gc.anchor = GridBagConstraints.CENTER; 
			gc2.ipady = gc2.anchor = GridBagConstraints.CENTER; 
			gc3.ipady = gc3.anchor = GridBagConstraints.CENTER; 
			
			/**********************************************/
			
			
			
			/********************** Placement du choix du personnage ***********/
			
			
			gc.gridx = 0;
            gc.gridy = 0;
            gc.gridwidth = 2;
            
            gc.insets = new Insets(0, 0, 0, 0);
            PanelPerso.add(choix,gc);
            gc.insets = new Insets(3, 3, 3, 3);
            gc.gridwidth = 1;
            
            
			/********************Placement bouton de validation *****************************/
            
           /* gc.gridx = 8;
            gc.gridy = 1;
            //gc.gridwidth = GridBagConstraints.REMAINDER;
            gc.anchor = GridBagConstraints.CENTER;
            gc.fill = GridBagConstraints.HORIZONTAL;
            PanelPerso.add(validerChoix,gc);*/
            
            //gc.ipady = gc.anchor = GridBagConstraints.CENTER; 
            
            /********************Placement bouton proposition************************/
			
            gc.gridx = 8;
            gc.gridy = 0;
            gc.anchor = GridBagConstraints.PAGE_END;
            gc.fill = GridBagConstraints.HORIZONTAL;
            
            PanelPerso.add(proposition,gc);
            
            /********************Placement bouton Question************************/
			question.setPreferredSize(new Dimension (200,25));
			question.addActionListener(this);
            gc.gridx = 0;
            gc.gridy = 2;
            gc.gridwidth = 2;

            //gc.anchor = GridBagConstraints.CENTER;
            gc.fill = GridBagConstraints.HORIZONTAL;
            PanelPerso.add(question,gc);
            gc.gridwidth = 1;
            
            
		    
		    CreationBouton();
		    
		    
		    PaletteAdversaire ();
		   
		    
		    this.setResizable(false);
		    this.add(PanelPerso);
		    this.pack(); 
			this.setContentPane(PanelPerso);
		    
	        this.setVisible(true);
		    //this.invalidate();
			//this.validate();
			
		}
		/** La classe CreationBouton creera les differents boutons avec les images corespondantes 
		 * de plus on fera en sorte que seul l'image correspondante soit afficher en tant que bouton 
		 * 
		 * */
		public void CreationBouton (){
		
			int i = 0;
			while (i<24){
				for(int y=0; y<4;y++){
						for(int x=2; x<8; x++){
						  try{		
							  
							  buttonIcon3 = ImageIO.read(new File("j"+i+".png"));
							  boutonAux =new JButton(new ImageIcon(buttonIcon3));
							  boutonAux.setBorder(BorderFactory.createEmptyBorder());
							  boutonAux.setContentAreaFilled(false);
							  boutonAux.setPressedIcon(new ImageIcon("j"+i+"_"+i+".png"));
							  boutonAux.setSelectedIcon(new ImageIcon("Click.png"));
							  boutonAux.addActionListener(this);
							  boutonPersonnage.add(boutonAux);
							  gc.anchor = GridBagConstraints.BASELINE;
							  gc.gridx = x;
                              gc.gridy = y;
							  
							  PanelPerso.add(boutonPersonnage.get(i),gc);
							  //JeuxAdversaire.add(boutonPersonnage.get(i),gc3);
							  System.out.println("Bouton Personnage"+i+"crÃ©e à la position x="+x+" y="+y);
							  i++;
						  } catch (Exception ex) {System.out.println("Probleme dans la crÃ©ation des boutons");}
				 	
				}
			}}
		}
		
		public void PaletteAdversaire (){
		   JLabel adv = new JLabel ("Adversaire"); 
			gc3.gridx = 0;
            gc3.gridy = 0;
            gc3.gridwidth = 6;
            gc3.anchor = GridBagConstraints.CENTER;
            gc3.gridwidth = GridBagConstraints.REMAINDER;
            //gc3.anchor = GridBagConstraints.PAGE_START;
            
            
            JeuxAdversaire.add(adv,gc3);
            gc3.gridwidth = 1;
			int i = 0;
			while (i<24){
			for(int y=1; y<5;y++){
				for(int x=0; x<6; x++){
						  try{		
							  buttonIcon3 = ImageIO.read(new File("mini/j"+i+".png"));
							  boutonAux =new JButton(new ImageIcon(buttonIcon3));
							  boutonAux.setBorder(BorderFactory.createEmptyBorder());
							  boutonAux.setContentAreaFilled(false);
							  //boutonAux.setSelectedIcon(new ImageIcon("Click.png"));
							//  boutonAux.addActionListener(this);
							  boutonPersonnageAdv.add(boutonAux);
							  gc3.anchor = GridBagConstraints.BASELINE;
							
							  gc3.gridx = x;
                              gc3.gridy = y;
							  
							
							  JeuxAdversaire.add(boutonPersonnageAdv.get(i),gc3);
							  System.out.println("Bouton Personnage"+i+"crÃ©e à la position x="+x+" y="+y);
							  i++;
						  } catch (Exception ex) {System.out.println("Probleme dans la crÃ©ation des boutons");}}
						  
							gc.gridheight =2;
							gc.anchor = GridBagConstraints.BASELINE;
				            gc.fill = GridBagConstraints.VERTICAL;
						  gc.gridx = 8;
						  gc.gridy = 2;
		            
		            PanelPerso.add(JeuxAdversaire, gc);
				}
			}
		}
		
	
		/****************** Methode permettant de generer un son ***********/
		public void sonVictoire(){
			try {
			    AudioInputStream stream;
			    AudioFormat format;
			    DataLine.Info info;
			    Clip clip;

			    stream = AudioSystem.getAudioInputStream(new File("mario.wav"));
			    format = stream.getFormat();
			    info = new DataLine.Info(Clip.class, format);
			    clip = (Clip) AudioSystem.getLine(info);
			    clip.open(stream);
			    clip.start();
			}
			catch (Exception e) {
			    System.out.println("Errrrrrreur");
			}
		}
		
		  
		  public ArrayList getListImage()
		  {
		 	 return boutonPersonnage;
		  }

		  /*public void setImageChoisie(int i)
		  {
		 	   System.out.println("debut setimagechoisie");

		 	 imageChoisie.setIcon(new ImageIcon("j"+i+".jpg"));
		 	  this.validate();

		  }*/

		  public void griser(int i)
		  {	  System.out.println("debut griser");
		      
		  	boutonPersonnage.get(i).setSelected(!boutonPersonnage.get(i).isSelected());
		 	 boutonPersonnage.get(i).setEnabled(false);
		 	 this.validate();

		  }

		  public JButton getProposition()
		  {
		 	 return proposition;
		  }
		  
		  public void actionPerformed (ActionEvent arg0){
			 if  (arg0.getSource() == question)
			 {			 
				 DialogQuestion zd = new DialogQuestion(null, "Questions", true);
			 }
			 else if (boutonPersonnage.contains(arg0.getSource())){
				 for(int k = 0; k<boutonPersonnage.size(); k++){
					 
					 if (boutonPersonnage.get(k).equals(arg0.getSource()))
				 	System.out.println("Bouton"+k+"cliquer");
				 }
			 }
		  }

		
	}
			
		