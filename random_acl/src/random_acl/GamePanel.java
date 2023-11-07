package random_acl;

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
	
	public GridPane grid = new GridPane();
	public Scene scene = null;
	
	Labyrinthe labyrinthe = new Labyrinthe(this, "src/utils/map02.txt");
	Monstre monstre = new Monstre(this,2,3);
	
	Thread gameThread;
	public GamePanel() {
		this.labyrinthe.drawMap(labyrinthe.mapTable);
		this.monstre.drawMonstre();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		long interval=1000; // FPS
		long passTime =0;
		long oldTime = System.currentTimeMillis();
		long currentTime;
		
		while (gameThread != null){	
			currentTime = System.currentTimeMillis();
			passTime = currentTime - oldTime;
			if (passTime > interval ) {  //><
			update();
			
			System.out.println(monstre.getPosi_y());
			oldTime = System.currentTimeMillis();
			}
		}
	}
	
	public void update() {
		}
}
