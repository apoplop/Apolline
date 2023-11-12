package random_acl;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Personnage {
	private int posi_x;
	private int posi_y;
	private Image image;
	public ImageView imageView;
	GamePanel panel;
	public  int nombreVie;
	private  int jaugeVie;
	private int vieActuelle;
	private int[] listeVie = new int[nombreVie];
	public enum direction {
		BAS,HAUT,GAUCHE,DROITE;
	}
	
	public Personnage(GamePanel panel,int posi_x, int posi_y) {
		for (int i=0; i<nombreVie; i++) {
			listeVie[i] = jaugeVie; 
		}
		this.posi_x = posi_x;
		this.posi_y = posi_y;
		this.panel = panel;
	}
	
	public int getVie() {
		return vieActuelle;
	}
	
	public void setVie(int vie) {
		if (vie > 0) {
			if (listeVie[vieActuelle-1] + vie >= jaugeVie) {
				listeVie[vieActuelle-1] = jaugeVie;
				vieActuelle += vieActuelle%nombreVie;
				listeVie[vieActuelle-1] = jaugeVie;
			}else {
				listeVie[vieActuelle-1] += vie;
			}
		}else {
			if (listeVie[vieActuelle-1] + vie < 0) {
				listeVie[vieActuelle-1] = 0;
				vieActuelle -= nombreVie%vieActuelle;
				listeVie[vieActuelle-1] = jaugeVie;
			}else {
				listeVie[vieActuelle-1] += vie;
			}
		}
	}
	
	public int getPosi_x() {
		return this.posi_x;
	}
	
	public int getPosi_y() {
		return this.posi_y;
	}
	
	public void deplacerHaut() {
		int ny = (int) this.imageView.getY() - this.panel.originalTileSize/10;
		this.imageView.setY(ny);
		this.posi_y = (int) this.imageView.getY()/48;
	

	}
	
	public void deplacerBas() {
		int ny = (int) this.imageView.getY() + this.panel.originalTileSize/10;
		this.imageView.setY(ny);
		this.posi_y = (int) this.imageView.getY()/48;
	
	}
	
	public void deplacerDroite() {
		int ny = (int) this.imageView.getX() + this.panel.originalTileSize/10;
		this.imageView.setX(ny);
		this.posi_x = (int) this.imageView.getX()/48;
	
		
	}
	
	public void deplacerGauche() {
		int ny = (int) this.imageView.getX() - this.panel.originalTileSize/10;
		this.imageView.setX(ny);
		this.posi_x = (int) this.imageView.getX()/48;
		
	}
	
	public void attaquer(Personnage personnage, int degat) {
		personnage.setVie(degat);
	}
	
	public void drawPersonnage(String fileSource) {
		this.image = new Image(fileSource);
		this.imageView = new ImageView(this.image);
		this.imageView.setFitWidth(this.panel.tileSize);
        this.imageView.setFitHeight(this.panel.tileSize);
        imageView.setX(posi_x*this.panel.tileSize);
        imageView.setY(posi_y*this.panel.tileSize);
        this.panel.getChildren().add(imageView);
	}
	
}
