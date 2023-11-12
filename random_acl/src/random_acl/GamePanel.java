 package random_acl;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import random_acl.Personnage.direction;


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
	
	
	Labyrinthe labyrinthe;
	Labyrinthe labyrinthe2;
	Monstre m;
	
	Thread gameThread;
	public GamePanel() {
		labyrinthe = new Labyrinthe(this, "src/utils/map02.txt");
		//labyrinthe2 = new Labyrinthe(this, "src/utils/map01.txt");
		m = new Monstre(this,5,2);
		this.creerMonstre(labyrinthe);
		this.scene = new Scene(this,this.screenWidth,this.screenHeight);
	}
	
	public void startGameThread() {
        new Thread(() -> {
            while (true) {
            	update();
                try {
                    Thread.sleep(20); // Delay for smoother movement
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
	}
	public void update() {
	    Platform.runLater(() -> {
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
				}
			}
		}
	}
}
