package test1;

import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import java.util.ArrayList;


public class Hero extends Personnage {
	GamePanel panel;
	private enum direction {UP,DOWN,LEFT,RIGHT};
	private direction last_dir;
	private ProgressBar healthBar;
	private int vie;
	private int vieMax=100;
	public int degatAttaqueDistance;
	private int compteurPotion = 0;


    public Hero(GamePanel panel, int posi_x, int posi_y, int vieInitiale) {
        super(panel, posi_x, posi_y, vieInitiale);
        this.panel = panel;
        this.vitesse = 1;
        this.degatAttaqueDistance = 20;
        this.vieMax = vieMax;
        this.vie = vieInitiale; // Initialise la vie du héros à la valeur spécifiée
        
        //Ajout de la barre de vie du héro
        this.healthBar = new ProgressBar(1.0); // Initialise à 100%
        this.panel.getChildren().add(this.healthBar); // Ajoute la barre de vie à la scène
        this.drawPersonnage("file:src/res/images/heroV2.png");

    }


	//Mécanique de gesition et mise à jour de la vie du héro
    public void updateHealthBar() {
        Platform.runLater(() -> {
            double vieProportion = (double) this.vie / this.vieMax;
            this.healthBar.setProgress(vieProportion);
        });
    }
	  
	  public void recevoirDegat(int degat) {
	        this.vie -= degat; // Réduire la vie
	        if (this.vie < 0) {
	            this.vie = 0;
	            mourir();
	        }
	        updateHealthBar(); // Mettre à jour la barre de vie
	        System.out.println(this.vie);
	    }
	  public void gagnerVie(int quantite) {
		    this.vie += quantite; //Augmenter la vie
		    if (this.vie > this.vieMax) {
		        this.vie = this.vieMax;
		    }
		    updateHealthBar();
	        System.out.println(this.vie);
		}
	
	  public void mourir() {
	        System.out.println("Le héros est mort. Fin du jeu.");
	        Platform.runLater(() -> {
	            this.panel.getChildren().remove(this.imageView); // Retire le héro de l'interface
	            this.panel.isPaused = true; // Optionnel : mettez le jeu en pause ou terminez le jeu
	            this.panel.montrerMessageDefaite();	            
	        });
	    }
	  
	  
	  
	  
	  //Mécanique d'interaction entre le héro et les objet de la map (trésor, potion, feu)
//	  public boolean detectionVictoire(int x, int y) {
//		if (this.panel.labyrinthe.mapTable[x][y].equals("*")) {
//			return true;
//			}
//		return false;
//	}
	  
	  public boolean detectionVictoire(int pixelX, int pixelY) {
		    int caseX = pixelX / this.panel.tileSize;
		    int caseY = pixelY / this.panel.tileSize;

		    // Vérifier si la case correspondante contient une potion
		    if (this.panel.labyrinthe.mapTable[caseY][caseX].equals("*")) {
		        return true;
		    }
		    return false;
		}
	  
	  
	  
	  public boolean detectionPotion(int pixelX, int pixelY) {
		    int caseX = pixelX / this.panel.tileSize;
		    int caseY = pixelY / this.panel.tileSize;

		    // Vérifier si la case correspondante contient une potion
		    if (this.panel.labyrinthe.mapTable[caseY][caseX].equals("4")
		    		||this.panel.labyrinthe.mapTable[caseY][caseX].equals("3")
		    		||this.panel.labyrinthe.mapTable[caseY][caseX].equals("2")) {
		        return true;
		    }
		    return false;
		}
	  
	  public boolean detectionFeu(int pixelX, int pixelY) {
		    int caseX = pixelX / this.panel.tileSize;
		    int caseY = pixelY / this.panel.tileSize;

		    // Vérifier si la case correspondante contient une potion
		    if (this.panel.labyrinthe.mapTable[caseY][caseX].equals("5")) {
		        return true;
		    }
		    return false;
		}

	  
	  
