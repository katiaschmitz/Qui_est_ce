import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Ecoute extends Thread {
	Socket socket;
	String message;
	Joueur joueur;
	BufferedReader in = null;
	public Ecoute(Socket socket,Joueur joueur){
		this.socket = socket;
		this.joueur = joueur;
	}
		public void run(){
			try{
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}
			catch(IOException e){}
			while(true){
			try{
				message = in.readLine();
				System.out.println("recu "+message);

				joueur.traiterMessage(message);
			}
			catch(IOException e){}
			}
		}
}
