import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/****export CLASSPATH=.:~/final/qui_est_ce/serveur/jdbc.jar
mysql -h  mysql-thetourist.alwaysdata.net thetourist_quiestce -u 107510_3 -p  root
 ****/

/**
 * Classe DataBase est un module dans le cadre du jeu du Qui_Est_Ce projet de licence informatique 3ème année
 *
 * @author     Mehdi Naima
 * @version    1.0
 * @since      May 2015
 */

public class DataBase
{
	private Connection connexion;



	/**
	 * Initialise une instance de base de donnee
	 * @param  connexion pour initialiser la connexion à la base de donnee
	 */
	public DataBase()
	{
		 System.out.println("Connecting to a selected database...");
		String driver = "com.mysql.jdbc.Driver";
		/*String url = "jdbc:mysql://marseille/BDE11216832";
    		String user = "E11216832";
    		String password = "0JYJQM054H1";*/

			String url = "jdbc:mysql://mysql-thetourist.alwaysdata.net/thetourist_quiestce";
    		String user = "107510_3";
    		String password = "root";

		try
		{
			try{Class.forName(driver);}
			catch(ClassNotFoundException e){System.out.println("probleme driver");}
			connexion = DriverManager.getConnection(url,user,password);
			System.out.println("Connected database successfully...");
			/*PreparedStatement req;
			String sql = "";
			sql = "create table qui_est_ce (pseudo varchar(20),password varchar(20),gagne int,perdu int,rang int,connecte int)";
			req=connexion.prepareStatement(sql);
			req.executeUpdate(sql);
	  	    System.out.println("table created...");*/

		}
		catch (SQLException ex)
		{
			System.out.println("probleme connexion base de donnee");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
        	}
		/*verifierExistencePersonnes();*/
		/*creerTablePersonnes();*/

	}


	/**
	 * verifie si un pseudo et un mot de passe correspondent bien a un utilisateur
	 * @param  pseudo le pseudo a verifier
	 * @param  password le mot de passe
	 * @return  vrai si la personne existe, faux sinon
	 */
	public boolean connexion(String pseudo,String password)
	{
		System.out.println("DataBase:connexion debut methode ");
		int reponse;
		boolean x=false;
		PreparedStatement req;
		try
		{
			req=connexion.prepareStatement("Select count(*) as n from qui_est_ce where pseudo=? and password=?;");
			req.setString( 1, pseudo);
			req.setString( 2, password);
			ResultSet resultat = req.executeQuery();
		    resultat.next();
			reponse=resultat.getInt("n");
			if(reponse==1)
			{
				x=true;
				try
				{
					req=connexion.prepareStatement( "update qui_est_ce set connecte=1 where pseudo=?;");
					req.setString( 1, pseudo );
					int res = req.executeUpdate();
				}
				catch(SQLException ex){}

			}
			else
				x=false;

		}
		catch(SQLException ex){}

		/*while ( resultat.next() )*/

		System.out.println("DataBase:connexion fin methode "+x);

		return x;
	}

	/**
	 * inscrit pseudo et un mot de passe dans la base de donnee
	 * @param  pseudo le pseudo a verifier
	 * @param  password le mot de passe
	 * @return  vrai si la personne a bien ete ajoutee la personne existe, et faux si le pseudo est deja utilise
	 */
	public boolean inscription(String pseudo,String password)
	{
		System.out.println("DataBase:inscription debut methode ");

		boolean x=false;
		int reponse;
		PreparedStatement req=null;
		try
		{
			req=connexion.prepareStatement( "Select count(*) as n from qui_est_ce where pseudo=?");
			req.setString( 1, pseudo );
			ResultSet resultat = req.executeQuery();
		   	resultat.next();
			reponse=resultat.getInt("n");
			System.out.println("reponse = "+reponse);

			if(reponse==1)
				x=false;
			else
			{
				PreparedStatement inscrip=null;
				try
				{
					int indice=recupererNombreJoueurs();
					inscrip=connexion.prepareStatement("INSERT INTO qui_est_ce VALUES(?,?,0,0,?,1)");
					inscrip.setString(1,pseudo);
					inscrip.setString(2,password);
					inscrip.setInt(3,indice);
					int res = inscrip.executeUpdate();
					x=true;
					arrangerClassement(pseudo);

				
				}
				catch(SQLException ex){	ex.printStackTrace();System.out.println("erreur inscription 1ere requete");}
			}

		}
		catch(SQLException ex){ex.printStackTrace();System.out.println("erreur inscription 2eme requete ");}
		System.out.println("DataBase: fin inscription");

		return x;

	}

