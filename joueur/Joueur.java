import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.*;

public class Joueur implements ActionListener  {

	/***************ajout****************/
	int nbAuto;
	/***************************/
	Socket socket;
	private int etat;
	PrintWriter out = null;
	BufferedReader in = null;
	Ecoute ThreadEcoute;
	String[] info;
	String message;
	Fenetre fenetre;
	int x;
	public Joueur(){
		try{
		etat=0;
		nbAuto=0;
		socket = new Socket("10.11.64.2",2019);
		out = new PrintWriter(socket.getOutputStream());
	    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    System.out.println("Connecter");
	    ThreadEcoute = new Ecoute(socket, this);
	    ThreadEcoute.start();
	    System.out.println("Thread Ecoute lancer");
		fenetre = new Fenetre(this);
		}catch(IOException e){
		 System.err.println("Aucun serveur � l'�coute du port "+socket.getLocalPort());
		}


}


	public void traiterMessage (String message)
	{
		info = message.split(":");

		if (info[0].equals("1") && info[1].equals("0")){
			/*fenetre.changerLabelAccueil("Pseudo deja utilis�");*/
			System.out.println("Pseudo deja utilis�");
		}

		if (info[0].equals("1") && info[1].equals("1")){
			envoiMessageServeur("3:");
			}


		if (info[0].equals("2") && info[1].equals("0")){
		/*	fenetre.changerLabelAccueil("Probleme au niveau du mot de passe ou du pseudo");*/
			System.out.println("Probleme au niveau du mot de passe ou du pseudo");

		}

		if (info[0].equals("2") && info[1].equals("1")){
			envoiMessageServeur("3:");
		}

		if (info[0].equals("3")){
			fenetre.selectAdversaire(info);
		}

		if (info[0].equals("17")){

			fenetre.majListeAttente(info[1],info[2]);
		}



		if (info[0].equals("5")){
			boolean rep = fenetre.AfficherBoiteDialogue(info[1]);
			if (rep == true){
				envoiMessageServeur("5:1");
				receptionImage();
				fenetre.lancerPartie();
			}

			else if (rep == false){
			envoiMessageServeur("5:0");
				out.flush();
			}
		}
		if(info[0].equals("33")){
			fenetre.ajoutQuestion(info);
		}

		if (info[0].equals("6")&& info[1].equals("1")){
		 	receptionImage();
			fenetre.lancerPartie();
			if(fenetre.auto)
				envoiMessageServeur("33");


			}

		if (info[0].equals("6")&& info[1].equals("0"))
		{
			System.out.println("");

		}

		if (info[0].equals("11"))
		{
			fenetre.addChat(info[1]);
		}


		if (info[0].equals("10"))
		{
			fenetre.majLabel(info[1]);

		}
		if (info[0].equals("20"))
		{
			fenetre.majLabel("pas de bol!");
			etat=1;
		}

		if(info[0].equals("14"))
		{
			fenetre.afficherScore(info);
		}

		if(info[0].equals("32")){
			fenetre.griserCases(info);
		}


			//Ne plus attndre adversaire


		/*if (info[0].equals("9")){
			System.out.println("Reçu 9:");
			fenetre.majCaseAdversaire(info[1]);
			}

		if (info[0].equals("10") && info[1].equals(0)){
			System.out.println("Reçu 10:0");
			fenetre.jeuAdversaire();
		}


		if (info[0].equals("10") && info[1].equals(1))
			fenetre.resultat(1);//Signifie que  l'utilisateur � gagner la partie

		if (info[0].equals("10") && info[1].equals(0))
			fenetre.resultat(0);//Signifie que l'utilisateur � perdu

		if (info[0].equals("11"))
			fenetre.majTchat(info[1]);

		if(info[0].equals("12")) {
			fenetre.score(info[1]);
		}*/

	}

