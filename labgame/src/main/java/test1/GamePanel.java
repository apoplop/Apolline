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


public class GamePanel extends Pane {
	//initialisation des param�tres de l'�cran
	final int originalTileSize=16;  //taille d'une case (16x16 pixels)
	final int scale =3; 		//�chelle pour avoir un nombre de pixel adapt� a la r�solution de l'�cran
	
	final int tileSize = originalTileSize*scale;
	final int maxScreenCol = 16;		//taille de l'�cran (16x12 case)
	final int maxScreenRow = 12;		
	final int screenWidth = tileSize*maxScreenCol;	//768 pixels de largeur
	final int screenHeight = tileSize*maxScreenRow;	//576 pixels de hauteur
	
	public Scene scene = null;
	public boolean isPaused = false;
	
	
	Labyrinthe labyrinthe;
	Hero hero;
	public Text pauseText;
	public Text defaiteText;
	
	Thread gameThread;
	
	public GamePanel() {
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
        
        
        // Initialisaiton du labyrinthe
		labyrinthe = new Labyrinthe(this, "src/res/maps/map02.txt");
		this.creerMonstre(labyrinthe);
		this.scene = new Scene(this,this.screenWidth+1,this.screenHeight+1);
	}
	
	
	//Fonctions pour afficher les messages de victoire ou défaite dans l'interface utilisateur
	public void updatePauseTextVisibility() {
        // Affiche ou masque le texte en fonction de l'état de la pause
        pauseText.setVisible(isPaused);
        // Centre le texte horizontalement et verticalement
        pauseText.setLayoutX((screenWidth - pauseText.getBoundsInLocal().getWidth()) / 2);
        pauseText.setLayoutY((screenHeight - pauseText.getBoundsInLocal().getHeight()) / 2);
    }
	public void montrerMessageDefaite() {
	    defaiteText.setLayoutX((screenWidth - defaiteText.getBoundsInLocal().getWidth()) / 2);
	    defaiteText.setLayoutY((screenHeight - defaiteText.getBoundsInLocal().getHeight()) / 2);
		defaiteText.setVisible(true);
		defaiteText.toFront();
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
	        new Thread(() -> {
	            while (true) {
	            	if (!isPaused) {
	            		update();		//Mets à jour l'état du thread
	            	}
	            	this.scene.setOnKeyPressed(e -> {
	                    if (e.getCode() == KeyCode.P) {
	                		isPaused = !isPaused;		//Mettre en pause en changant l'état pas pause en pause
	                        updatePauseTextVisibility();
	                    }
	                });
	                try {
	                    Thread.sleep(20); // Delay for smoother movement
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        }).start();
		}

	
	
	//Update du thread game
	public void update() {
	    Platform.runLater(() -> {
	    	hero.deplacerHero();
	    	collisionEntreHeroMonstre();
	    	hero.updateHealthBar();
	        ArrayList<Monstre> monstres = new ArrayList();
	        monstres.addAll(this.labyrinthe.listeMonstre); // Copie manuelle des éléments dans une nouvelle liste
	        
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
