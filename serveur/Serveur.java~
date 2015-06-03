/**
 *
 *  Cette classe fait partie du logiciel "Qui est ce",adaptation du jeu de société
 *  en réseau.</p> <p>
 *
 *  Un "Serveur" représente le serveur du jeu permettant de gérer les différentes parties et utilisateurs
 *
 *  Il est caractérisé  par une socket,socket principale du jeu qui attend la connexion des utilisateurs;
 *  sa base de données contenant les différents membres du logiciel et leur mot de passe,les scores; les différents ThreadServeur
 *  correspondant aux joueurs, les différentes parties
 *
 */


import java.net.InetAddress;
import java.net.ServerSocket;

import java.util.*;





public class Serveur
{
	private ServerSocket socket_serveur  ;
	private DataBase bdd;
	private ArrayList<ThreadJoueur> joueurs;
	private ArrayList<Partie> parties;

/*******************************************************/
/***          CONSTRUCTEUR, GET, SET                 ***/
/*******************************************************/

	/**
	 *  Initialise un Serveur en créant les différentes listes vides et en attendant la connexion des utilisateurs pour crééer les 		 *  joueurs en leur attribuant une socket.
	 *
	 * @return Serveur
	 */
	public Serveur()
	{
		try{
			System.out.println("entree dans la boucle1 ");

		socket_serveur = new ServerSocket(2028);
		System.out.println("entree dans la boucle2 ");

		bdd = new DataBase();
		joueurs= new ArrayList<ThreadJoueur>();
		parties= new ArrayList<Partie>();

		bdd.deconncterTous();

			/*while (true) // attente en boucle de connexion (bloquant sur ss.accept)
			{
				//COMPILATION
				System.out.println("constructeur serveur : attente client sur "+InetAddress.getLocalHost());
			        joueurs.add(new ThreadJoueur(socket_serveur.accept(),this));
				//COMPILATION
				System.out.println("constructeur serveur : client connecté ");
			}*/
		}catch (Exception e) {		System.out.println("erreur constructeur serveur ");
 }
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle renvoie le ThreadJoueur correspondant au pseudo données en argument.
	 *
	 *  @param pseudo pseudo du joueur
	 *  @return le threadJoueur correspondant
	 */
	public ThreadJoueur getThreadJoueur(String pseudo)
	{
		/******element de compilation******/
		System.out.println("début methodeServeur getThreadJoueur :"+pseudo );
		/***************/

		ThreadJoueur res=null;
		boolean stop=false;
		for(int i=0;i<joueurs.size()&& !stop ;i++)
		{
			if(joueurs.get(i).getPseudo().equals(pseudo))
			{
				res = joueurs.get(i);
				stop=true;
				/**********element de compilation*************/
				System.out.println("fin methodeServeur getThreadJoueur :"+pseudo+"thread "+ i);
				/********************************/
			}
		}
		return res;
	}



/*******************************************************/
/***             FONCTIONS PRINCIPALES               ***/
/*******************************************************/

	/**
	 * Fonction main lançant un serveur
	 */
	public static void main(String args[])
	{
		//COMPILATION
		System.out.println("debut méthodeServeur main ");
		System.out.println("***************** ");
		Serveur x = new Serveur();
		System.out.println(x.bdd.afficherScore("bouh"));
		//COMPILATION
		System.out.println("fin méthodeServeur main ");
	}

/***************************************************************/
/***        FONCTIONS POUR LES THREADJOUEUR            ********/
/*   elles permettent de gérer les informations des     ********/
/*   différentes parties,de la base de données et autres    ****/
/***************************************************************/

/************************************************/
/**********   gestion bdd     *******************/
/************************************************/
	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle demande a la base de données si les informations d'inscription sont correctes
	 *
	 *  @param pseudo pseudo voulu pour l'inscription
	 *  @param motPasse mot de passe voulu pour l'inscription
	 *  @return true si c'est correcte, false sinon
	 */
	public boolean inscription(String pseudo,String motPasse)
	{
		//COMPILATION
		System.out.println("debut méthodeServeur inscription: "+pseudo+" "+motPasse);
		return bdd.inscription(pseudo,motPasse);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur..
	 *  Elle demande a la base de données si les informations de connexion sont correctes
	 *
	 *  @param pseudo pseudo pour la connexion
	 *  @param motPasse mot de passe pour la connexion
	 *  @return true si c'est correcte, false sinon
	 */
	public boolean connexion(String pseudo,String motPasse)
	{
		//COMPILATION
		System.out.println("debut méthodeServeur connexion: "+pseudo+" "+motPasse);
		return bdd.connexion(pseudo,motPasse);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle demande a la base de données les scores pour le joueur dont le pseudo est en argument
	 *
	 *  @param joueur pseudo de l utilsateur faisant la demande
	 *  @return une chaine de caractère contenant les scores
	 */
	public String recupererScores(String joueur)
	{
		//COMPILATION
		System.out.println("debut méthodeServeur recupererScores: "+joueur);
		return bdd.afficherScore(joueur);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle demande a la base de données la liste des joueurs en attente pour l utilisateur dont le pseudo
	 *  est donné en argument
	 *  @param joueur pseudo à ne pas prendre en compte (celui qui veut la liste)
	 *  @return une chaine de caractère avec tous les noms
	 */
	public String listeAttente(String joueur)
	{
		//COMPILATION
		System.out.println("debut méthodeServeurlisteAttente: "+joueur);
		 return bdd.listeJoueur(joueur);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle demande a la base de données de supprimer le joueur des utilisateur connectés
	 *
	 *  @param joueur pseudo à ne pas prendre en compte (celui qui veut la liste)
	 */
	public void deconnexion(String joueur)
	{
		bdd.deconnexion(joueur);
		ThreadJoueur s = getThreadJoueur( joueur);
		joueurs.remove(s);
	}

	public void majScore(String gagnant,String perdant)
	{
		bdd.majScore(gagnant,true);
		bdd.majScore(perdant,false);
	}

/************************************************/
/**********   gestion parties   *****************/
/************************************************/

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle creee une partie avec les deux joueurs donnés en argument.
	 *  Elle est creee lors d'une proposition de partie
	 *
	 *  @param joueur1 pseudo du joueur ayant proposer la partie
	 *  @param joueur2 pseudo de l'autre joueur
	 *  @return la partie creee
	 */
	public Partie creerPartie(String joueur1,String joueur2)
	{
		//COMPILATION
		System.out.println("debut méthodeServeur creerPartie: "+joueur1+" "+joueur2);
		Partie p = new Partie(joueur1, joueur2);
		parties.add(p);
		return p;
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle permet de verifier si le joueur donner en argument n'a pas de partie.
	 *  C'est a dire s'il n'a pas fait de demande et s'il n'en a pas en attente
	 *
	 *  @param joueur pseudo joueur quel'on veut verifier
	 *
	 *  @return true s'il a une partie,false sinon
	 */
	public boolean existePartie(String joueur)
	{
		//COMPILATION
		System.out.println("debut méthodeServeur existePartie: "+joueur);
		System.out.println("parties.size() "+parties.size());


		for(int i=0;i<parties.size();i++)
		{
			Partie p = parties.get(i);
			if( joueur.equals(p.getJoueur1()) || joueur.equals(p.getJoueur2()) )
			return true;
		}
		return false;
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle cherche un l'adversaire d'un joueur dont le pseudo et la partie sont specifié en argument
	 *  Il recupere le pseudo de l'adversaire dans la parties puis il recherche le ThreadJoueur correspondant
	 *
	 *  @param joueur pseudo du joueur dont on veut trouver l'adversaire
	 *  @param p partie du joueur
	 *  @return le ThreadJoueur de l'adversaire
	 */
	public ThreadJoueur getPartieAdversaire(String joueur,Partie p)
	{
		//COMPILATION
		System.out.println("debut méthodeServeur getPartieAdversaire: "+joueur);
		String adv = p.getAdversaire(joueur);//recupere le pseudo de l'adversaire
		//COMPILATION
		System.out.println("méthodeServeur getPartieAdversaire: "+joueur+" adversaire "+adv);
		return getThreadJoueur(adv);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle supprime la partie donnee en argument
	 *
	 *  @param p partie a supprimer
	 */
	public void supprimerPartie(Partie p)
	{
		parties.remove(p);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle met a jours la partie donnée en argument en ajoutant le choix du joueur spécifiée
	 *
	 *  @param joueur pseudo du joueur
	 *  @param p partie en question
	 *  @param choix numero de l'image( indice de l'image dans la grille de jeu)
	 */
	public void ajouterChoixPersoPartie(int choix,String joueur,Partie p)
	{
		//COMPILATION
		System.out.println("debut méthodeServeur ajoutChoixPersoPartie: image"+choix+" "+joueur);
		p.setChoixJoueur(joueur,choix);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle met a jours la partie donnée en argument en invrémentant le nombre de cases baissées du joueur spécifiée
	 *
	 *  @param joueur pseudo du joueur
	 *  @param p partie en question
	 *  @param nb nombre de case baissée supplementaire
	 *  @return nombre de case baissée au totale du joueur spécifié
	 */
	/*public int augmenterCaseBaissee(Partie p,String joueur,int nb)
	{
		//COMPILATION
		System.out.println("debut méthodeServeur augmenterCAaseBaissee: "+joueur+" "+ nb);
		p.ajouterCase(joueur,nb);
		return p.getNbCaseBaissee(joueur);
	}*/

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle vérifie si l'image proposé est la bonne
	 *
	 *  @param joueur pseudo du joueur
	 *  @param p partie en question
	 *  @param choix numero de l'image( indice de l'image dans la grille de jeu)
	 *  @return true si c'est bon,false sinon
	 */
	public boolean verifierReponse(Partie p,String joueur,int choix)
	{
		//COMPILATION
		System.out.println("debut méthodeServeur verifierReponse: "+joueur+ "image "+choix);
		return p.verifierChoix(joueur,choix);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle crée un tableau d'entier correspondant aux images dans la partie donnée en argument
	 *
	 *  @param p partie en question
	 */
	public void chargerImagePartie(Partie p)
	{
		//COMPILATION
		System.out.println("debut méthodeServeur chargerIMagePartie: ");
		String mode = p.getMode();
		int nb = bdd.getNbImages(mode);
		
		p.initialiseImagePartie(nb);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle return le numéro de l'image de la partie p et d'indice num
	 *
	 *  @param p partie en question
	 *  @param num indice de l image en question
	 *  @return numero de l image voulue
	 */
	public int recupererImagePartie(int num, Partie p)
	{
		//COMPILATION
		System.out.println("debut méthodeServeur recupererImagePartie: "+ num);
		return p.getImage(num);
	}

/************************************************/
/**********   fonctions autres  *****************/
/************************************************/

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle permet de mettre a jour la liste d attente de tous les joueurs connectés
	 *  cest a dire de supprimer ou ajouter quelqu un a la liste d attente
	 *
	 *  @param joueur pseudo du joueur que l on doit retirer ou ajouter a la liste
	 *  @param sens 1 s il faut ajouter,0 sinon
	 */
	public void raffraichirListeAttente(String joueur, int sens)
	{
		if (!estOrdinateur(joueur)){//COMPILATION
			System.out.println("debut méthodeServeur raffraichirListeAttente: "+ joueur + sens);
			ThreadJoueur res = null;
			for(int i=0;i<joueurs.size() ;i++)
			{
				if(!(joueurs.get(i).getPseudo().equals(joueur) ))
					{
						res = joueurs.get(i);
						res.raffraichirListeAttente(joueur,sens);
					}

			}
		}
	}

	public boolean estOrdinateur(String pseudo)
	{
		String[] tmp = pseudo.split("-");
		return tmp[0].equals("ORDINATEUR");
	}



	/************************************************/
	/********** fonctions parties automatique  ******/
	/************************************************/

		/**
		 *  Cette fonction est appelée par un ThreadJoueur.
		 *  Elle demande à la base de données le nombre de questions disponibles
		 */
		public int recupereNbQuestion(String mode)
		{
			return bdd.getNombreQuestion(mode);
		}

		/**
		 *  Cette fonction est appelée par un ThreadJoueur.
		 *  Elle permet de répondre  à une question posée.
		 *  cest a dire elle répond à la question posée et renvoie la liste des personnages à baisser
		 *  avec le nombre de case baissées
		 *
		 *  @param p partie concernée
		 *  @param pseudo pseudo du joueur posant la question
		 *  @param question numero de la question posée
		 */
		public String recupererReponseQuestion(Partie p ,String pseudo, int question)
		{
			int[] tmp = p.getTabImages();// récupération des images de la partie

			int nb = 0;

			String img = "";
			String m = p.getMode();
			System.out.println("/**************verification question***************/");
			System.out.println("num question"+ question);
			int id_image = p.getChoixAdversaire(pseudo);// récuperation de l'image de l'adversaire
			System.out.println("image du joueur(indice)"+id_image);
			int[] num_champs = bdd.getChampReponse(question,m);// recupération du champs réponse correspondant à la question
			System.out.println("numero du champs"+num_champs[0]+" valeur du champs pour la question"+num_champs[1]);
			int val_img = bdd.getBonneReponse(tmp[id_image],num_champs[0],m);// récupération de la réponse
			System.out.println("numero image adversaire"+tmp[id_image]);
			System.out.println("valeur image adversaire"+ val_img);
			boolean val_ref;

			if(num_champs[1]==val_img)
			{
				val_ref = true;
			}
			else
			{
				val_ref = false;
			}
			/*verifier avec les images de la partie les reponse au question*/
			for( int i =0; i < tmp.length; i++)
			{
				if( bdd.verifierImage( tmp[i], num_champs[0],num_champs[1],val_ref,m))
				{
					System.out.println("image ok "+ i);
					img = img + ":" + i ;
				}

			}

			return img ;//le nb de case baissées et les indices des cases baissées
		}

		public String listeQuestion(String mode)
		{
			return bdd.listeQuestion(mode);
		}


/***********************ajout mode *******************************/

	public void choisirModePartie(String mode, Partie p)
	{
		p.setMode(mode);
	}


	public String getModePartie(Partie p)
	{
		return p.getMode();
	}

	public String listeFavoris(String pseudo)
	{
		return bdd.listeFavoris(pseudo);
	}




}