	public void lireImage(int im)
    {
       // System.out.println("numero "+im);
        byte r;
        int x;
        Integer z;
        try{FileOutputStream fichier= new FileOutputStream("j"+im+".jpg");

        try{x=in.read();
        while(x!=64000)
        {
            z=x;
            r=z.byteValue();
            fichier.write(r);
            x=in.read();

        }
        fichier.close();}
        catch(IOException e){System.out.println("probleme reception");}
        }
        catch(FileNotFoundException e){System.out.println("erreur ouverture fichier de reception");}
    }

	public void receptionImage(){
		for(int i = 0 ; i<24; i++){
			envoiMessageServeur("13:"+i);
			lireImage(i);
			//System.out.println("Image "+i+" reçu");
		}
	}


	public void actionPerformed(ActionEvent arg0)
	{
		  String[] elt;
		  String e;
		  if(arg0.getSource() == fenetre.getBConnexion())
		  {
			  envoiMessageServeur("2:"+fenetre.getPseudo()+":"+fenetre.getMdp());
			  fenetre.pseudo = fenetre.getPseudo();


		  }
		  else if(arg0.getSource() == fenetre.getBInscription())
		  {
			  envoiMessageServeur("1:"+fenetre.getPseudo()+":"+fenetre.getMdp());
		  }

		  else if(fenetre.advButton.contains(arg0.getSource()))
		  {
			  System.out.println("je suis ici");
			  for(int i=0; i<fenetre.advButton.size(); i++){
				  if(fenetre.advButton.get(i).equals(arg0.getSource()))
				{
				  System.out.println("je suis la");


				  elt = fenetre.advButton.get(i).getText().split(" ");

				  for(int j=0; j<elt.length; j++ )
				  {
				  System.out.println(elt[i]);}
				  if(elt[0].equals("ORDINATEUR"))
				  {
				        new JoueurAutomatique(fenetre.pseudo);
				        try {
				            Thread.sleep(2000);    // s'execute pendant 30 secondes
				        } catch (InterruptedException ex) {}
				        fenetre.auto = true;
					  envoiMessageServeur("4:"+elt[0]+fenetre.pseudo);
				  }
				  else
				  envoiMessageServeur("4:"+elt[0]);
				}
			  }

		  }
		  else if(fenetre.questButton.contains(arg0.getSource()))
		  {
			  for(int i=0; i<fenetre.questButton.size(); i++){
				  if(fenetre.questButton.get(i).equals(arg0.getSource()))
				{
				  System.out.println("je suis la");
				  e = fenetre.questButton.get(i).getText();
				  envoiMessageServeur("31:"+ i/*+fenetre.getPseudo()*/);
				}
			  }
		  }
		  else if(fenetre.getBoutonEnvoyer()==arg0.getSource())
		  {
			  fenetre.addChat(fenetre.texteChat());
			  envoiMessageServeur("11:"+fenetre.texteChat());

		  }
		  else if(fenetre.getListImage().contains(arg0.getSource()))
		  {
			  for(int i=0;i<fenetre.getListImage().size();i++)
			  {
				  if(fenetre.getListImage().get(i)==arg0.getSource())
				  {
					  if(etat==0)
					  {
						  envoiMessageServeur("7:"+i);
						  fenetre.setImageChoisie(i);
						  etat=1;
					  }
					  else if(etat==1)
					  {
						  fenetre.griser(i);
					  }
					  else if(etat==2)
					  {
						  envoiMessageServeur("10:"+i);

					  }
					  else{}
				  }
			  }
		  }
		  else if(fenetre.getProposition()==arg0.getSource())
		  {
			  etat=2;
		  }

		  else if(fenetre.getRevenir()==arg0.getSource())
		  {
			  envoiMessageServeur("3:");
		  }







	 }

	public void envoiMessageServeur(String message)
	{
		  System.out.println("envoi "+message);
		  out.println(message);
		  out.flush();
	}





	public static void main(String[] args) {
		Joueur joueur = new Joueur();

	}

}
