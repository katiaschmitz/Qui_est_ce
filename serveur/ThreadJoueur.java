/**
 *
 *  Cette classe fait partie du logiciel "Qui est ce",adaptation du jeu de société
 *  en réseau.</p> <p>
 *
 *  Un "ThreadJoueur" représente un utilisateur (joueur) utilisant le logiciel
 *  et permet de gérer les différentes intéractions (requetes) faites par ce derniers.
 *  auprès du serveur.
 *
 *  Il est caractérisé principalement par un thread le représentant. Puis il décrit
 *  les informations principales concernant le joueur : pseudo, numéro, partie auquelle il participe.
 *  Il comprend également les éléments qui permettent l'intéraction entre l'utilisateur et
 *  le serveur : la socket son entrée et sa sortie, le serveur, l'analyseur(pour analyser les requetes).
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;

import java.io.*;



public class ThreadJoueur implements Runnable
{
	/* thread correspondant*/
	private Thread thread_client;
	/* informations relatives au joueur*/
	private String pseudo="";
	private Partie partie;
	/* acteurs permettant les intéractions joueur-serveur*/
	private Socket socket_joueur;
	private BufferedReader in;//entrée socket
	private PrintWriter out;//sortie socket
	private Serveur serveur;
	private AnalyseurServeur analyseur;


/*******************************************************/
/***          CONSTRUCTEUR, GET, SET                 ***/
/*******************************************************/

	/**
	 *  Initialise un ThreadJoueur avec la socket et le serveur donnée en argument.
	 *  Cette méthode peut être utilisée lors de la création du ThreadJoueur (quand
	 *  un utilisateur se connecte au logiciel).
	 *
	 * @param  sock la socket associée
	 * @param  serv le serveur associée
	 * @return ThreadJoueur
	 */
	public ThreadJoueur(Socket sock,Serveur serv)
	{
			//COMPILATION
			System.out.println("debut méthodeJoueur constructeur: ");
			try
			{
			/*initialisation acteurs intéractions*/
			this.socket_joueur = sock;
			this.serveur = serv;
			out = new PrintWriter(socket_joueur.getOutputStream());
      			in = new BufferedReader(new InputStreamReader(socket_joueur.getInputStream()));
			analyseur=new AnalyseurServeur(this);
			}
			catch (IOException e){ }

			/*creation et lancement du thread*/
			thread_client=new Thread(this);
			thread_client.start();
	}

	/**
	 *  Renvoie le pseudo du joueur
	 *
	 * @return  pseudo du joueur
	 */
	public String getPseudo()
	{
		//COMPILATION
		System.out.println("debut méthodeThreadJoueur getPseudo: ");
		return pseudo;
	}

	/**
	 *  Renvoie l entree de la socket
	 *
	 * @return  entree socket
	 */
	public BufferedReader getInput()
	{
		//COMPILATION
		System.out.println("debut méthodeThreadJoueur getInput: ");
		return in;
	}

	public PrintWriter getOutput()
	{	
		return out;
	}

/*******************************************************/
/***             FONCTIONS PRINCIPALES               ***/
/*******************************************************/

	/**
	 *  Fonction effectuée par le thread. Elle boucle sur l'analyseur pour
	 *  répondre aux demandes du joueur.
	 *
	 */
	public void run()
	{
		/**élément de compilation*/
		System.out.println(" debut méthode threadJoueur : run()");
		/************/
		while(analyseur.getActif())
		analyseur.principale();
	}

	/**
	 *  Cette fonction permet d'envoyer une chaine de caractère au joueur à travers la socket
	 *
	 *  @param message texte à envoyer au joueur
	 */
	public void envoiMessageJoueur(String message)
	{
		//COMPILATION
		System.out.println("debut méthodeThreadJoueur envoiMessageJoueur: "+ message);
		out.println(message);
		out.flush();
	}

