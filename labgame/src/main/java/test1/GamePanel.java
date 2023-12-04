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


public class GamePanel extends Pane {
	//initialisation des param�tres de l'�cran
	final int originalTileSize=16;  //taille d'une case (16x16 pixels)
	final int scale =3; 		//�chelle pour avoir un nombre de pixel adapt� a la r�solution de l'�cran
	
	final int tileSize = originalTileSize*scale;
	final int maxScreenCol = 16;		//taille de l'�cran (16x12 case)
	final int maxScreenRow = 12;		
	final int screenWidth = tileSize*maxScreenCol;	//768 pixels
	final int screenHeight = tileSize*maxScreenRow;	//576 pixels
	
	public Scene scene = null;
	public boolean isPaused = false;
	
	
	Labyrinthe labyrinthe;
	Labyrinthe labyrinthe2;
	Hero hero;
	public Text pauseText;
	
	Thread gameThread;
	public GamePanel() {
		pauseText = new Text("Bravo vous avez gagné !!");
        pauseText.setStyle("-fx-font-size: 24; -fx-fill: white;");
        pauseText.setVisible(false); // Initialement invisible
        pauseText.setTextAlignment(TextAlignment.CENTER);
		labyrinthe = new Labyrinthe(this, "src/res/maps/map02.txt");
		//labyrinthe2 = new Labyrinthe(this, "src/utils/map01.txt");
		this.creerMonstre(labyrinthe);
		this.scene = new Scene(this,this.screenWidth,this.screenHeight);
	}
	public void updatePauseTextVisibility() {
        // Affiche ou masque le texte en fonction de l'état de la pause
        pauseText.setVisible(isPaused);
        // Centre le texte horizontalement et verticalement
        pauseText.setLayoutX((screenWidth - pauseText.getBoundsInLocal().getWidth()) / 2);
        pauseText.setLayoutY((screenHeight - pauseText.getBoundsInLocal().getHeight()) / 2);
    }
	
	public void startGameThread() {
        new Thread(() -> {
            while (true) {
            	if (!isPaused) {
            		update();
            	}
            	this.scene.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.P) {
                        togglePause();
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
	public void togglePause() {
        isPaused = !isPaused;
    }
	public void update() {
	    Platform.runLater(() -> {
	    	hero.deplacerHero();
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




	public void creerMonstre(Labyrinthe l) {
		for (int i=0; i<l.mapTable.length; i++) {
			for (int j=0; j<l.mapTable[i].length; j++) {
				switch(l.mapTable[i][j]) {
				case "d": {
					l.mapTable[i][j] ="0";
					Monstre m = new Monstre(this,j,i);
					m.d = direction.DROITE;
					l.listeMonstre.add(m);
				}break;
				case "g": {
					l.mapTable[i][j] ="0";
					Monstre m = new Monstre(this,j,i);
					m.d = direction.GAUCHE;
					l.listeMonstre.add(m);				
				}break;
				case "b": {
					l.mapTable[i][j] ="0";
					Monstre m = new Monstre(this,j,i);
					m.d = direction.BAS;
					l.listeMonstre.add(m);
				}break;
				case "h": {
					l.mapTable[i][j] ="0";
					Monstre m = new Monstre(this,j,i);
					m.d = direction.HAUT;
					l.listeMonstre.add(m);
				}break;
				case "s": {
					l.mapTable[i][j] ="0";
					hero = new Hero(this,j,i);
				}break;
				
				}
			}
		}
	}
}