	/**
	 * renvoie la liste d attente des personnes connectee avec leur nombre de victoires et defaites
	 * @param  pseudo la personne qui demande la liste, qu on ne renverra pas dans la liste
	 * @return  une chaine de caracteres avec des ':' entre chaque personne
	 */
	public String listeJoueur(String pseudo)
	{
		System.out.println("DataBase:liste Joueur debut methode ");
		String reponse="";
		PreparedStatement req=null;
		try
		{
			req=connexion.prepareStatement( "Select pseudo from qui_est_ce where pseudo<>? and connecte=1;");


			req.setString( 1, pseudo );
			ResultSet resultat = req.executeQuery();
			while ( resultat.next())
			{
				reponse=reponse+":"+resultat.getString("pseudo");
			}

		}
		catch(SQLException ex){}
		System.out.println("fin liste attente");
		System.out.println("DataBase:liste Joueur fin methode "+reponse);

		return reponse;
	}



	/**
	 * deconnecte une personne de la base de donnees
	 * @param  pseudo la personne a deconnecter de la base de donnees
	 */
	public void deconnexion(String pseudo)
	{
		PreparedStatement req;
		try
		{
			req=connexion.prepareStatement( "UPDATE qui_est_ce set connecte=0 where pseudo=?");
			req.setString( 1, pseudo );
			int resultat = req.executeUpdate();
		}
		catch(SQLException ex){}

	}


	/**
	 * ferme la connection a la base de donnees
	 */
	public void fermerConnection()
	{
		try{connexion.close();}
		catch(SQLException ex){}

	}

	/**
	 * met a jour la table des scores d une personne dans la base de donnees en incrementant son nombre de victoires ou son nombre 		 * de defaites selon si le deuxieme parametre est a vrai ou faux. puis arrangera le classement de cette personne dans la base 		 * de donnees. 
	 * @param  pseudo la personne dont son score doit etre mis a jour
	 * @param  gagne qui prend 
	 */
	public void majScore(String pseudo,boolean gagne)
	{
		PreparedStatement req=null;
		int nombre=-1;
		try
		{
			if(gagne)
			{
				req=connexion.prepareStatement( "select gagne from qui_est_ce where pseudo=?");
				req.setString( 1, pseudo );
				ResultSet resultat = req.executeQuery();
		    		resultat.next();
				nombre=resultat.getInt("gagne");
				nombre++;
				req=connexion.prepareStatement( "update qui_est_ce set gagne=? where pseudo=?");
				req.setInt(1 , nombre );
				req.setString( 2, pseudo );
				req.executeUpdate();


			}

			else
			{
				req=connexion.prepareStatement( "select perdu from qui_est_ce where pseudo=?");
				req.setString( 1, pseudo );
				ResultSet resultat = req.executeQuery();
		    		resultat.next();
				nombre=resultat.getInt("perdu");
				nombre++;
				req=connexion.prepareStatement( "update qui_est_ce set perdu=? where pseudo=?");
				req.setInt(1 , nombre );
				req.setString( 2, pseudo );
				req.executeUpdate();


			}
			arrangerClassement(pseudo);

		}
		catch(SQLException ex){}

	}




	/**
	 * renvoie le tableau des scores represente par les pseudos des joueurs ainsi que leur nombre de victoires et leurs nombre de 		 * defaites
	 * @param  pseudo la personne qui demande la liste, qu on ne renverra pas dans la liste
	 * @return  une chaine de caracteres avec des ':' entre chaque personnes et des ' ' entre le nom,victoires et defaites de 		 * chaque personne
	 */
	public String afficherScore(String pseudo)
	{
		PreparedStatement req=null;
		int nombre;
		String reponse = "";
		int g;
		int p;
		String ps="";

		try
		{
			
			req=connexion.prepareStatement( "select pseudo,gagne,perdu from qui_est_ce where rang <10");
			ResultSet resultat = req.executeQuery();
			while(resultat.next())
			{
				g=resultat.getInt("gagne");
				p=resultat.getInt("perdu");
				ps = resultat.getString("pseudo");
				reponse = reponse + ":" +ps +" "+g+" "+p;
			}
			req=connexion.prepareStatement( "select rang from qui_est_ce where pseudo=?");	
			req.setString( 1, pseudo );	
			resultat = req.executeQuery();
			nombre = resultat.getInt("rang");	
			if(nombre>=0)
			{
				req=connexion.prepareStatement( "select pseudo,gagne,perdu from qui_est_ce where pseudo=?");
				ResultSet resultat = req.executeQuery();
				resultat.next()
				req.setString( 1, pseudo );
				g=resultat.getInt("gagne");
				p=resultat.getInt("perdu");
				ps = resultat.getString("pseudo");
				reponse = reponse + ":" +ps +" "+g+" "+p;	
			}

	
		}
		catch(SQLException ex){System.out.println("erreur afficherScore");}


		return reponse;
	}