/********************************************************/
/***        FONCTIONS POUR L ANALYSEUR_SERVEUR       ****/
/* elles permettent de répondre aux différentes demandes*/
/********************************************************/


	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle demande au serveur si les informations d'inscription sont correctes
	 *  et envoie un message au Joueur à travers la socket.
	 *
	 *  Il envoie une demande de type 1: .
	 *  Si l'inscription est valide il envoie 1 et initialise l'attribut pseudo, sinon 0.
	 *
	 *  @param pseudo pseudo voulu pour l'inscription
	 *  @param motPasse mot de passe voulu pour l'inscription
	 */
	public void inscription(String pseudo,String motPasse)
	{

		/*****élément de compilation***/
		System.out.println("debut methode threadjoueur : inscription "+pseudo+" "+motPasse);
		/*********************/
		if( !pseudo.equals("") && !motPasse.equals("")){
			boolean rep_serveur = serveur.inscription(pseudo,motPasse);
			if(rep_serveur == true)
			{
				envoiMessageJoueur("1:1");
				this.pseudo=pseudo;
				serveur.raffraichirListeAttente(this.pseudo,1);
			}
			else
				envoiMessageJoueur("1:0");
		}
		else 
			envoiMessageJoueur("1:2");
	}

	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle demande au serveur si les informations de connexion sont correctes
	 *  et envoie un message au Joueur à travers la socket.
	 *
	 *  Il envoie une demande de type 2: .
	 *  Si la connexion est valide il envoie 1 et initialise l'attribut pseudo, sinon 0.
	 *
	 *  @param pseudo pseudo pour connexion
	 *  @param motPasse mot de passe pour connexion
	 */
	public void connexion(String pseudo,String motPasse)
	{
		boolean rep_serveur;
		/****element de compilation***/
		System.out.println("debut methode threadjoueur : connexion"+pseudo+" "+motPasse);
		/********************/
		if( !pseudo.equals("") && !motPasse.equals("")){		
			rep_serveur = serveur.connexion(pseudo,motPasse);
			if(rep_serveur == true)
			{
				envoiMessageJoueur("2:1");
				this.pseudo=pseudo;
				serveur.raffraichirListeAttente(this.pseudo,1);
			}	
			else
				envoiMessageJoueur("2:0");
		}
		else 
			envoiMessageJoueur("2:2");
	}

	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle demande la liste des joueurs en attente au serveur et l'envoie au joueur
	 *  à travers la socket.
	 *
	 *  Il envoie une demande de type 3: avec la liste d'attente.
	 */
	public void listeAttente()
	{
		//COMPILATION
		System.out.println("debut méthodeThreadjoueur listeAttente: ");
		envoiMessageJoueur("3:"+"ORDINATEUR"+ serveur.listeAttente(this.pseudo));
	}

	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle vérifie que le joueur n'a pas déja fait de demandes et que son adversaire
	 *  n'a pas de demandes en cours auprès du serveur. Si c'est bon elle créée la partie (partie potentielle
	 *  en attente de validation ) et met à jours l'attribut partie du ThreadJoueur puis envoie une demande.
	 *
	 *  Si c'est bon il envoie une demande de type 5: avec le pseudo du joueur (celui qui lui propose la partie).
	 *  Sinon il envoie des messages d'erreur
	 *
	 *  @param pseudo_adv pseudo de l'adversaire
	 */
	public void choixAdversaire(String pseudo_adv)
	{
		//COMPILATION
		System.out.println("debut méthodeThreadjoueur choixAdversaire: "+ pseudo_adv);

			ThreadJoueur adv = serveur.getThreadJoueur(pseudo_adv);//récupération du ThreadJOueur de l'adversaire
			if (serveur.existePartie(this.pseudo)==false) //vérification si le joueur n'a pas fait de proposition
			{
				if(serveur.existePartie(adv.pseudo)==false)//vérifie si l'adversaire n'a pas de demande en cours
				{
					this.partie = serveur.creerPartie(this.pseudo,adv.pseudo);
					adv.partie = this.partie;
					serveur.raffraichirListeAttente(this.pseudo,0);	System.out.println("bouh");
					serveur.raffraichirListeAttente(adv.pseudo,0);	System.out.println("bouh 2");
					adv.envoiMessageJoueur("5:"+this.pseudo);
				}
				else
					System.out.println("methodeThreadjoueur choixAdvesaire : adversaire a deja une demande");
			}
			else
				System.out.println("methodeThreadjoueur choixAdversaire : demande deja envoyée");

	}

	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle vérifie que la réponse du joueur.
	 *  S'il accepte on initialise la partie du joueur (avec celle créer dans la méthode choixAdversaire)
	 *  puis on envoie un message à l'adversaire pour qu'il lance la partie. Ensuite on envoie les images pour la partie aux deux 	 	 *  joueurs. Sinon on supprime la partie potentielle créée précedement
	 *
	 *  Si c'est bon il envoie une demande de type 6:.
	 *
	 *  @param reponse reponse du joueur , 1 s'il accepte,0 sinon.
	 */
	public void demandeAccept(String reponse)
	{
		//COMPILATION
		System.out.println("debut méthodeThreadjoueur demandeAccepte: "+reponse);
			ThreadJoueur adv= serveur.getPartieAdversaire(this.pseudo,this.partie);//récupération du Threadjoueur de l'adversaire
			if(reponse.equals("1"))
			{
				//adv.envoiMessageJoueur("35:simpson:disney:s");
				adv.envoiMessageJoueur("6:1:simpson:disney:s");// l adversaire doit lancer la partie
				/*enregistrer numéro image*/

			}
			else
			{
				adv.envoiMessageJoueur("6:0");// le joueur peut de nouveau choisir quelquun
				serveur.supprimerPartie(adv.partie);//suppression de la partie potentielle
				serveur.raffraichirListeAttente(this.pseudo,1);
				serveur.raffraichirListeAttente(adv.pseudo,1);
				adv.partie = null;
				this.partie = null;
			}
	}
	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle permet de mettre à jours la partie avec le choix du personnage à deviner en faisant appel au serveur.
	 *
	 *  @param num_perso numéro du personnage choisit par le joueur(indice dans le tableau de jeu)
	 */
	public void choisirPerso(String num_perso)
	{
		//COMPILATION
		System.out.println("debut méthodeThreadjoueur choisirPerso: "+ num_perso);
		int num_image = Integer.parseInt(num_perso);
		serveur.ajouterChoixPersoPartie(num_image,this.pseudo,this.partie);
	}

	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle permet de mettre à jours le nombre de cases baissées du joueur puis elle envoi ce nombre à l'adversaire
	 *
	 *  elle envoie une demande de type 9: avec le nouveau nombre de case baissée à son adversaire.
	 *
	 *  @param nb nombre de cases baissees par le joueur.
	 */
	public void nbCaseBaissee(String nb)
	{
		//COMPILATION
		System.out.println("debut méthodeThreadjoueur nbCaseBaissee: "+nb);
		ThreadJoueur adv = serveur.getPartieAdversaire(this.pseudo,this.partie);//récupération Threadjoueur de l'adversaire
		//int nouv_nb = serveur.augmenterCaseBaissee(this.partie,this.pseudo,Integer.parseInt(nb));
		String nouv_nb = "";
		

		adv.envoiMessageJoueur(nb);
	}

	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle vérifie si la réponse proposee est juste auprès du serveur.
	 *  Si oui on envoi un message "gagne" au joueur et "perdu" à son adversaire, sinon on passe la main à l'autre joueur
	 *
	 *  Si la réponse est juste, elle envoie une demande de type 10: avec 0 pour perdu  1 pour gagné.
	 *  Sinon elle envoie une demande de type 13: pour passer la main
	 *
	 *  @param num_perso numéro du personnage choisit par le joueur(indice dans le tableau de jeu)
	 */

	public void proposeReponse(String num_perso)
	{
		//COMPILATION
		System.out.println("debut méthodeThreadjoueur proposeReponse: "+ num_perso);
		int num_image = Integer.parseInt(num_perso);
		boolean rep_serveur;
		rep_serveur = serveur.verifierReponse(this.partie,this.pseudo,num_image);
		ThreadJoueur adv = serveur.getPartieAdversaire(this.pseudo,this.partie);//récuperation threadjoueur adversaire

		if(rep_serveur == true)
		{
			envoiMessageJoueur("10:Gagnée");//gagnee
			adv.envoiMessageJoueur("10:Perdue");//perdu
			serveur.majScore(this.pseudo,adv.pseudo);
			afficherScores();
			adv.afficherScores();
			serveur.supprimerPartie(this.partie);
			serveur.raffraichirListeAttente(this.pseudo, 1);
			serveur.raffraichirListeAttente(adv.pseudo,1);


		}
		else
		{
				envoiMessageJoueur("20");
				adv.envoiMessageJoueur("9:" );

		}
	}

	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle permet d'envoyer un message à l'adversaire.
	 *
	 * elle envoie une demande de type 11: avec le texte à envoyer.
	 *
	 *  @param texte texte à envoyer
	 */
	public void envoiTexte(String texte)
	{
		//COMPILATION
		System.out.println("debut méthodeThreadjoueur envoiTexte: "+texte);
		ThreadJoueur adv = serveur.getPartieAdversaire(this.pseudo,this.partie);//récuperation threadjoueur adversaire
		adv.envoiMessageJoueur("11:"+texte);
	}

	/**
	 *  Elle permet d'envoyer une image au joueur à travers la socket
	 *
	 *  @param image indice de l image a envoyer
	 */
	public void envoiImage(String image)
	{
		//COMPILATION
		System.out.println("debut méthodeThreadjoueur envoiImage d indice: "+ image);
		ThreadJoueur adv = serveur.getPartieAdversaire(this.pseudo,this.partie);//récuperation threadjoueur adversaire
		int num_image = Integer.parseInt(image);
		int i = serveur.recupererImagePartie(num_image,this.partie)  ;// recuperer le numero de l image a envoyer
		
		Byte[] b = new Byte[256];
		byte[] z = new byte[256];
		int[] c = new int[256];
		int x;
		String m = serveur.getModePartie(this.partie);
		System.out.println("Image : "+m+i);
		FileInputStream fichiers = null;
		try{fichiers=new FileInputStream(m+i+".png");}
			catch(FileNotFoundException e){System.out.println("erreur ouverture fichier");}

		try{x=fichiers.read(z);
		while(x!=-1)
		{
			for(int j=0;j<x;j++)
			{
					System.out.println(c[j]);
				b[j]=z[j];
				c[j]=b[j].intValue();
				out.write(c[j]);
				//adv.getOutput().write(c[j]);
			}
			x=fichiers.read(z);
		}
		x=64000;
		out.write(x);
		//adv.getOutput().write(x);
		out.flush();
		//adv.getOutput().flush();
		
		System.out.println(x);
		}
		catch(IOException e){System.out.println("erreur envoi image");}
	}

	/**
	 *  Elle recupere les scores puis les renvoi a l utilisateur dont le pseudo est donnée en argument
	 *
	 *  @param pseudo pseudo de l utilisateur ayant fait la demande
	 */
	public void afficherScores()
	{
		//COMPILATION
		System.out.println("debut méthodeThreadjoueur afficherScores: "+this.pseudo);
		this.envoiMessageJoueur("14"+ serveur.recupererScores(this.pseudo));
	}

	/**
	 *  Elle traite le cas ou le joueur se deconnecte cest a dire elle supprime la partie et previent l autre joueur
	 *  si une partie etait en cours
	 *
	 */
	public void deconnexion()
	{
		//COMPILATION
		System.out.println("debut méthodeThreadjoueur deconnexion: "+this.pseudo);
		serveur.deconnexion(this.pseudo);
		serveur.raffraichirListeAttente(this.pseudo,0);
		if(serveur.existePartie(this.pseudo))
		{
			ThreadJoueur adv = serveur.getPartieAdversaire(this.pseudo,this.partie);//récuperation threadjoueur adversaire
			serveur.supprimerPartie(this.partie);
			adv.envoiMessageJoueur("16:");// partie interrompue joueur deconnecté
			serveur.raffraichirListeAttente(adv.pseudo, 1);
			serveur.majScore(adv.pseudo,this.pseudo);
		}
		//fermeture socket
		try{
		socket_joueur.close();}catch(IOException e){}
	}

	/**
	 *  Elle dit au joueur de  raffraichir sa liste d attente en ajoutant ou retirant le joueur dont le pseudo est donnée en 		 *  argument
	 *
	 *  @param pseudo pseudo du joueur a ajouter ou retirer
	 *  @param sens 0 s'il faut le retirer,1 sinon
	 */
	public void raffraichirListeAttente(String pseudo, int sens)
	{
		//COMPILATION
		System.out.println("debut méthodeThreadjoueurraffraichirListeAttente: "+pseudo+sens);
		this.envoiMessageJoueur("17:"+ pseudo +":"+ sens);
	}






