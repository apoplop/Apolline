package test1;

import javafx.application.Platform;
import javafx.scene.image.Image;
import test1.GamePanel.typeCase;



public class Monstre extends Personnage {
	GamePanel panel;
	direction d ;
	private int degatAttaque; // Dégâts que le monstre peut infliger, valeur à initialiser, peut changer en fonction du niveau choisi par exemple
	private int vie;
	private enum marche {Up0,Up1,Down0,Down1,Left0,Left1,Right0,Right1};
	private marche last_marche;
	private direction last_dir;
	
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
	                if (this.last_dir != direction.BAS || !this.colisionAutreMonstre()) {
                    	if (!this.panel.isPaused) {
                    		if(this.last_marche!=marche.Down1) {
                    			this.imageView.setImage(new Image("file:src/res/imagesV2/monstre/orc_down_1.png"));
                    			this.last_marche = marche.Down1;
                    		}else if (this.last_marche != marche.Down0) {
                    			this.imageView.setImage(new Image("file:src/res/imagesV2/monstre/orc_down_2.png"));
                    			this.last_marche = marche.Down0;
                    		}
                    		
                    	}
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
	                if (this.last_dir != direction.HAUT || !this.colisionAutreMonstre()) {
                    	if (!this.panel.isPaused) {
                    		if(this.last_marche!=marche.Up1) {
                    			this.imageView.setImage(new Image("file:src/res/imagesV2/monstre/orc_down_1.png"));
                    			this.last_marche = marche.Up1;
                    		}else if (this.last_marche != marche.Up0) {
                    			this.imageView.setImage(new Image("file:src/res/imagesV2/monstre/orc_down_2.png"));
                    			this.last_marche = marche.Up0;
                    		}
                    		
                    	}
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
	                if (this.last_dir != direction.GAUCHE || !this.colisionAutreMonstre()) {
                    	if (!this.panel.isPaused) {
                    		if(this.last_marche!=marche.Left1) {
                    			this.imageView.setImage(new Image("file:src/res/imagesV2/monstre/skeletonlord_left_1.png"));
                    			this.last_marche = marche.Left1;
                    		}else if (this.last_marche != marche.Left0) {
                    			this.imageView.setImage(new Image("file:src/res/imagesV2/monstre/skeletonlord_left_2.png"));
                    			this.last_marche = marche.Left0;
                    		}
                    		
                    	}
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
	                if (this.last_dir != direction.DROITE || !this.colisionAutreMonstre()) {
                    	if (!this.panel.isPaused) {
                    		if(this.last_marche!=marche.Right1) {
                    			this.imageView.setImage(new Image("file:src/res/imagesV2/monstre/skeletonlord_right_1.png"));
                    			this.last_marche = marche.Right1;
                    		}else if (this.last_marche != marche.Right0) {
                    			this.imageView.setImage(new Image("file:src/res/imagesV2/monstre/skeletonlord_right_2.png"));
                    			this.last_marche = marche.Right0;
                    		}
                    		
                    	}
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
