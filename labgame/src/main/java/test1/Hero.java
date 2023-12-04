package test1;

import javafx.scene.input.KeyCode;
import javafx.scene.control.ProgressBar;

public class Hero extends Personnage {
	GamePanel panel;
	private enum direction {UP,DOWN,LEFT,RIGHT};
	private direction last_dir;
	private int degatAttaque;
	private ProgressBar healthBar;
	private int vie;
	private int vieMax=100;
	//private int vieMax;

	public Hero(GamePanel panel, int posi_x, int posi_y) {
		 
		super(panel, posi_x, posi_y,10);
		this.panel = panel;
		this.vitesse = 1;
		this.degatAttaque = 1;
		this.vieMax = vieMax;
	    this.vie = vieMax; // Initialise la vie du héros à sa valeur maximale
		this.drawPersonnage("file:src/res/images/heroV2.png");
		
		this.healthBar = new ProgressBar(20.0);  // La valeur initiale est 20.0 (100%)
        this.healthBar.setPrefSize(this.panel.tileSize, 20);  // Ajustez la taille selon vos préférences

        // Ajout de la barre de vie à la scène
        this.panel.getChildren().add(this.healthBar);
  	
	}
	
	public void updateHealthBar() {
	    // Calculer la proportion de la vie actuelle par rapport à la vie maximale
	    double proportion = (double) vie / vieMax;
	    this.healthBar.setProgress(proportion);
	    // Mettre à jour la largeur de l'image pour représenter la jauge de vie
	    double newWidth = proportion * this.panel.tileSize;
	    this.imageView.setFitWidth(newWidth);
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
					this.attaquer(m, -degatAttaque);
					if (m.getVie() <=0) {
						this.panel.labyrinthe.supprimerMonstre(m);
					}
					return true;
			}
		}
		return false;
	}
	
	public boolean detectionVictoire(int x, int y) {
		if (this.panel.labyrinthe.mapTable[x][y].equals("*")) {
			return true;
			}
		return false;
	}
	
	public void deplacerHero() {
		System.out.println(this.imageView.getX()+" "+this.imageView.getY());
		this.panel.scene.setOnKeyPressed(e -> {
			
            switch (e.getCode()) {
                case UP:
                {
                	if (this.imageView.getY() > 0) {
                		if (this.panel.labyrinthe.mapTable[this.getPosi_y()][this.getPosi_x()].equals("0")) {
                			if (!this.colisionMonstre() || this.last_dir!=direction.UP) {
                				this.deplacerHaut();
                				if(this.detectionVictoire(this.getPosi_y(),this.getPosi_x())) {
                					this.panel.labyrinthe.supprimerCase(this.getPosi_y(), this.getPosi_x());
                					this.panel.isPaused = true;
                					this.panel.getChildren().add(this.panel.pauseText);
                					this.panel.updatePauseTextVisibility();
                				}
                				this.last_dir = direction.UP;
                			}
    						
    						
    					}
                	}
                }
                    break;
                case DOWN:{
                	if (this.imageView.getY() < this.panel.screenHeight-this.panel.tileSize
                			&& !this.colisionMonstre()) {
                		if (this.panel.labyrinthe.mapTable[this.getPosi_y()+1][this.getPosi_x()].equals("0")) {
                			if (!this.colisionMonstre() || this.last_dir!=direction.DOWN) {
                				this.deplacerBas();
                				if(this.detectionVictoire(this.getPosi_y()+1,this.getPosi_x())) {
                					this.panel.labyrinthe.supprimerCase(this.getPosi_y()+1, this.getPosi_x());
                					this.panel.isPaused = true;
                					this.panel.getChildren().add(this.panel.pauseText);
                					this.panel.updatePauseTextVisibility();
                				}
        						this.last_dir = direction.DOWN;
                			}
    						
    						
    					}
         
                	}
                }
                  
                    break;
                case LEFT:
                {
                	if (this.imageView.getX() > 0 && !this.colisionMonstre()) {
                		if (this.panel.labyrinthe.mapTable[this.getPosi_y()][this.getPosi_x()].equals("0")) {
                			if (!this.colisionMonstre() || this.last_dir!=direction.LEFT) {
                				this.deplacerGauche();
                				if(this.detectionVictoire(this.getPosi_y(),this.getPosi_x())) {
                					this.panel.labyrinthe.supprimerCase(this.getPosi_y(), this.getPosi_x());
                					this.panel.isPaused = true;
                					this.panel.getChildren().add(this.panel.pauseText);
                					this.panel.updatePauseTextVisibility();
                				}
        						this.last_dir = direction.LEFT;
                			}
    						
    					
    					}
                	}
                }
                    break;
                case RIGHT:
                {
                	if (this.imageView.getX() < this.panel.screenWidth-this.panel.tileSize
                			&& !this.colisionMonstre()) {
                		if (this.panel.labyrinthe.mapTable[this.getPosi_y()][this.getPosi_x()+1].equals("0")) {
                			if (!this.colisionMonstre() || this.last_dir!=direction.RIGHT) {
                				this.deplacerDroite();
                				if(this.detectionVictoire(this.getPosi_y(),this.getPosi_x()+1)) {
                					this.panel.labyrinthe.supprimerCase(this.getPosi_y(), this.getPosi_x()+1);
                					this.panel.isPaused = true;
                					this.panel.getChildren().add(this.panel.pauseText);
                					this.panel.updatePauseTextVisibility();
                				}
        						this.last_dir = direction.RIGHT;
                			}
    						
    					}	
                	}
                }
                    break;
               
            }
           
        });
	}

}