/***********************fonction joueurAuto*****************************/
	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle permet de creer un joueur automatique sans pour autant le connecté ou l'inscrire
	 *  elle met son pseudo a jour
	 *
	 *  @param pseudo pseudo du joueur automatique ( ex ORDINATEURpseudo )
	 */
	public void creationJoueurAuto(String pseudo)
	{
		/*****élément de compilation***/
		System.out.println("debut methode threadjoueur : creationJoueurAuto "+pseudo);
		/*********************/
		this.pseudo = pseudo;
	}

	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle permet au joueur de poser une question automatique.
	 *  Elle récupère les personnages correspondant à la question (ceux à éliminer)et l'envoi au joueur
	 *  Elle recupère le nombre de personnages baissés et l'envoi al adversaire
	 *
	 *  @param num_question numéro de la question posée (commence a 0)
	 */
	public void envoiQuestionAuto(String num_question)
	{

		ThreadJoueur adv= serveur.getPartieAdversaire(this.pseudo,this.partie);

		/*n	 une chaine de caracteres contenant le nombre de personnage à baiser suivi de ces personnages( indice de la 			table de jeu*/
		String tmp = serveur.recupererReponseQuestion(this.partie,this.pseudo,Integer.parseInt(num_question));

		

		this.envoiMessageJoueur("32"+ tmp);// envoi des images a baissée au joueur
		adv.envoiMessageJoueur("9"+ tmp);// envoi nb case baissée à l adver
	}

	/**
	 *  Cette fonction est appelée par l'AnalyseurServeur.
	 *  Elle permet d'envoyer la liste des questions au joueur
	 */
	public void envoiQuestion()
	{
		String m = serveur.getModePartie(this.partie);
		envoiMessageJoueur("33"+serveur.listeQuestion(m));
	}

