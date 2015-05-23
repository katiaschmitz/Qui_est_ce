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


public class JoueurAutomatique{
	Socket socket;
	Thread threadJoueurAuto;
	PrintWriter out = null;
	BufferedReader in = null;
	public JoueurAutomatique(String pseudo){
		try{
		socket = new Socket("10.11.79.13",2019);
		out = new PrintWriter(socket.getOutputStream());
	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        System.out.println("Connecter");
		envoiMessageJoueur("1:ORDINATEUR"+pseudo+":ORDINATEUR");
		threadJoueurAuto = new Thread(this);
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
			recu = th.getInput().readLine();// entrée de la socket
			System.out.println("reception "+recu);
		 
			/*rajouter cas ou on recoit null attention pas de split*/
			infos=recu.split(":");// decoupage de la chaine de caractère
			demande=infos[0];// numero de la demande

			/*compilation*/
			for(int i=0;i<infos.length;i++)
			System.out.println(infos[i]);


			/*traitement des différentes demandes*/
			if(demande.equals("5"))//demande inscription
			{
				traiterDemandePartie();
			}
			else if(demande.equals("11"))//il reçoit une question 
			{
				traiterDemandePartie();
			}

			/*remise à zero des informations*/
			recu="";
			for(int i=0;i<infos.length;i++)
				infos[i]="";

		  }catch (IOException e){ }
	
		}
	}

	public void envoiMessageServeur(String message)
	{
		  System.out.println("envoi "+message);
		  out.println(message);
		  out.flush();
	}

	public void traiterDemandePartie(){
		envoiMessageJoueur("5:1");
		/* on ne lance pas de partie graphique*/
		/*on choisit un personnage au hasard*/
		Random rand = new Random();
		envoiMessageJoueur("7:"+rand.nextInt(24));
	}
}
