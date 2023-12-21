package test1;

import javafx.application.Platform;
import test1.GamePanel.typeCase;


public class Monstre extends Personnage {
	GamePanel panel;
	direction d ;
	private int degatAttaque; // Dégâts que le monstre peut infliger, valeur à initialiser, peut changer en fonction du niveau choisi par exemple
	private int vie;
	
	public Monstre(GamePanel panel, int posi_x, int posi_y, int vie) {
        super(panel, posi_x, posi_y, vie); //Monstre hérite de vie de la classe Personnage
        this.degatAttaque = 1; // Les dégât lors d'une rencontre avec un héro sont initialisé à 5
		this.vitesse = 0;
		this.panel = panel;
		this.vie = vie;
		drawMonstre();
		
	}
	
	 // Getter pour degatAttaque
    public int getDegatAttaque() {
        return degatAttaque;
    }
    
    public void recevoirDegat(int degat) {
        this.vie -= degat; // Réduire la vie
        if (this.vie < 0) {
            this.vie = 0;
            mourir();
        }
        System.out.println(this.vie);
    }
    
    public void mourir() {
        System.out.println("Un monstre est mort.");
        Platform.runLater(() -> {
            // Retirer le monstre de l'affichage
            panel.getChildren().remove(imageView);

            // Retirer le monstre de la liste des monstres actifs
            panel.labyrinthe.listeMonstre.remove(this);
        });
    }
	
	public void deplacerMonstre() {
		int caseX = (int) this.imageView.getX() / this.panel.tileSize;
	    int caseY = (int) this.imageView.getY() / this.panel.tileSize;
	    if (this.panel.getChildren().contains(this.imageView)) {
	        switch (d) {
	            case BAS: {
	                if (caseY < this.panel.labyrinthe.getCaseColumn()-1 && !this.colisionAutreMonstre() 
	                        && !this.colisionAvecPersonnage(this.panel.hero)) {
	                    if (this.panel.labyrinthe.getCase(caseX, caseY+1).getTypeCase() == typeCase.TERRE) {
	                        this.deplacerBas();
	                    } else {
	                        this.d = direction.HAUT;
	                        this.deplacerHaut();
	                    }
	                } else {
	                    this.d = direction.HAUT;
	                    this.deplacerHaut();
	                }
	            }
	            break;
	            case HAUT: {
	                if (this.imageView.getY() > 0 && !this.colisionAutreMonstre()
	                        && !this.colisionAvecPersonnage(this.panel.hero)) {
	                    if (this.panel.labyrinthe.getCase(caseX, caseY).getTypeCase() == typeCase.TERRE) {
	                        this.deplacerHaut();
	                    } else {
	                        this.d = direction.BAS;
	                        this.deplacerBas();
	                    }
	                } else {
	                    this.d = direction.BAS;
	                    this.deplacerBas();
	                }
	            }
	            break;
	            case GAUCHE: {
	                if (this.imageView.getX() > 0 && !this.colisionAutreMonstre() 
	                        && !this.colisionAvecPersonnage(this.panel.hero)) {
	                    if (this.panel.labyrinthe.getCase(caseX, caseY).getTypeCase() == typeCase.TERRE) {
	                        this.deplacerGauche();
	                    } else {
	                        this.d = direction.DROITE;
	                        this.deplacerDroite();
	                    }
	                } else {
	                    this.d = direction.DROITE;
	                    this.deplacerDroite();
	                }
	            }
	            break;
	            case DROITE: {
	                if (caseX < this.panel.labyrinthe.getCaseRow()-1 && !this.colisionAutreMonstre()
	                        && !this.colisionAvecPersonnage(this.panel.hero)) {
	                    if (this.panel.labyrinthe.getCase(caseX+1, caseY).getTypeCase() == typeCase.TERRE) {
	                        this.deplacerDroite();
	                    } else {
	                        this.d = direction.GAUCHE;
	                        this.deplacerGauche();
	                    }
	                } else {
	                    this.d = direction.GAUCHE;
	                    this.deplacerGauche();
	                }
	            }
	            break;
	            default: {
	                // Cas par défaut si nécessaire
	            }
	            break;
	        }
	    }
	}

	
	public void drawMonstre() {
		this.drawPersonnage("file:src/res/images/monstreV2.png");
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
					System.out.print("colision monstre - monstre : ");
					System.out.println(" "+dist);
			
					return true;
			}
		}
		}
		return false;
	}
}
