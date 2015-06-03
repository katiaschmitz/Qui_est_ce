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
	 *  Initialise un Serveur en créant les différentes listes vides et en attendant la connexion des utilisateurs pour créer les 		 *  joueurs en leur attribuant une socket.
	 *
	 * @return Serveur
	 */
	public Serveur()
	{
		try{
			socket_serveur = new ServerSocket(2028);
			bdd = new DataBase();
			joueurs= new ArrayList<ThreadJoueur>();
			parties= new ArrayList<Partie>();
			bdd.deconncterTous();

			while (true) // attente en boucle de connexion (bloquant sur ss.accept)
			{
				System.out.println("constructeur serveur : attente client sur "+InetAddress.getLocalHost());
			        joueurs.add(new ThreadJoueur(socket_serveur.accept(),this));
				System.out.println("constructeur serveur : client connecté ");
			}
		}catch (Exception e){System.out.println("erreur constructeur serveur ");}
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
		//System.out.println("début methodeServeur getThreadJoueur :"+pseudo );
		ThreadJoueur res=null;
		boolean stop=false;
		for(int i=0;i<joueurs.size()&& !stop ;i++)
		{
			if(joueurs.get(i).getPseudo().equals(pseudo))
			{
				res = joueurs.get(i);
				stop=true;
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
		//System.out.println("debut méthodeServeur main ");
		Serveur x = new Serveur();
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
		//System.out.println("debut méthodeServeur inscription: "+pseudo+" "+motPasse);
		return bdd.inscription(pseudo,motPasse);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle demande a la base de données si les informations de connexion sont correctes
	 *
	 *  @param pseudo pseudo pour la connexion
	 *  @param motPasse mot de passe pour la connexion
	 *  @return true si c'est correcte, false sinon
	 */
	public boolean connexion(String pseudo,String motPasse)
	{
		//System.out.println("debut méthodeServeur connexion: "+pseudo+" "+motPasse);
		return bdd.connexion(pseudo,motPasse);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle demande a la base de données les scores de tous les joueurs
	 *
	 *  @param joueur pseudo de l utilsateur faisant la demande
	 *  @return une chaine de caractères contenant les scores
	 */
	public String recupererScores(String joueur)
	{
		//System.out.println("debut méthodeServeur recupererScores: "+joueur);
		return bdd.afficherScore(joueur);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle demande a la base de données les scores d'un joueur
	 *
	 *  @param joueur pseudo de l utilsateur faisant la demande
	 *  @return une chaine de caractères contenant son scores
	 */
	public String recupererScoreJoueur(String joueur)
	{
		//System.out.println("debut méthodeServeur recupererScorejoueur: "+joueur);
		return bdd.afficherScoreJoueur(joueur);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle demande a la base de données la liste des joueurs en attente pour l utilisateur dont le pseudo
	 *  est donné en argument
	 * 
	 *  @param joueur pseudo à ne pas prendre en compte (celui qui veut la liste)
	 *  @return une chaine de caractère avec tous les noms
	 */
	public String listeAttente(String joueur)
	{
		//System.out.println("debut méthodeServeurlisteAttente: "+joueur);
		return bdd.listeJoueur(joueur);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle demande a la base de données de supprimer le joueur des utilisateur connectés
	 *
	 *  @param joueur pseudo du joueur
	 */
	public void deconnexion(String joueur)
	{
		bdd.deconnexion(joueur);
		ThreadJoueur s = getThreadJoueur(joueur);
		joueurs.remove(s);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle permet de mettre à jours les scores
	 * 
	 *  @param gagnant pseudo du joueur ayant gagné
	 *  @param perdant pseudo du joueur ayant perdu
	 */
	public void majScore(String gagnant,String perdant)
	{
		bdd.majScore(gagnant,true);
		bdd.majScore(perdant,false);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle demande à la base de données le nombre de questions disponibles dans le mode
	 * 
	 * @param mode nom du mode
	 * @ return nombre de questions du mode
	 */
	public int recupereNbQuestion(String mode)
	{
		return bdd.getNombreQuestion(mode);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle permet de récupérer la liste des questions du mode
	 *
	 *  @param mode mode choisi
	 *  @param return liste de questions du mode
	 */
	public String listeQuestion(String mode)
	{
		return bdd.listeQuestion(mode);
	}
	
	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle permet de récupérer la liste des favoris du joueur
	 *
	 *  @param pseudo pseudo du joueur
	 *  @param return liste contenant les favoris du joueur
	 */
	public String listeFavoris(String pseudo)
	{
		return bdd.listeFavoris(pseudo);
	}


/************************************************/
/**********   gestion parties   *****************/
/************************************************/

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle créée une partie avec les deux joueurs donnés en argument.
	 *  Elle est créée lors d'une proposition de partie
	 *
	 *  @param joueur1 pseudo du joueur ayant proposé la partie
	 *  @param joueur2 pseudo de l'autre joueur
	 *  @return la partie créée
	 */
	public Partie creerPartie(String joueur1,String joueur2)
	{
		System.out.println("debut méthodeServeur creerPartie: "+joueur1+" "+joueur2);
		Partie p = new Partie(joueur1, joueur2);
		parties.add(p);
		return p;
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle permet de verifier si le joueur donné en argument n'a pas de partie.
	 *  C'est a dire s'il n'a pas fait de demande et s'il n'en a pas en attente
	 *
	 *  @param joueur pseudo joueur quel'on veut verifier
	 *  @return true s'il a une partie,false sinon
	 */
	public boolean existePartie(String joueur)
	{
		//System.out.println("debut méthodeServeur existePartie: "+joueur);
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
	 *  Elle cherche un l'adversaire d'un joueur dont le pseudo et la partie sont specifiés en argument
	 *  Il récupère le pseudo de l'adversaire dans la partie puis il recherche le ThreadJoueur correspondant
	 *
	 *  @param joueur pseudo du joueur dont on veut trouver l'adversaire
	 *  @param p partie du joueur
	 *  @return le ThreadJoueur de l'adversaire
	 */
	public ThreadJoueur getPartieAdversaire(String joueur,Partie p)
	{
		//System.out.println("debut méthodeServeur getPartieAdversaire: "+joueur);
		String adv = p.getAdversaire(joueur);//recupere le pseudo de l'adversaire
		return getThreadJoueur(adv);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle supprime la partie donnée en argument
	 *
	 *  @param p partie a supprimer
	 */
	public void supprimerPartie(Partie p)
	{
		parties.remove(p);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle met a jours la partie donnée en argument en ajoutant le choix du joueur spécifié
	 *
	 *  @param joueur pseudo du joueur
	 *  @param p partie en question
	 *  @param choix numero de l'image( indice de l'image dans la grille de jeu)
	 */
	public void ajouterChoixPersoPartie(int choix,String joueur,Partie p)
	{
		//System.out.println("debut méthodeServeur ajoutChoixPersoPartie: image"+choix+" "+joueur);
		p.setChoixJoueur(joueur,choix);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle vérifie si l'image proposée est la bonne
	 *
	 *  @param joueur pseudo du joueur
	 *  @param p partie en question
	 *  @param choix numero de l'image( indice de l'image dans la grille de jeu)
	 *  @return true si c'est bon,false sinon
	 */
	public boolean verifierReponse(Partie p,String joueur,int choix)
	{
		//System.out.println("debut méthodeServeur verifierReponse: "+joueur+ "image "+choix);
		return p.verifierChoix(joueur,choix);
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
		//System.out.println("debut méthodeServeur recupererImagePartie: "+ num);
		return p.getImage(num);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle permet d'initialiser le mode de la partie
	 *
	 *  @param p partie en question
	 *  @param mode mode choisi
	 */
	public void choisirModePartie(String mode, Partie p)
	{
		p.setMode(mode);
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle permet de récupérer le mode de la partie
	 *
	 *  @param p partie en question
	 *  @return mode de la partie
	 */
	public String getModePartie(Partie p)
	{
		return p.getMode();
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
		//System.out.println("debut méthodeServeur raffraichirListeAttente: "+ joueur + sens);
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

	public void raffraichirListeAttente2(ThreadJoueur joueur)
	{
		ThreadJoueur res = null;
		for(int i=0;i<joueurs.size() ;i++)
		{
			if(existePartie(joueurs.get(i).getPseudo()))
			{
				joueur.raffraichirListeAttente(joueurs.get(i).getPseudo(),0);
			}
		}
	}

	/**
	 *  Cette fonction est appelée par un ThreadJoueur.
	 *  Elle permet de répondre  à une question posée.
	 *  cest a dire elle répond à la question posée et renvoie la liste des personnages à baisser
	 *
	 *  @param p partie concernée
	 *  @param pseudo pseudo du joueur posant la question
	 *  @param question numero de la question posée
	 *  @return chaine de caractères contenant les indices des cases à baisser
	 */
	public String recupererReponseQuestion(Partie p ,String pseudo, int question)
	{
		int[] tmp = p.getTabImages();// récupération des images de la partie
		String img = "";
		String m = p.getMode();
		int id_image = p.getChoixAdversaire(pseudo);// récuperation de l'image de l'adversaire
		int[] num_champs = bdd.getChampReponse(question,m);/*recupération du champs réponse correspondant à la question 										et de sa valeur pour une bonne réponse*/
		int val_img = bdd.getBonneReponse(tmp[id_image],num_champs[0],m);/* récupération de la réponse sur l'image de 												l'adversaire */
		boolean val_ref;

		if(num_champs[1]==val_img)
			val_ref = true;
		else
			val_ref = false;
		
		/*verifier avec les images de la partie les reponse au question*/
		for( int i =0; i < tmp.length; i++)
		{
			if( bdd.verifierImage( tmp[i], num_champs[0],num_champs[1],val_ref,m))
			{
				img = img + ":" + i ;
			}
		}

		return img ;//les indices des cases baissées
	}
}