	/**
	 * arrange le classement du joueur donne en argument, la methode cherche le nouveau classement du joueur, et decale le 
	 * classement des autres joueurs 
	 * @param  pseudo la personne qui demande la liste, qu on ne renverra pas dans la liste
	 */
	public void arrangerClassement(String pseudo)
	{
		int i=-1;
		int dif=-1;
		PreparedStatement req=null;
		int indice=-1;
		boolean x=false;
		try
		{
			req=connexion.prepareStatement( "select rang,(gagne-perdu) as dif from qui_est_ce where pseudo=?");
			req.setString( 1, pseudo );
			ResultSet resultat = req.executeQuery();
	    		resultat.next();
	    		dif=resultat.getInt("dif");
	    		indice=resultat.getInt("rang");
	    		System.out.println("difference = "+dif);
	    		System.out.println("indice = "+indice);

		}
		catch(SQLException ex){System.out.println("erreur arrangerClassement");}
		decalerHaut(indice,pseudo);
		int taille=recupererNombreJoueurs()-1;
		int[] tab=new int[taille];
		remplirTableau(tab,pseudo);
		for(i=0;i<tab.length;i++)
	    		System.out.println("tab["+i+"] = "+tab[i]);
		for(i=0;i<taille && x==false ;i++)
		{
			if(dif>tab[i])
			{
				x=true;
				decalerBas(i);	
				setRang(pseudo,i);				
			}
		}
		if(x==false)
		{
			setRang(pseudo,i);				
		}
		
	}


	/**
	 * decale le classement de tous les joueurs qui sont dans un classement superieur a i  d'un cran vers le haut
	 * @param  pseudo la personne qui demande la liste, qu on ne renverra pas dans la liste
	 * @param  i l'indice du classement ou il faut commencer le decalage 
	 */
	public void decalerHaut(int i,String pseudo)
	{
		
		PreparedStatement req=null;
		try
		{
				
				req=connexion.prepareStatement( "update qui_est_ce set rang=rang-1 where rang>?");
				req.setInt( 1, i );
				req.executeUpdate();
		}
		catch(SQLException ex){}
		try
		{
				
				req=connexion.prepareStatement( "update qui_est_ce set rang=-1 where pseudo=?");
				req.setString( 1, pseudo );
				req.executeUpdate();
		}
		catch(SQLException ex){System.out.println("erreur decalerHaut");}

	}


	/**
	 * decale le classement de tous les joueurs qui sont dans un classement superieur a i  d'un cran vers le bas
	 * @param  pseudo la personne qui demande la liste, qu on ne renverra pas dans la liste
	 * @param  i l'indice du classement ou il faut commencer le decalage 
	 */
	public void decalerBas(int i)
	{
		
		PreparedStatement req=null;
		try
		{
				
				req=connexion.prepareStatement( "update qui_est_ce set rang=rang+1 where rang>=?");
				req.setInt( 1, i );
				req.executeUpdate();
		}
		catch(SQLException ex){System.out.println("erreur decalerBas");}
	}



	/**
	 * change le classement du joueur pseudo avec la valeur i 
	 * @param  pseudo la personne qui se fait modifie son rang
	 * @param  i l'indice du nouveau classement 
	 */
	public void setRang(String pseudo,int i)
	{


	
		PreparedStatement req=null;
		try
		{
				req=connexion.prepareStatement( "update qui_est_ce set rang=? where pseudo=?");
				req.setInt( 1, i );
				req.setString( 2, pseudo );
				req.executeUpdate();
		}
		catch(SQLException ex){System.out.println("erreur setRang");}

	}

	/**
	 * remplit le tableau tab avec les scores des joueurs autres que le joueurs pseudo
	 * @param  tab le tableau a remplir
	 * @param  pseudo le joueur a ignorer
	 */
	public void remplirTableau(int[] tab,String pseudo)
	{
		PreparedStatement req=null;
		int i;
		try
		{
			req=connexion.prepareStatement( "select rang,(gagne-perdu) as dif from qui_est_ce where pseudo<>?");
			req.setString( 1,pseudo);
			ResultSet resultat = req.executeQuery();
			for(i=0;i<tab.length;i++)
			{

				resultat.next();
				tab[resultat.getInt("rang")]=resultat.getInt("dif");
				
				
			}
		}
		catch(SQLException ex){System.out.println("erreur remplir Tableau");}

	}


