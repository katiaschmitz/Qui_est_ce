/**
 *
 *  Cette classe fait partie du logiciel "Qui est ce",adaptation du jeu de société
 *  en réseau.</p> <p>
 *
 *  Une "Partie" représente une partie entre deux utilisateurs.
 *
 *  Elle est caractérisée  par le pseudo des joueurs, leur choix d'image, les images de la partie et le mode de la partie
 * 
 *
 */

import java.util.*;
public class Partie
{
	private String joueur1;
	private String joueur2;
	private int choixJoueur1;
	private int choixJoueur2;
	private int images[];
	private String mode;

/*******************************************************/
/***          CONSTRUCTEUR, GET, SET                 ***/
/*******************************************************/

	/**
	 *  Initialise une Partie avec les pseudos des deux joueurs
	 *
	 * @param pseudo1 pseudo de l'utilisateur qui fait la demande de partie
	 * @param pseudo2 pseudo de l'autre utilisateur
	 * @return Partie
	 */
	public Partie(String pseudo1,String pseudo2)
	{	
		//System.out.println("debut méthodePartie constructeur: "+pseudo1+" "+pseudo2);
		images=new int[24];
		joueur1=pseudo1;
		joueur2=pseudo2;
		mode = "";
		for(int i=0; i<24;++i)
		images[i]= i+1;
	}

	/**
	 *  Initialise le mode de la partie
	 *
	 * @param mode mode choisi par le joueur
	 */
	public void setMode(String mode)
	{
		this.mode = mode;
		System.out.println("Mode : "+mode);
	}

	/**
	 *  Renvoie le mode de la partie
	 * 
	 * @return nom du mode 
	 */
	public String getMode()
	{
		return mode;	
	}

	/**
	 *  Initialise le choix de l'image du joueur
	 *
	 * @param pseudo pseudo du joueur
	 * @param choix numero de l'image choisie (indice dans le tableau de jeu)
	 */
	public void setChoixJoueur(String pseudo,int choix)
	{
		//System.out.println("debut méthodePartie setChoixJoueur: "+pseudo+" "+ choix);
		if(pseudo.equals(joueur1))
			choixJoueur1=choix;
		else
			choixJoueur2=choix;
	}

	/**
	 *  Renvoie le pseudo de l'adversaire du joueur donné en argument
	 *
	 * @param pseudo pseudo du joueur
	 * @return le pseudo de l adversaire
	 */
	public String getAdversaire(String pseudo)
	{
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
		return joueur1;
	}

	/**
	 *  Renvoie le joueur2
	 *
	 * @return pseudo du joeuur2
	 */
	public String getJoueur2()
	{
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
		//System.out.println("debut méthodePartie getImage d indice: "+i);
		return images[i];
	}

	/**
	 *  Renvoie le choix de l'adversaire du joueur donné en argument
	 *
	 * @return indice de l'image choisie par l'adversaire
	 */
	public int getChoixAdversaire(String pseudo)
	{
		if(pseudo.equals(joueur1))
			 return choixJoueur2;
		else
			return choixJoueur1;
	}

	/**
	 *  Renvoie le tableau des images
	 *
	 * @return tableau des images
	 */
	public int[] getTabImages()
	{
		return images;
	}

/*******************************************************/
/***          FONCTIONS MANIPULATION PARTIES         ***/
/*******************************************************/

	/**
	 *  Cette fonction est appelée par un Serveur.
	 *  Elle vérifie le choix du joueur
	 *
	 * @param pseudo pseudo du joueur
	 * @param choix numero de l image proposée
	 * @return true si la reponse est correcte,false sinon
	 */
	public boolean verifierChoix(String pseudo,int choix)
	{
		//System.out.println("debut méthodePartie verifierChoix: "+pseudo +" "+ choix);
		boolean reponse=false;
		if(pseudo.equals(joueur1))
			reponse = (choix == choixJoueur2);
		else
			reponse = (choix==choixJoueur1);
		return reponse;
	}

}










