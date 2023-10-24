package test1;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
	
	//initialisation des paramètres de l'écran
	final int originalTileSize=16;  //taille d'une case (16x16 pixels)
	final int scale =3; 		//échelle pour avoir un nombre de pixel adapté a la résolution de l'écran
	
	final int tileSize = originalTileSize*scale;
	final int maxScreenCol = 16;		//taille de l'écran (16x12 case)
	final int maxScreenRow = 12;		
	final int screenWidth = tileSize*maxScreenCol;	//768 pixels
	final int screenHeight = tileSize*maxScreenRow;	//576 pixels
	
	Thread gameThread;
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {  
		
		while (gameThread!=null) {  
			// le jeu va tourner dans cette loop, ça va permettre 2 choses:
			// 1- mettre a jour les informations du personnage (ex: la position avec les touches du clavier)
			update();
			// 2- mettre a jour l'affichage après chaque changement
			repaint();
			System.out.println("Le jeu est lancé et il tourne");		// test d'affichage pour voir comment ça marche
		}
		
	}
	
	public void update() {
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.white);
		g2.fillRect(100,100,tileSize,tileSize);
		g2.dispose();
	}
	
}
