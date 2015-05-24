/**
 *
 *  Cette classe fait partie du logiciel "Qui est ce",adaptation du jeu de société 
 *  en réseau.</p> <p>
 *
 *  Une "Partie" représente une partie entre deux utilisateurs.
 *
 *  Elle est caractérisée  par le pseudo des joueurs, leur choix d image, le nombre de case qu'ilq ont baisséeune socket,socket principale du jeu qui attend la connexion des utilisateurs; 
 *  sa base de données contenant les différents membres du logiciel et leur mot de passe,les scores; les différents ThreadJOueur
 *  correspondant aux joueurs, les différentes parties 
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.PrintWriter;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.ListIterator;



public class Partie
{
	private String joueur1;
	private String joueur2;
	private int choixJoueur1;/*entre 1 et 20*/
	private int choixJoueur2;
	private int nbCaseBaissee1;
	private int nbCaseBaissee2;
	private int images[];/* supplementaire*/
	
/*******************************************************/
/***          CONSTRUCTEUR, GET, SET                 ***/
/*******************************************************/

	/**
	 *  Initialise une Partie avec les pseudos des deux joueurs et met le nombre de cases baissées à 0
	 *
	 * @param pseudo1 pseudo de l'utilisateur qui fait la demande de partie
	 * @param pseudo2 pseudo de l'autre utilisateur
	 * @return Partie
	 */
	public Partie(String pseudo1,String pseudo2)
	{
		//COMPILATION 
		System.out.println("debut méthodePartie constructeur: "+pseudo1+" "+pseudo2);
		images=new int[24];
		joueur1=pseudo1;
		joueur2=pseudo2;
		nbCaseBaissee1=0;
		nbCaseBaissee2=0;

	}
	
	/**
	 *  Renvoie le nombre de cases baissées par le joueur dont le pseudo est donné en argument
	 *
	 * @param pseudo pseudo du joueur
	 * @return nombre de cases baissées du joueur
	 */	
	public int getNbCaseBaissee(String pseudo)
	{
		//COMPILATION 
		System.out.println("debut méthodePartie getNbCaseBaissee: "+pseudo);
		if(pseudo.equals(joueur1))
			return nbCaseBaissee1;
		else
			return nbCaseBaissee2;
	}

	/**
	 *  Initialise le choix de l'image du joueur 
	 *
	 * @param pseudo pseudo du joueur
	 * @param choix numero de l'image choisie (indice dans le tableau de jeu)
	 */	
	public void setChoixJoueur(String pseudo,int choix)
	{
		//COMPILATION 
		System.out.println("debut méthodePartie setChoixJoueur: "+pseudo+" "+ choix);
		if(pseudo.equals(joueur1))
			choixJoueur1=choix;
		else
			choixJoueur2=choix;
	}

	/**
	 *  Renvoie le pseudo de l'adversaire du joueur donner en argument 
	 *
	 * @param pseudo pseudo du joueur
	 * @return le pseudo de l adversaire
	 */	
	public String getAdversaire(String pseudo)
	{
		//COMPILATION 
		System.out.println("debut méthodePartie getAdversaire: "+pseudo);
		if(pseudo.equals(joueur1))
			return joueur2;
		else
			return joueur1;
	}

	/**
	 *  Renvoie le joueur1 
	 *
	 * @return pseudo du joeuur1
	 */	
	public String getJoueur1()
	{
		//COMPILATION 
		System.out.println("debut méthodePartie getJoueur1: ");
		return joueur1;
	}

	/**
	 *  Renvoie le joueur2 
	 *
	 * @return pseudo du joeuur2
	 */	
	public String getJoueur2()
	{
		//COMPILATION 
		System.out.println("debut méthodePartie getJoueur2: ");
		return joueur2;
	}

	/**
	 *  Renvoie le numéro de l'image d'indice i 
	 *
	 * @param i indice de l image compris entre 0 et 24 exclus
	 * @return numero de l image d indice i 
	 */	
	public int getImage(int i)
	{
		//COMPILATION 
		System.out.println("debut méthodePartie getImage d indice: "+i);
		return images[i];
	}

/*******************************************************/
/***          FONCTIONS MANIPULATION PARTIES         ***/
/*******************************************************/

	/**
	 *  Cette fonction est appelée par un Serveur.
	 *  vérifie le choix du joueur
	 *
	 * @param pseudo pseudo du joueur
	 * @param choix numero de l image proposée
	 * @return true si la reponse est correcte,false sinon
	 */	
	public boolean verifierChoix(String pseudo,int choix)
	{
		//COMPILATION 
		System.out.println("debut méthodePartie verifierChoix: "+pseudo +" "+ choix);
		boolean reponse=false;
		if(pseudo.equals(joueur1))
			reponse = (choix == choixJoueur2);
		else
			reponse=(choix==choixJoueur1);
		return reponse;	
	}
	
	/**
	 *  Cette fonction est appelée par un Serveur.
	 *  augmente le nombre de case baissée du joueur
	 *
	 * @param pseudo pseudo du joueur
	 * @param nb nombre a ajouter
	 */	
	public void ajouterCase(String pseudo,int nb)
	{
		//COMPILATION 
		System.out.println("debut méthodePartie ajouterCase: "+pseudo+" "+ nb);
		if(pseudo.equals(joueur1))
			nbCaseBaissee1= nbCaseBaissee1+nb;
		else
			nbCaseBaissee2 = nbCaseBaissee2+nb;
	}

	/**
	 *  Cette fonction est appelée par un Serveur.
	 *  Choisit au hasard 24 numéro d'image compris entre 0 et 19 
	 */	
	public void initialiseImagePartie()
	{
		//COMPILATION 
		System.out.println("debut méthodePartie initialiseImagePartie: ");
		Random rand;
		ArrayList<Integer> tab = new ArrayList<Integer>();
		for (int i=0; i<24;++i)
		tab.add(i+1);	
	
		for(int i=0; i<24;++i)
		{
			rand = new Random();
			images[i]=tab.get(rand.nextInt(24-i));
			tab.remove((Integer)images[i]);
		}
	}		
}










