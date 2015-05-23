
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import javax.swing.JTextField;

public class Fenetre extends JFrame  {
  private Joueur joueur;
  private JPanel accueil= new JPanel();
  protected JPanel adversaire = new JPanel();
  private JPanel PartieEnCours = new JPanel();
  private JPanel title = new JPanel();
  private JPanel saisie = new JPanel();
  private JPanel bouton = new JPanel();
  Marqee policePanel=new Marqee("Bienvenu sur qui-est ce jeu cr�� par Msaddek Mohamed - Naima Mehdi - Schmitz Katia - Duhammel Anthony    ");
  private JTextField SaisiePseudo = new JTextField();
  private JLabel Pseudo = new JLabel("PSEUDO");
  private JTextField SaisiePassword = new JTextField();
  private JLabel Password = new JLabel("MOT DE PASSE");
  //ImageIcon icon = new ImageIcon("PseudoQuiEsce.png");
  //JLabel Pseudo = new JLabel(icon);
   ImageIcon main = new ImageIcon("qui2.png");
   JLabel titre = new JLabel(main);
   //JPanel cont = new JPanel();
   JButton connexion ;
   JButton inscription;
   BufferedImage buttonIcon1;
   BufferedImage buttonIcon2;
   JLabel[] label;
   ArrayList<JButton> advButton=new ArrayList<JButton>();

   /*ajout*/
   private Box boiteListeAttente = Box.createVerticalBox();
   private Box boitePrincipale=Box.createHorizontalBox();
   private Box boiteGauche=Box.createVerticalBox();
   private JButton validerChat=new JButton("envoyer") ;
   private JButton proposition=new JButton("faire proposition") ;

   private JTextField saisieChat = new JTextField();
   private JTextArea texteChat = new JTextArea("");
   private JLabel imageChoisie=new JLabel();
   private ArrayList<JButton> boutonPersonnage = new ArrayList<JButton>();


   private Box boiteScore = Box.createVerticalBox();
   private JTextArea texteScore = new JTextArea("joueur gagne perdu");

   private JButton revenirListe=new JButton("revenir liste") ;