	/**
	 * recupere le nombre total de joueurs
	 * @return  le nombre de joueurs
	 */
	public int recupererNombreJoueurs()
	{
		PreparedStatement req=null;
		int nombre=-1;
		try
		{
				req=connexion.prepareStatement( "select count(*) as n from qui_est_ce");
				ResultSet resultat = req.executeQuery();
				resultat.next();
	    		nombre=resultat.getInt("n");
		}
		catch(SQLException ex){System.out.println("erreur recupererNombreJoueurs");}
		return nombre;


	}
	
	/**
	 * deconnecte tous les joueurs presents dans la base de donnees
	 */
	public void deconncterTous()
	{
		PreparedStatement req=null;
		try
		{
				req=connexion.prepareStatement( "update qui_est_ce set connecte=0 where connecte=1");
				req.executeUpdate();
		}
		catch(SQLException ex){System.out.println("deconnecter Tous");}


	}
	
	/**
	 * renvoie l'ensemble des questions d'un mode sous forme de chaine de caracteres separe par des ':' entre chaque question
	 * @param  mode que nous cherchons a renvoyer ses questions
	 */
	public String listeQuestion(String mode)
	{
		PreparedStatement req=null;
		int i;
		String reponse = "";
		int g;
		int p;
		String ps="";

		try
		{
			for(i=0;i<recupererNombreJoueurs();i++)
			{
			req=connexion.prepareStatement( "select q from question_"+mode);
			ResultSet resultat = req.executeQuery();
			while(resultat.next())
			{
				reponse=reponse+":"+resultat.getString("q");
			}

			ps = resultat.getString("q");
			reponse = reponse + ":" +ps;

			}
		}
		catch(SQLException ex){System.out.println("erreur listeQuestion");}


		return reponse;
	}


	public int[] getChampReponse( int id_question, String mode )
	{
		PreparedStatement req=null;
		int[] nombre= {-1,-1} ;
		try
		{
			req=connexion.prepareStatement( "select r,ref  from question_"+mode+" where id = ?");
			req.setInt(1,id_question);
			ResultSet resultat = req.executeQuery();
			resultat.next();
	    		nombre[0]=resultat.getInt("r");
	    		nombre[1] = resultat.getInt("ref");
		}
		catch(SQLException ex){System.out.println("erreur getChampReponse");}
		System.out.println("champ" + nombre[0] + " valeur"+ nombre[1]);
		return nombre;

	}


	public boolean verifierImage(int id_image, int num_champs,int br, boolean correct,String m)
	{
		PreparedStatement req=null;
		boolean x = false;
		String ch="champ"+num_champs;
		int res;

		if(correct)
		{
			try
			{
				req=connexion.prepareStatement( "select "+ch+" as rep from image_"+m+" where id=?");
				req.setInt(1,id_image);
				ResultSet resultat = req.executeQuery();
				resultat.next();
	    		res=resultat.getInt("rep");
	    		if(res!=br)
	    			x=true;
	    		else
	    			x=false;

			}
			catch(SQLException ex){}
		}
		else
		{
			try
			{
				req=connexion.prepareStatement( "select "+ch+" as rep from image_"+m+" where id=?");
				req.setInt(1,id_image);
				ResultSet resultat = req.executeQuery();
				resultat.next();
	    		res=resultat.getInt("rep");
	    		if(res==br)
	    			x=true;
	    		else
	    			x=false;

			}
			catch(SQLException ex){System.out.println("verifierImage");}
		}
		return x;
	}


	
	/**
	 * recupere le nombre total de questions d'un mode
	 * @param  mode le mode donne
	 * @return  le nombre total de questions d'un mode
	 */
	public int getNombreQuestion(String mode)
	{

		PreparedStatement req=null;
		int nombre=-1;
		try
		{
			req=connexion.prepareStatement( "select count(*) as n from question_"+mode);
			ResultSet resultat = req.executeQuery();
			resultat.next();
	    		nombre=resultat.getInt("n");
		}
		catch(SQLException ex){System.out.println("erreur getNombreQuestion");}
		return nombre;


	}