/**************************ajout mode*********************************/

	public void choisirMode(String mode)
	{
		ThreadJoueur adv= serveur.getPartieAdversaire(this.pseudo,this.partie);
		serveur.choisirModePartie(mode,this.partie);
						serveur.chargerImagePartie(this.partie);
		adv.envoiMessageJoueur("30:"+serveur.recupereNbQuestion(mode));
		adv.envoiMessageJoueur("18:"+mode);
	}
	
	public void envoyerImageMode(String image){
                	//COMPILATION
		//System.out.println("debut méthodeThreadjoueur envoiImage d indice: "+ image);



		Byte[] b = new Byte[256];
		byte[] z = new byte[256];
		int[] c = new int[256];
		int x;
		FileInputStream fichiers = null;
		try{fichiers=new FileInputStream(image+"0.png");}
			catch(FileNotFoundException e){System.out.println("erreur ouverture fichier");}

		try{x=fichiers.read(z);
		while(x!=-1)
		{
			for(int j=0;j<x;j++)
			{
				b[j]=z[j];
				c[j]=b[j].intValue();
				out.write(c[j]);

			}
			x=fichiers.read(z);
		}
		x=64000;
		out.write(x);

		out.flush();
                } 
		catch(IOException e){System.out.println("erreur envoi image");}
	


}

		public void quitterPartie()
		{
			ThreadJoueur adv= serveur.getPartieAdversaire(this.pseudo,this.partie);

			serveur.majScore(adv.pseudo,this.pseudo);
			serveur.supprimerPartie(this.partie);
			adv.envoiMessageJoueur("16:");
			serveur.raffraichirListeAttente(this.pseudo, 1);
			serveur.raffraichirListeAttente(adv.pseudo,1);

		}

}