  public Fenetre(Joueur j) {
	joueur=j;
    this.setTitle("Qui-est-ce ?");
    this.setSize(1100, 700);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    /**********ACCUEIL************/
    try{
    	   buttonIcon1 = ImageIO.read(new File("boutonConnexion.png"));
    	   buttonIcon2 = ImageIO.read(new File("boutonInscription.png"));
    	   }
    	   catch(IOException e){}
    connexion =new JButton(new ImageIcon(buttonIcon1));
    inscription = new JButton(new ImageIcon(buttonIcon2));
    inscription.setBorder(BorderFactory.createEmptyBorder());
    inscription.setContentAreaFilled(false);
    connexion.setBorder(BorderFactory.createEmptyBorder());
    connexion.setContentAreaFilled(false);
    connexion.addActionListener(joueur);
    inscription.addActionListener(joueur);
    accueil.setLayout(new BorderLayout());
    Font police = new Font("Arial", Font.BOLD, 22);
    Pseudo.setFont(police);
    Password.setFont(police);
    saisie.setBackground(Color.red);
    Pseudo.setForeground(Color.blue);
    Pseudo.setFont(police);
    SaisiePseudo.setFont(police);
    inscription.setSize(100, 50);
   // connexion.setPreferredSize(new Dimension(150, 30));
   // inscription.setPreferredSize(new Dimension(150, 30));
    policePanel.setMaximumSize( policePanel.getPreferredSize() );

    SaisiePseudo.setPreferredSize(new Dimension(150, 30));
    SaisiePseudo.setForeground(Color.GRAY);
    SaisiePassword.setFont(police);
    SaisiePassword.setPreferredSize(new Dimension(150, 30));
    SaisiePassword.setForeground(Color.GRAY);
    Box b1 = Box.createVerticalBox();
    title.setBackground(Color.orange);
    Password.setForeground(Color.blue);
    title.add(titre);
    title.setPreferredSize(new Dimension(0, 100));
    b1.add(title);
    Box b2 = Box.createVerticalBox();
    Box b3 = Box.createHorizontalBox();

    policePanel.setBackground(Color.red);

    saisie.add(Pseudo);
    saisie.add(SaisiePseudo);
    saisie.add(Password);
    saisie.add(SaisiePassword);
    bouton.add(connexion);
    //bouton.add(Box.createRigidArea(new Dimension(0,0)));
    bouton.add(inscription);
    b2.add(saisie);
    b3.add(bouton);
    b1.add(policePanel);
    b1.add(b2);
    b1.add(b3);
    accueil.add(b1);
    accueil.setBackground(Color.orange);
    b2.setBackground(Color.orange);
    bouton.setBackground(Color.orange);
    /**********ACCUEIL*************/







    this.getContentPane().add(accueil);
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
  public void selectAdversaire(String[] adv)
  {
	   JButton boutonAux;
	   boolean res=false;
	  System.out.println("Entrée dans selectAdversaire");
	  for(int i=0; i<adv.length-1; i++)
	  {
			res=verifListeAttente(adv[i+1]);
			if(res==false)
			{
		  boutonAux = new JButton(adv[i+1]);
		  boutonAux.setSize(150,30);
		  boutonAux.addActionListener(joueur);
		  advButton.add(boutonAux);
		  boiteListeAttente.add(advButton.get(i));
			}
	  }
	  System.out.println("add");
	  adversaire.add(boiteListeAttente);
	  this.setContentPane(adversaire);
	  this.invalidate();
	  this.validate();
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

  public void lancerPartie(){

	  JButton boutonAux ;
	   Box b4 = Box.createVerticalBox();
	  Box b5 = Box.createHorizontalBox();
	  Box b6 = Box.createHorizontalBox();
	  Box b7 = Box.createHorizontalBox();
	  Box b8 = Box.createHorizontalBox();

	  int j=0;

	  for(int i=0; i<24; i++){
		  try{

			  boutonAux = new JButton();

			  boutonAux.setIcon(new ImageIcon("j"+i+".jpg"));

			  boutonAux.setSize(30,30);

			  boutonAux.addActionListener(joueur);
			  boutonPersonnage.add(boutonAux);
			  if(i<6){
				  b5.add(boutonPersonnage.get(i));
			  }
			  if(i>5 && i<12){
				  b6.add(boutonPersonnage.get(i));
			  }
			  if(i>11 && i<18){
				  b7.add(boutonPersonnage.get(i));
			  }
			  if(i>17 && i<24){
				  b8.add(boutonPersonnage.get(i));
			  }
			  System.out.println("Bouton Personnage"+i+"crée");
		  } catch (Exception ex) {System.out.println("Probleme dans la création des boutons");}
	  }
	  b4.add(b5);
	  b4.add(b6);
	  b4.add(b7);
	  b4.add(b8);
	  boitePrincipale.add(b4);


	  boiteGauche.add(texteChat);
	  boiteGauche.add(saisieChat);
	  boiteGauche.add(validerChat);
	  boiteGauche.add(proposition);

	  boiteGauche.add(imageChoisie);
	  boitePrincipale.add(boiteGauche);

	  validerChat.addActionListener(joueur);
	  proposition.addActionListener(joueur);

	  PartieEnCours.add(boitePrincipale);
	  this.setContentPane(PartieEnCours);
	  this.invalidate();
	  this.validate();

  }


  public void majListeAttente(String pseudo,String x)
  {
	  int demande=Integer.parseInt(x);
	  System.out.println("debut methode majListeAttente "+pseudo+" "+demande);

	  JButton boutonAux=null;
	 if(demande==0)
	 {
		 for(int i=0;i< advButton.size();i++)
		 {
			 if( advButton.get(i).getText().equals(pseudo))
			 {
				 boutonAux=advButton.get(i);
				 boiteListeAttente.remove(boutonAux);
				 advButton.remove(boutonAux);
			 }
		 }
	 }

	 else
	 {
		 boolean res=verifListeAttente(pseudo);

		 if(res==false)
		 {
			 boutonAux = new JButton(pseudo);
			 boutonAux.setSize(150,30);
			 boutonAux.addActionListener(joueur);
			 advButton.add(boutonAux);
			 boiteListeAttente.add(boutonAux);
		 }
	 }
	 /* this.invalidate();*/
	  this.validate();

	  System.out.println("fin methode majListeAttente "+pseudo+" "+x);

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


 public boolean verifListeAttente(String pseudo)
 {
	 boolean res=false;
	 for(int j=0;j<advButton.size();j++)
	 {
		  System.out.println(advButton.get(j).getText()+"?"+pseudo);

		 if(advButton.get(j).getText().equals(pseudo))
			 res=true;
	 }
	  System.out.println(pseudo+res);

	 return res;
 }

 public ArrayList getListImage()
 {
	 return boutonPersonnage;
 }

 public void setImageChoisie(int i)
 {
	   System.out.println("debut setimagechoisie");

	 imageChoisie.setIcon(new ImageIcon("j"+i+".jpg"));
	  this.validate();

 }

 public void griser(int i)
 {	  System.out.println("debut griser");

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

 public void afficherScore(String[] liste)
 {
	 for(int i=1;i<liste.length;i++)
	 {
		 texteScore.append("\n"+liste[i]);
	 }
	 boiteScore.add(texteScore);
	 boiteScore.add(revenirListe);
	 revenirListe.addActionListener(joueur);

	 this.setContentPane(boiteScore);
	 this.invalidate();
	  this.validate();


 }

public JButton getRevenir()
{
	return revenirListe;
}


}











