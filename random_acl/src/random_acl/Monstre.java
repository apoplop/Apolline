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
			if (this.imageView.getY() < this.panel.screenHeight-this.panel.tileSize && !this.colisionAutreMonstre()) {
				if (this.panel.labyrinthe.mapTable[this.getPosi_y()+1][this.getPosi_x()].equals("0")) {
					this.deplacerBas();
				
				}else {
					this.d = direction.HAUT;
					this.deplacerHaut();
				}
				
			}else {
				this.d = direction.HAUT;
				this.deplacerHaut();
			}
		}
		break;
		case HAUT: {
			if (this.imageView.getY() > 0 && !this.colisionAutreMonstre()) {
				if (this.panel.labyrinthe.mapTable[this.getPosi_y()][this.getPosi_x()].equals("0")) {
					this.deplacerHaut();
				
				}else {
					this.d = direction.BAS;
					this.deplacerBas();
				}
				
			}else {
				this.d = direction.BAS;
				this.deplacerBas();
			}
		}
		break;
		case GAUCHE: {
			if (this.imageView.getX() > 0 && !this.colisionAutreMonstre()) {
				if (this.panel.labyrinthe.mapTable[this.getPosi_y()][this.getPosi_x()].equals("0")) {
					this.deplacerGauche();
				
				}else {
					this.d = direction.DROITE;
					this.deplacerDroite();
				}
				
			}else {
				this.d = direction.DROITE;
				this.deplacerDroite();
			}
		}
		break;
		case DROITE: {
			if (this.imageView.getX() < this.panel.screenWidth-this.panel.tileSize && !this.colisionAutreMonstre()) {
				if (this.panel.labyrinthe.mapTable[this.getPosi_y()][this.getPosi_x()+1].equals("0")) {
					this.deplacerDroite();
				}else {
					this.d = direction.GAUCHE;
					this.deplacerGauche();
				}
				
			}else {
				this.d = direction.GAUCHE;
				this.deplacerGauche();
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
	public boolean colisionAutreMonstre() {
		double a = this.imageView.getX();
		double b = this.imageView.getY();
		for (Monstre m: this.panel.labyrinthe.listeMonstre) {
			if (m != this) {
				double x = m.imageView.getX();
				double y = m.imageView.getY();
				double dist = Math.sqrt((a-x)*(a-x)+(b-y)*(b-y));
				if (dist<=this.panel.tileSize) {
					System.out.print("colision");
					System.out.println(" "+dist);
					return true;
			}
		}
		}
		return false;
	}
}