	public void deplacerHero() {
		//System.out.println(this.imageView.getX()+" "+this.imageView.getY());
		this.panel.scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
	            case SPACE: {
	            	boolean collisionAvecMonstre = false;
	            	 for (Monstre m : this.panel.labyrinthe.listeMonstre) {
	                        if (this.colisionAvecPersonnage(m)) {
	                            collisionAvecMonstre = true;
	                            m.recevoirDegat(degatAttaqueDistance);
	                            System.out.println("-------------------------------attaque-------------------------------------------------");
	                            break; // Sortie de la boucle si une collision est détectée
	                        }
	                    }
	            	System.out.println("--------------------------------------------------------------------------------");
	            	break;
	            	
	            }
	            case UP: {
	                int marge = this.panel.tileSize / 10;
	                int nextY = (int) this.imageView.getY() - marge; // Déplacement vers le haut
	                int upperY = nextY - marge; // Haut du sprite avec la marge
	                int leftX = (int) this.imageView.getX() + marge;
	                int rightX = leftX + this.panel.tileSize - marge * 2;
	
	                boolean collision = this.panel.collisionMur(upperY, leftX) || this.panel.collisionMur(upperY, rightX);
	
	                
	             // Convertir les coordonnées en pixels en coordonnées de grille
	                int caseX = (int) this.imageView.getX() / this.panel.tileSize;
	                int caseY = (int) this.imageView.getY() / this.panel.tileSize;
	             // Accéder à la case actuelle
	                Case caseActuelle = this.panel.labyrinthe.getCase(caseX, caseY);

	                
	                
	                if (!collision) {
	                	boolean collisionAvecMonstre = false;
	                    // Vérification de la collision avec les monstres (inchangé)
	                    for (Monstre m : this.panel.labyrinthe.listeMonstre) {
	                        if (this.colisionAvecPersonnage(m)) {
	                            collisionAvecMonstre = true;
	                            this.recevoirDegat(m.getDegatAttaque());
	                            break; // Sortie de la boucle si une collision est détectée
	                        }
	                    }
	                    
	
	                    // Déplacez le héros si aucune collision avec un monstre
	                    if (!collisionAvecMonstre || this.last_dir != direction.UP) {
	                        this.deplacerHaut();
	                        
		                        //POTION
		                        if (this.detectionPotion((int) this.imageView.getX(), (int) this.imageView.getY())) {
		                        	caseActuelle.incrementerCompteurCase();
		                            System.out.println("compteur" + caseActuelle.getCompteurCase());
		                        	
		                        	if (caseActuelle.getCompteurCase() < 3) {
		                                this.gagnerVie(15);
			                            System.out.println("Potion trouvée, vie augmentée");
		                            } else {
		                                // Remplacer la potion par de l'herbe
		                                this.panel.labyrinthe.supprimerCase(caseX, caseY);
			                            System.out.println("case supprimée");
		                            }

		                        }
		                        
		                        //FEU
		                        if (this.detectionFeu((int) this.imageView.getX(), (int) this.imageView.getY())) {
		                            this.recevoirDegat(5); // degatFeu est la quantité de dégâts causés par le feu
		                            System.out.println("Piège de feu rencontré, vie réduite");
		                        }

		                        //TRESOR
		                        if (this.detectionVictoire((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                            	System.out.println("victoire du heros");
	                                this.panel.isPaused = true;
	                                this.panel.getChildren().add(this.panel.pauseText);
	                                this.panel.updatePauseTextVisibility();
	                            }
	                        	
	                        	
	                        
	                    }
	
	                    this.last_dir = direction.UP;
	                }
	                break;
	            }
  
	            case DOWN: {
	                int marge = this.panel.tileSize / 10; // La marge pour la vérification des coins
	                int nextY = (int) this.imageView.getY() + marge; // Déplacement vers le bas
	                int nextBottomY = nextY + this.panel.tileSize - marge; // Calcul de la position du bas avec la marge
	                int leftX = (int) this.imageView.getX() + marge; // Bord gauche avec la marge
	                int rightX = leftX + this.panel.tileSize - marge * 2; // Bord droit avec la marge

	                
	                
	                
	                // Vérifiez si les quatre coins sont libres
	                boolean collision = this.panel.collisionMur(nextY, leftX) || this.panel.collisionMur(nextY, rightX)
	                                    || this.panel.collisionMur(nextBottomY, leftX) || this.panel.collisionMur(nextBottomY, rightX);

	                
	                
	             // Convertir les coordonnées en pixels en coordonnées de grille
	                int caseX = (int) this.imageView.getX() / this.panel.tileSize;
	                int caseY = (int) this.imageView.getY() / this.panel.tileSize;
	             // Accéder à la case actuelle
	                Case caseActuelle = this.panel.labyrinthe.getCase(caseX, caseY);

	                
	                
	                if (!collision) {
	                	boolean collisionAvecMonstre = false;
	                    // Vérification de la collision avec les monstres
	                    for (Monstre m : this.panel.labyrinthe.listeMonstre) {
	                        if (this.colisionAvecPersonnage(m)) {
	                            collisionAvecMonstre = true;
	                            this.recevoirDegat(m.getDegatAttaque());
	                            break;
	                        }
	                    }
	      
	                    

	                    if (!collisionAvecMonstre || this.last_dir != direction.DOWN) {
	                        this.deplacerBas();
 
	                      //POTION
	                        if (this.detectionPotion((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	caseActuelle.incrementerCompteurCase();
	                            System.out.println("compteur" + caseActuelle.getCompteurCase());
	                        	
	                        	if (caseActuelle.getCompteurCase() < 3) {
	                                this.gagnerVie(15);
		                            System.out.println("Potion trouvée, vie augmentée");
	                            } else {
	                                // Remplacer la potion par de l'herbe
	                                this.panel.labyrinthe.supprimerCase(caseX, caseY);
		                            System.out.println("case supprimée");
	                            }

	                        }
	                        
//	                        //POTION
//		                    if (this.detectionPotion((int) this.imageView.getX(), (int) this.imageView.getY())) {
//		                    	this.gagnerVie(15); // quantiteVieGagnee est la quantité de vie à gagner
//	                         
//	                            System.out.println("Potion trouvée, vie augmentée");
//
//		                        }
		                    
		                    //FEU
		                    if (this.detectionFeu((int) this.imageView.getX(), (int) this.imageView.getY())) {
		                    	this.recevoirDegat(5); // degatFeu est la quantité de dégâts causés par le feu
		                        System.out.println("Piège de feu rencontré, vie réduite");
		                    }
		                        
		                    //TRESOR
	                        if (this.detectionVictoire((int) this.imageView.getX(), (int) this.imageView.getY())) {
                            	System.out.println("victoire du heros");
	                        	this.panel.isPaused = true;
	                        	this.panel.getChildren().add(this.panel.pauseText);
	                        	this.panel.updatePauseTextVisibility();
	                        }
	                    }
	                     
	                    

	                    this.last_dir = direction.DOWN;
	                }
	                break;
	            }
  
	            case LEFT: {
	                int marge = this.panel.tileSize / 10;
	                int nextX = (int) this.imageView.getX() - marge; // Déplacement vers la gauche
	                int upperY = (int) this.imageView.getY() + marge;
	                int lowerY = upperY + this.panel.tileSize - marge * 2;
	                int leftX = nextX - marge;

	                boolean collision = this.panel.collisionMur(upperY, leftX) || this.panel.collisionMur(lowerY, leftX);

	                
	             // Convertir les coordonnées en pixels en coordonnées de grille
	                int caseX = (int) this.imageView.getX() / this.panel.tileSize;
	                int caseY = (int) this.imageView.getY() / this.panel.tileSize;
	             // Accéder à la case actuelle
	                Case caseActuelle = this.panel.labyrinthe.getCase(caseX, caseY);

	                
	                
	                
	                if (!collision) {
	                	boolean collisionAvecMonstre = false;
	                    // Vérification de la collision avec les monstres
	                    for (Monstre m : this.panel.labyrinthe.listeMonstre) {
	                        if (this.colisionAvecPersonnage(m)) {
	                            collisionAvecMonstre = true;
	                            this.recevoirDegat(m.getDegatAttaque());
	                            break;
	                        }
	                    }
	                    
	                   

	                    if (!collisionAvecMonstre|| this.last_dir != direction.LEFT) {
	                        this.deplacerGauche();

	                      
	                      //POTION
	                        if (this.detectionPotion((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	caseActuelle.incrementerCompteurCase();
	                            System.out.println("compteur" + caseActuelle.getCompteurCase());
	                        	
	                        	if (caseActuelle.getCompteurCase() < 3) {
	                                this.gagnerVie(15);
		                            System.out.println("Potion trouvée, vie augmentée");
	                            } else {
	                                // Remplacer la potion par de l'herbe
	                                this.panel.labyrinthe.supprimerCase(caseX, caseY);
		                            System.out.println("case supprimée");
	                            }

	                        }
	                        
	                        //FEU
	                        if (this.detectionFeu((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                            this.recevoirDegat(5); // degatFeu est la quantité de dégâts causés par le feu
	                            System.out.println("Piège de feu rencontré, vie réduite");
	                        }
	                        
	                        //TRESOR
                            if (this.detectionVictoire((int) this.imageView.getX(), (int) this.imageView.getY())) {
                            	System.out.println("victoire du heros");
                                this.panel.isPaused = true;
                                this.panel.getChildren().add(this.panel.pauseText);
                                this.panel.updatePauseTextVisibility();
                            }
	                    }

	                    this.last_dir = direction.LEFT;
	                }
	                break;
	            }  
	            
	            case RIGHT: {
	                int marge = this.panel.tileSize / 10;
	                int nextX = (int) this.imageView.getX() + marge; // Déplacement vers la droite
	                int upperY = (int) this.imageView.getY() + marge;
	                int lowerY = upperY + this.panel.tileSize - marge * 2;
	                int rightX = nextX + this.panel.tileSize - marge;

	                boolean collision = this.panel.collisionMur(upperY, rightX) || this.panel.collisionMur(lowerY, rightX);

	                
	             // Convertir les coordonnées en pixels en coordonnées de grille
	                int caseX = (int) this.imageView.getX() / this.panel.tileSize;
	                int caseY = (int) this.imageView.getY() / this.panel.tileSize;
	             // Accéder à la case actuelle
	                Case caseActuelle = this.panel.labyrinthe.getCase(caseX, caseY);

	                
	                
	                
	                if (!collision) {
	                	boolean collisionAvecMonstre = false;
	                    // Vérification de la collision avec les monstres
	                    for (Monstre m : this.panel.labyrinthe.listeMonstre) {
	                        if (this.colisionAvecPersonnage(m)) {
	                            collisionAvecMonstre = true;
	                            this.recevoirDegat(m.getDegatAttaque());
	                            break;
	                        }
	                    }
	                    
	                    
	                    if (!collisionAvecMonstre || this.last_dir != direction.RIGHT) {
	                        this.deplacerDroite();

	                        
	                        
	                      //POTION
	                        if (this.detectionPotion((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	caseActuelle.incrementerCompteurCase();
	                            System.out.println("compteur" + caseActuelle.getCompteurCase());
	                        	
	                        	if (caseActuelle.getCompteurCase() < 3) {
	                                this.gagnerVie(15);
		                            System.out.println("Potion trouvée, vie augmentée");
	                            } else {
	                                // Remplacer la potion par de l'herbe
	                                this.panel.labyrinthe.supprimerCase(caseX, caseY);
		                            System.out.println("case supprimée");
	                            }

	                        }
	                        
	                        //FEU
	                        if (this.detectionFeu((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                            this.recevoirDegat(5); // degatFeu est la quantité de dégâts causés par le feu
	                            System.out.println("Piège de feu rencontré, vie réduite");
	                        }
	                        
	                        //TRESOR
                            if (this.detectionVictoire((int) this.imageView.getX(), (int) this.imageView.getY())) {
                            	System.out.println("victoire du heros");
                                this.panel.isPaused = true;
                                this.panel.getChildren().add(this.panel.pauseText);
                                this.panel.updatePauseTextVisibility();
                            }
	                    }

	                    this.last_dir = direction.RIGHT;
	                }
	                break;
	            }
            

            }
            
        }
		); //fin du switch

	}	

}