	public int getBonneReponse( int id_img,int champ,String m)
	{
		System.out.println("id_image = "+id_img+" champ"+champ);
		String col = "champ"+champ;
		System.out.println("**"+col+"**");

		PreparedStatement req=null;
		int nombre=-1;
		try
		{
			req=connexion.prepareStatement( "select "+col+" as m from image_"+m+" where id=?");
			req.setInt(1,id_img);

			ResultSet resultat = req.executeQuery();
			resultat.next();
			System.out.println("cloche");

	    	nombre=resultat.getInt("m");
		}
		catch(SQLException ex){System.out.println("erreur getBonneReponse");}
		return nombre;

	}


	/**
	 * recupere le nombre total d'images d'un mode
	 * @param  mode le mode donne
	 * @return  le nombre total de questions d'un mode
	 */
	public int getNbImages(String mode)
	{
		PreparedStatement req=null;
		int nombre=-1;
		try
		{
			req=connexion.prepareStatement( "select count(*) as n from image_"+mode);
			ResultSet resultat = req.executeQuery();
			resultat.next();
	    		nombre=resultat.getInt("n");
		}
		catch(SQLException ex){System.out.println("erreur getNbImages");}
		return nombre;
	}



	/**
	 * ajouter une personne a la liste des favoris d'une autre personne(la liste des favoris est une table de la b.d.d)
	 * @param  personne : la personne qui ajoute une autre dans ses favoris
	 * @param  fav : la personne a ajouter dans les favoris d'une autre
	 * @return  vrai ou faux selon si la deuxieme personne etait deja dans les favoris de la premiere ou pas
	 */
	public boolean ajouterFavoris(String personne,String fav)
	{
		PreparedStatement req=null;
		int nombre =-1;
		boolean x=false;
		try
		{

			req=connexion.prepareStatement( "select count(*) as n from favoris where personne=? and fav=?");
			req.setString(1,personne);
			req.setString(2,fav);
			ResultSet resultat = req.executeQuery();
			resultat.next();
		    	nombre=resultat.getInt("n");
			if(nombre==0)
			{
	
				try
				{
					req=connexion.prepareStatement( "insert into favoris values(?,?)");
					req.setString(1,personne);
					req.setString(2,fav);
					req.executeUpdate();
					x = true;
	
				}
				catch(SQLException ex){System.out.println("erreur ajouterFavoris 1ere requete");}
			}
			else
			{
				x = false;
			}
		}
		catch(SQLException ex){System.out.println("erreur ajouterFavoris 2eme requete");}
		return x;
	}

	/**
	 * supprime une personne de la liste des favoris d'une autre
	 * @param  personne : la personne qui supprime une autre dans ses favoris
	 * @param  fav : la personne a supprimer dans les favoris d'une autre
	 * @return  vrai ou faux selon si la deuxieme personne etait deja dans les favoris de la premiere ou pas
	 */
	public boolean supprimerFavoris(String personne,String fav)
	{
		PreparedStatement req=null;
		int nombre =-1;
		boolean x=false;
		try
		{

			req=connexion.prepareStatement( "select count(*) as n from favoris where personne=? and fav=?");
			req.setString(1,personne);
			req.setString(2,fav);
			ResultSet resultat = req.executeQuery();
			resultat.next();
		    	nombre=resultat.getInt("n");
			if(nombre==1)
			{
	
				try
				{
					req=connexion.prepareStatement( "delete from favoris where personne=? and fav=?");
					req.setString(1,personne);
					req.setString(2,fav);
					req.executeUpdate();
					x = true;
	
				}
				catch(SQLException ex){System.out.println("erreur supprimerFavoris 1ere requete");}
			}
			else
			{
				x = false;
			}
		}
		catch(SQLException ex){System.out.println("erreur supprimerFavoris 2eme requete");}
		return x;
	}



	/**
	 * renvoie la liste des favoris d'une personne
	 * @param  personne : la personne en question
	 * @return  une chaine de caracteres avec les pseudos des favoris d'une personne separes par des ':'
	 */
	public String listeFavoris(String personne)
	{
		PreparedStatement req=null;
		int i;
		String reponse = "";
		String ps="";
		try
		{
			
			req=connexion.prepareStatement( "select fav,connecte from favoris,qui_est_ce where fav=pseudo and personne=?");
			req.setString(1,personne);
			ResultSet resultat = req.executeQuery();
			while ( resultat.next())
			{
				reponse=reponse+":"+resultat.getString("fav")+"-"+resultat.getInt("connecte");
			}

			ps = resultat.getString("fav");
			reponse = reponse + ":" +ps;
		}
		catch(SQLException ex){}


		return reponse;	

	}



}












