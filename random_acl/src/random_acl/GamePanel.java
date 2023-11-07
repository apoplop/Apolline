package random_acl;

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
	
	public GridPane grid = new GridPane();
	public Scene scene = null;
	
	
	Labyrinthe labyrinthe;
	Monstre monstre1;
	Monstre monstre2;

	
	Thread gameThread;
	public GamePanel() {
		labyrinthe = new Labyrinthe(this, "src/utils/map02.txt");
		monstre1 = new Monstre(this,1,1);
		monstre1.d = direction.DROITE;
		monstre2 = new Monstre(this,2,0);
		monstre2.d = direction.BAS;
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
		this.monstre1.deplacerMonstre();
		this.monstre2.deplacerMonstre();
		if (monstre1.colisionAutreMonstre(monstre2)) {
			System.out.println("colision");
			//this.monstre2.d = direction.HAUT;
		}
		}
}
