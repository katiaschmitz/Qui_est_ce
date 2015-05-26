

	import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
	public class FenetreGird extends JFrame {
		
		
		

		/*************** Case Personnage choisie ********************/
		
		
		 ImageIcon main = new ImageIcon("choix.png");
		 JLabel choix = new JLabel(main);
		 
		
		/*************** Declaration Panel avec fond d'ecran********************/
		
	
		private ImagePanel PanelPerso = new ImagePanel((new ImageIcon("game.jpg").getImage()));
		
		/*************** Declaration Case Personnages********************/
		
	
		private JButton boutonAux ;
		BufferedImage buttonIcon3;	
		private ArrayList<JButton> boutonPersonnage = new ArrayList<JButton>();
		private ArrayList<JButton> boutonPersonnageAdv = new ArrayList<JButton>();
		
		
		/*************** Declaration Chat********************/
		
		private JButton validerChat=new JButton("envoyer") ;
		private JButton proposition=new JButton("Proposition") ;
		private JTextField saisieChat = new JTextField();
		private JTextArea texteChat = new JTextArea("");
		private JScrollPane scroll = new JScrollPane(texteChat);
		
		/*************** Mini Panel********************/
		
		private JPanel miniPanel = new JPanel();
		
		
		/*************** Jeux Adversaire ********************/
		
		private ImagePanel JeuxAdversaire =  new ImagePanel((new ImageIcon("orange.jpg").getImage()));
		
		/*************** Declaration bouton valider ********************/
		private JButton validerChoix=new JButton("Valider Choix") ;
		
		
		/**** Le gridBagConstraints va définir la position et la taille des éléments ****/
		GridBagConstraints gc = new GridBagConstraints();
		GridBagConstraints gc2 = new GridBagConstraints();
		GridBagConstraints gc3 = new GridBagConstraints();
	
		public FenetreGird (){
			
			this.setVisible(true);
			
			//this.setPreferredSize(new Dimension(1000,600));
			
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
            gc.insets = new Insets(0, 0, 0, 0);
            PanelPerso.add(choix,gc);
            gc.insets = new Insets(3, 3, 3, 3);
			
            
            
			/********************Placement bouton de validation *****************************/
            
            gc.gridx = 8;
            gc.gridy = 1;
            //gc.gridwidth = GridBagConstraints.REMAINDER;
            gc.anchor = GridBagConstraints.CENTER;
            gc.fill = GridBagConstraints.HORIZONTAL;
            PanelPerso.add(validerChoix,gc);
            
            //gc.ipady = gc.anchor = GridBagConstraints.CENTER; 
            
            /********************Placement bouton proposition************************/
			
            gc.gridx = 8;
            gc.gridy = 0;
            gc.anchor = GridBagConstraints.PAGE_END;
            gc.fill = GridBagConstraints.HORIZONTAL;
            
            //gc2.fill = GridBagConstraints.HORIZONTAL;
            //proposition.setPreferredSize(new Dimension(100, 50));
            //proposition.setMinimumSize(new Dimension(70, 10));
            PanelPerso.add(proposition,gc);
			
		    this.setResizable(false);
		    CreationBouton();
		    
		    
		    
		    PlacementChat ();
		    PaletteAdversaire ();
		   
		    
		    
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
			for(int x=2; x<8; x++){
				for(int y=0; y<4;y++)
						  try{		
							  
							  buttonIcon3 = ImageIO.read(new File("j"+i+".png"));
							  boutonAux =new JButton(new ImageIcon(buttonIcon3));
							  //boutonAux.setSize(5,5);
							  boutonAux.setBorder(BorderFactory.createEmptyBorder());
							  boutonAux.setContentAreaFilled(false);
							  boutonAux.setPressedIcon(new ImageIcon("j"+i+"_"+i+".png"));
							  boutonAux.setSelectedIcon(new ImageIcon("Click.png"));
							//  boutonAux.setSize(30, 30);
							 // boutonAux.addActionListener(this);
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
			}
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
			for(int x=0; x<6; x++){
				for(int y=1; y<5;y++)
						  try{		
							  buttonIcon3 = ImageIO.read(new File("mini/j"+i+".png"));
							  boutonAux =new JButton(new ImageIcon(buttonIcon3));
							  boutonAux.setBorder(BorderFactory.createEmptyBorder());
							  boutonAux.setContentAreaFilled(false);
							  //boutonAux.setSelectedIcon(new ImageIcon("Click.png"));
							 // boutonAux.addActionListener(this);
							  boutonPersonnageAdv.add(boutonAux);
							  gc3.anchor = GridBagConstraints.BASELINE;
							
							  gc3.gridx = x;
                              gc3.gridy = y;
							  
							
							  JeuxAdversaire.add(boutonPersonnageAdv.get(i),gc3);
							  System.out.println("Bouton Personnage"+i+"crÃ©e à la position x="+x+" y="+y);
							  i++;
						  } catch (Exception ex) {System.out.println("Probleme dans la crÃ©ation des boutons");}
							gc.gridheight =2;
							gc.anchor = GridBagConstraints.BASELINE;
				            gc.fill = GridBagConstraints.VERTICAL;
						  gc.gridx = 8;
						  gc.gridy = 2;
		            
		            PanelPerso.add(JeuxAdversaire, gc);
				}
			}
		}
		
		public void PlacementChat (){
			
			
			gc.insets = new Insets(10, 10, 3, 10);
			
			/********Zone d'affichage*********/
			//texteChat.setForeground(new Color(0,0,0,65));
			texteChat.setBounds(50, 30, 90, 40);
			texteChat.setOpaque(false);
			//texteChat.setForeground(Color.WHITE);
			scroll.setOpaque(false);
			//scroll.setForeground(Color.WHITE);
			
            scroll.setPreferredSize(new Dimension(200, 100));
            scroll.setMinimumSize(new Dimension(200, 100));
            
            gc.gridx = 0;
            gc.gridy = 2;
            //gc.gridwidth = 2;
            //gc.gridheight = 2;
            //gc.weighty = 50;
            gc.anchor = GridBagConstraints.BASELINE;
            gc.fill = GridBagConstraints.VERTICAL;
            PanelPerso.add(scroll,gc);	
            
			/******** Zone de saisie **********/
			//saisieChat.setBorder(null);
		    saisieChat.setPreferredSize(new Dimension(200, 15));
			saisieChat.setMinimumSize(new Dimension(200, 15));
			saisieChat.setOpaque(false);
			saisieChat.setForeground(Color.WHITE);
			saisieChat.setBounds(0, 0, 0, 0);
			gc2.gridx = 0;
            gc2.gridy = 0;
            gc2.gridwidth =2;
            //gc2.anchor = GridBagConstraints.LINE_START;
            gc2.gridwidth = GridBagConstraints.REMAINDER;
            gc2.anchor = GridBagConstraints.PAGE_START;
            //gc.fill = GridBagConstraints.NONE;
            
            miniPanel.add(saisieChat,gc2);
            
            
            
            
            gc2.gridx = 0;
            gc2.gridy = 1;
            gc2.anchor = GridBagConstraints.PAGE_START;
            gc2.gridheight = GridBagConstraints.REMAINDER;
            gc2.weightx = 1.;
            gc2.fill = GridBagConstraints.HORIZONTAL;
            //validerChat.setPreferredSize(new Dimension(100, 50));
            //validerChat.setMinimumSize(new Dimension(70, 10));
            miniPanel.add(validerChat,gc2);
            
      
            gc.gridx = 0;
            gc.gridy = 3;
            //gc.anchor = GridBagConstraints.PAGE_START;
            //gc.fill = GridBagConstraints.HORIZONTAL;
			
			PanelPerso.add(miniPanel, gc);
          
				
		}
		
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
		
		public JButton getBoutonEnvoyer()
		  {
			  return validerChat;
		  }
		  public String texteChat()
		  {
			  return saisieChat.getText();
		  }
		  public void addChat(String texte)
		  {
			  texteChat.append("\n"+texte);
			  this.validate();
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

		  public void majLabel(String message)
		  {
		 	 texteChat.setText(message);
		 	  this.validate();
		  }
		
	}
			
		