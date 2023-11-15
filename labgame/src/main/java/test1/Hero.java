package test1;

public class Hero extends Personnage {
	GamePanel panel;

	public Hero(GamePanel panel, int posi_x, int posi_y) {
		super(panel, posi_x, posi_y,10);
		this.panel = panel;
		this.vitesse = 3;
		this.drawPersonnage("file:src/res/images/heroV2.png");
	}
	
	public boolean colisionMonstre() {
		double a = this.imageView.getX();
		double b = this.imageView.getY();
		for (Monstre m: this.panel.labyrinthe.listeMonstre) {
				double x = m.imageView.getX();
				double y = m.imageView.getY();
				double dist = Math.sqrt((a-x)*(a-x)+(b-y)*(b-y));
				if (dist<=this.panel.tileSize) {
					System.out.print("colision monstre");
					System.out.println(" "+dist);
					//this.attaquer(m, -this.puissanceAttaque);
					if (m.getVie() <=0) {
						this.panel.labyrinthe.supprimerMonstre(m);
					}
					return true;
			}
		}
		return false;
	}
	public void deplacerHero() {
		this.panel.scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                {
                	if (this.imageView.getY() > 0) {
                		if (this.panel.labyrinthe.mapTable[this.getPosi_y()-1][this.getPosi_x()].equals("0")) {
    						this.deplacerHaut();
    					}
                	}
                }
                    break;
                case DOWN:{
                	if (this.imageView.getY() < this.panel.screenHeight-this.panel.tileSize) {
                		if (this.panel.labyrinthe.mapTable[this.getPosi_y()+1][this.getPosi_x()].equals("0")) {
    						this.deplacerBas();
    					
    					}
         
                	}
                }
                  
                    break;
                case LEFT:
                {
                	if (this.imageView.getX() > 0) {
                		if (this.panel.labyrinthe.mapTable[this.getPosi_y()][this.getPosi_x()].equals("0")) {
    						this.deplacerGauche();
    					
    					}
                	}
                }
                    break;
                case RIGHT:
                {
                	if (this.imageView.getX() < this.panel.screenWidth-this.panel.tileSize) {
                		if (this.panel.labyrinthe.mapTable[this.getPosi_y()][this.getPosi_x()+1].equals("0")) {
    						this.deplacerDroite();
    					}	
                	}
                }
                    break;
                default:
                    break;
            }
        });
	}

}
