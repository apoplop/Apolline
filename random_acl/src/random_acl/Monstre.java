package random_acl;


public class Monstre extends Personnage {
	GamePanel panel;
	direction d ;
	
	public Monstre(GamePanel panel, int posi_x, int posi_y) {
		super(panel, posi_x, posi_y);
		this.panel = panel;
		drawMonstre();
		
	}
	
	public void deplacerMonstre() {
		switch (d) {
		case BAS: {
			if (this.imageView.getY() < this.panel.screenHeight-this.panel.tileSize) {
				if (this.panel.labyrinthe.mapTable[this.getPosi_y()+1][this.getPosi_x()].equals("0")) {
					this.deplacerBas();
				
				}else {
					this.d = direction.HAUT;
				}
				
			}else {
				this.d = direction.HAUT;
			}
		}
		break;
		case HAUT: {
			if (this.imageView.getY() > 0) {
				if (this.panel.labyrinthe.mapTable[this.getPosi_y()][this.getPosi_x()].equals("0")) {
					this.deplacerHaut();
				
				}else {
					this.d = direction.BAS;
				}
				
			}else {
				this.d = direction.BAS;
			}
		}
		break;
		case GAUCHE: {
			if (this.imageView.getX() > 0) {
				if (this.panel.labyrinthe.mapTable[this.getPosi_y()][this.getPosi_x()].equals("0")) {
					this.deplacerGauche();
				
				}else {
					this.d = direction.DROITE;
				}
				
			}else {
				this.d = direction.DROITE;
			}
		}
		break;
		case DROITE: {
			if (this.imageView.getX() < this.panel.screenWidth-this.panel.tileSize) {
				if (this.panel.labyrinthe.mapTable[this.getPosi_y()][this.getPosi_x()+1].equals("0")) {
					this.deplacerDroite();
				}else {
					this.d = direction.GAUCHE;
				}
				
			}else {
				this.d = direction.GAUCHE;
			}
		}
		break;
		default:{
			
		}break;
		}
		
		
	}
	public void drawMonstre() {
		this.drawPersonnage("file:src/images/monstre.png");
	}
	public boolean colisionAutreMonstre(Monstre monstre) {
		if ((monstre.imageView.getX()+this.panel.tileSize <= this.imageView.getX()) && 
				(this.imageView.getX()<=monstre.imageView.getX()+this.panel.tileSize)) {
			return true;
		}else {
			
			return false;
		}
	}
}
