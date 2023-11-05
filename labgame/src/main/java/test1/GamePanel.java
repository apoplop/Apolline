package test1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GamePanel extends GridPane implements Runnable {
	//initialisation des param�tres de l'�cran
	final int originalTileSize=16;  //taille d'une case (16x16 pixels)
	final int scale =3; 		//�chelle pour avoir un nombre de pixel adapt� a la r�solution de l'�cran
	
	final int tileSize = originalTileSize*scale;
	final int maxScreenCol = 16;		//taille de l'�cran (16x12 case)
	final int maxScreenRow = 12;		
	final int screenWidth = tileSize*maxScreenCol;	//768 pixels
	final int screenHeight = tileSize*maxScreenRow;	//576 pixels
	
	public final int pixel_size = 40;
	public GridPane grid = new GridPane();
	public Scene scene = null;
	
	Thread gameThread;
	public GamePanel() {
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		while (gameThread!=null) {  
			// le jeu va tourner dans cette loop, �a va permettre 2 choses:
			// 1- mettre a jour les informations du personnage (ex: la position avec les touches du clavier)
			update();
			// 2- mettre a jour l'affichage apr�s chaque changement
			System.out.println("Le jeu est lanc� et il tourne");		// test d'affichage pour voir comment �a marche
		}
	}
	
	public void update() {
			
		}
}
