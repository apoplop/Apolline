package test1;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import test1.Personnage.direction;

import javafx.scene.control.ProgressBar;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class GamePanel extends Pane {
	//initialisation des parametres de l'ecran
	final int originalTileSize=16;  //taille d'une case (16x16 pixels)
	final int scale =3; 		//echelle pour avoir un nombre de pixel adapte a la resolution de l'ecran
	
	final int tileSize = originalTileSize*scale;
	public int maxScreenCol = 25; //taille modifiée par la suite pour s'ajuster a la map chosie	
	public int maxScreenRow = 25;
	
	public int screenWidth = tileSize*maxScreenCol;	//768 pixels de largeur
	public int screenHeight = tileSize*maxScreenRow;	//576 pixels de hauteur
	
	//public Scene scene = null;
	public Scene scene;
	public boolean isPaused = false;
	
	enum typeCase {MUR,POTION_VIE,POTION_MAGIQUE,FEU,TRESOR,POTION_PROTECTION,TERRE};
	
	
	
	Labyrinthe labyrinthe;
 Hero hero;
	public Text pauseText;
	public Text defaiteText;
	
	Thread gameThread;
	
	public GamePanel(String mapName) {
		VBox layout = new VBox();
		
//		
	    // Gestion des événements liés à la touche 'P'
	    this.setOnKeyPressed(e -> {
	        if (e.getCode() == KeyCode.P) {
	            System.out.println("Touche P appuyée");
	            togglePause();
	        }
	    });
		
		// Configurez la taille de la scène en fonction de la carte
	    if (mapName.equals("src/res/maps/map02.txt")) {
	        this.maxScreenCol = 16;
	        this.maxScreenRow = 12;
	        // Recalculez screenWidth et screenHeight
	        this.screenWidth = tileSize * this.maxScreenCol;
	        this.screenHeight = tileSize * this.maxScreenRow;
	    } else if (mapName.equals("src/res/maps/map03.txt")) {
	        this.maxScreenCol = 21;
	        this.maxScreenRow = 15;
	        // Recalculez screenWidth et screenHeight
	        this.screenWidth = tileSize * this.maxScreenCol;
	        this.screenHeight = tileSize * this.maxScreenRow;
	    }

	    labyrinthe = new Labyrinthe(this, mapName);
	    this.creerMonstre(labyrinthe);
	    this.scene = new Scene(this, screenWidth, screenHeight);
		
	 
		
		//this.scene = new Scene(layout, screenWidth, screenHeight);
		
		//Message en cas de victoire, lorsuqe le héro trouve le trésor
		pauseText = new Text("Bravo vous avez gagné !!!");
        pauseText.setStyle("-fx-font-size: 24; -fx-fill: white;");
        pauseText.setVisible(false); // Initialement invisible
        pauseText.setTextAlignment(TextAlignment.CENTER);
        
        // Messagfe en cas de défaite, lorsque le héros n'a plus de vie
        defaiteText = new Text("!!!!!!  Game over !!!!!!");
        defaiteText.setStyle("-fx-font-size: 24; -fx-fill: white;");
        defaiteText.setVisible(false);
        defaiteText.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(defaiteText); // Ajoutez le message de défaite à la scène
        
        this.scene.getRoot().requestFocus(); // Assurez-vous que la scène a le focus
		// Démarrage du thread de jeu
        startGameThread();
        
	}


	public void togglePause() {
	    isPaused = !isPaused;
	    System.out.println("Etat de pause : " + isPaused); // Pour le débogage
	    updatePauseTextVisibility(); // Mettre à jour la visibilité du texte de pause
	}
	
//	//Fonctions pour afficher les messages de victoire ou défaite dans l'interface utilisateur
//	public void updatePauseTextVisibility() {
//        // Affiche ou masque le texte en fonction de l'état de la pause
//        pauseText.setVisible(isPaused);
//        // Centre le texte horizontalement et verticalement
//        pauseText.setLayoutX((screenWidth - pauseText.getBoundsInLocal().getWidth()) / 2);
//        pauseText.setLayoutY((screenHeight - pauseText.getBoundsInLocal().getHeight()) / 2);
//    }
	
	//Fonctions pour afficher les messages de victoire ou défaite dans l'interface utilisateur
		public void updatePauseTextVisibility() {
	        
			Platform.runLater(() -> {
	            Alert alert = new Alert(AlertType.CONFIRMATION);
	            alert.setTitle("Victory");
	            alert.setHeaderText("!!!!!! Vous avez gagné !!!!!!");
	            alert.setContentText("Voulez-vous quitter ou recommencer?");

	            ButtonType quitterButton = new ButtonType("Quitter");
	            ButtonType recommencerButton = new ButtonType("Recommencer");

	            alert.getButtonTypes().setAll(quitterButton, recommencerButton);

	            alert.showAndWait().ifPresent(response -> {
	                if (response == quitterButton) {
	                    Platform.exit();
	                    System.exit(0);
	                } else if (response == recommencerButton) {
	                	alert.close(); // Ferme l'alerte
	                    //recommencerJeu(); // Appel de la méthode pour recommencer le jeu
	                    alert.close(); // Ferme l'alerte
	                }
	            });
	        });
	    }
	
	public void montrerMessageDefaite() {
		Platform.runLater(() -> {
            this.isPaused = true; // Optionnel : mettez le jeu en pause ou terminez le jeu
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("!!!!!! Game over !!!!!!");
            alert.setContentText("Voulez-vous quitter ou recommencer?");

            ButtonType quitterButton = new ButtonType("Quitter");
            ButtonType recommencerButton = new ButtonType("Recommencer");

            alert.getButtonTypes().setAll(quitterButton, recommencerButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == quitterButton) {
                    Platform.exit();
                    System.exit(0);
                } else if (response == recommencerButton) {
                	alert.close(); // Ferme l'alerte
                	//this.panel.recommencerJeu(); // Appel de la méthode pour recommencer le jeu
                }
            });
        });
	}

	
	//public Map<String, Integer> compteurPotion = new HashMap<>();
	
	//Placement des monstre et du héro
	public void creerMonstre(Labyrinthe l) {
		int vieInitialeMonstre = 50;
	    int vieInitialeHero = 100; 
	    
		for (int i=0; i<l.mapTable.length; i++) {
			for (int j=0; j<l.mapTable[i].length; j++) {
				switch(l.mapTable[i][j]) {
				case "d": {
					l.mapTable[i][j] ="0";
					Monstre m = new Monstre(this,j,i, vieInitialeMonstre);
					m.d = direction.DROITE;
					l.listeMonstre.add(m);
				}break;
				case "g": {
					l.mapTable[i][j] ="0";
					Monstre m = new Monstre(this,j,i, vieInitialeMonstre);
					m.d = direction.GAUCHE;
					l.listeMonstre.add(m);				
				}break;
				case "b": {
					l.mapTable[i][j] ="0";
					Monstre m = new Monstre(this,j,i, vieInitialeMonstre);
					m.d = direction.BAS;
					l.listeMonstre.add(m);
				}break;
				case "h": {
					l.mapTable[i][j] ="0";
					Monstre m = new Monstre(this,j,i, vieInitialeMonstre);
					m.d = direction.HAUT;
					l.listeMonstre.add(m);
				}break;
				case "s": {
					l.mapTable[i][j] ="0";
					hero = new Hero(this,j,i, vieInitialeHero);
				}break;
				
				}
			}
		}
	}
	
	
	
	//Gestion des déplacement et des colisions entre monstre et héro et avec les murs
	public void collisionEntreHeroMonstre() {
	    for (Monstre m : this.labyrinthe.listeMonstre) {
	        if (hero.colisionAvecPersonnage(m)) {
	            System.out.println(hero.getVie());
	            hero.recevoirDegat(m.getDegatAttaque());
	            System.out.println("Collision avec un monstre, dégâts reçus par le héros");
	            if (hero.getVie() <= 0) {
	                hero.mourir();
	            }
	            break; // Sortie de la boucle si une collision est détectée
	        }
	    }
	}
	public boolean collisionMur(int y, int x) {
	    int caseX = x / tileSize;
	    int caseY = y / tileSize;
	    return labyrinthe.mapTable[caseY][caseX].equals("1"); //return true si la case est un mur
	}
	
	
	//Départ du thread game
		public void startGameThread() {
	        gameThread = new Thread(() -> {
	            while (true) {
	                if (!isPaused) {
	                	//System.out.println("We are here");
	                    update(); // Update the game state
	                }
	                try {
	                    Thread.sleep(20); // Delay for smoother movement
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	        gameThread.start();
	    }

	
	
		//Update du thread game
		public void update() {
		    if (isPaused) {
		        // Si le jeu est en pause, ne pas mettre à jour le jeu
		        return;
		    }

		    Platform.runLater(() -> {
		        hero.deplacerHero();
		        collisionEntreHeroMonstre();
		        hero.updateHealthBar();
		        ArrayList<Monstre> monstres = new ArrayList<>(this.labyrinthe.listeMonstre);

		        for (Monstre m : monstres) {
		            if (this.getChildren().contains(m.imageView)) {
		                m.deplacerMonstre();
		            } else {
		                this.labyrinthe.supprimerMonstre(m);
		            }
		        }
		    });
		}





	
}