import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;



public class JoueurAutomatique implements Runnable{
	Socket socket;
	String pseudo;
	int nb_question;
	int nb_case_baissee;
	ArrayList<Integer> questionsPosees;
	ArrayList<Integer> imageBaissee;
	Thread threadJoueurAuto;
	PrintWriter out = null;
	BufferedReader in = null;

	public JoueurAutomatique(String pseudo)
	{

		try{
			socket = new Socket("10.11.66.8",2020);
			out = new PrintWriter(socket.getOutputStream());
	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        System.out.println("Connecter");
	        nb_case_baissee =1;// nb case baissee par tour
			this.pseudo = "ORDINATEUR"+pseudo;
			questionsPosees = new ArrayList<Integer>();
			imageBaissee = new ArrayList<Integer>();
			threadJoueurAuto = new Thread(this);
			envoiMessageServeur("30:"+"ORDINATEUR-"+pseudo);// inscription fictive
			threadJoueurAuto.start();
	        System.out.println("Thread Ecoute lancer");
		}catch(IOException e){
		 System.err.println("Aucun serveur � l'�coute du port "+socket.getLocalPort());
		}


	}


	public void run(){


		while(true){
		System.out.println(" debut méthodeAnalyseurServeur principale ");

		String[] infos;
		String demande;
		String recu="";

		try
		{
			/* message pour compilation*/
			System.out.println("attente message client");
			/*recuperation du message*/
			recu = getInput().readLine();// entrée de la socket
			System.out.println("reception "+recu);

			/*rajouter cas ou on recoit null attention pas de split*/
			infos=recu.split(":");// decoupage de la chaine de caractère
			demande=infos[0];// numero de la demande

			/*compilation*/
			for(int i=0;i<infos.length;i++)
			System.out.println(infos[i]);


			/*traitement des différentes demandes*/
			if(demande.equals("5"))
			{
				traiterDemandePartie();
			}
			else if(demande.equals("30"))
			{
				nb_question = Integer.parseInt(infos[1]);
			}
			else if(demande.equals("9"))
			{
				if(nb_case_baissee == 0 || nb_question == questionsPosees.size())
				{
					faireProposition();
					nb_case_baissee = 1;
				}
				else
				{
					choisirQuestion();
				}
			}
			else if(demande.equals("32"))
			{
				nb_case_baissee =0;
				for(int h=1;h<infos.length;++h)
				{
					if(!imageBaissee.contains(Integer.parseInt(infos[h])))
					{
						imageBaissee.add(Integer.parseInt(infos[h]));
						nb_case_baissee = nb_case_baissee +1;
					}
				}
				afficherImageBaissee();
				System.out.println("NB CASE BAISSEE "+ nb_case_baissee );

			}



			/*remise à zero des informations*/
			recu="";
			for(int i=0;i<infos.length;i++)
				infos[i]="";

		  }catch (IOException e){ }

		}
	}

	public void afficherImageBaissee(){

		String tmp = "";
		for(int i=0;i<imageBaissee.size();++i)
		{
			tmp = tmp + " " + imageBaissee.get(i);
		}

		System.out.println(" case baissee "+tmp );

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

	public void envoiMessageServeur(String message)
	{
		  System.out.println("envoi "+message);
		  out.println(message);
		  out.flush();
	}

	public void traiterDemandePartie(){
		envoiMessageServeur("5:1");
		/* on ne lance pas de partie graphique*/
		/*on choisit un personnage au hasard*/
		Random rand = new Random();
		envoiMessageServeur("7:"+rand.nextInt(24));
	}

	public void choisirQuestion()
	{
		Random rand = new Random();
		int nb = rand.nextInt(nb_question);
		while (questionsPosees.contains(nb))
			nb = rand.nextInt(nb_question);

		questionsPosees.add(nb);
		envoiMessageServeur("11:question posee "+nb);
		envoiMessageServeur("31:"+nb );
		System.out.println("ORDINATEUR A JOUER UN TOUR");

	}

	public void faireProposition()
	{
		Random rand = new Random();
		int nb = rand.nextInt(24);
		while(imageBaissee.contains(nb))
			nb = rand.nextInt(24);

		imageBaissee.add(nb);
		envoiMessageServeur("11:perso propose "+nb);
		envoiMessageServeur("10:"+nb);
	}


	}


