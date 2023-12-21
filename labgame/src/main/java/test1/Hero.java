package test1;

import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import test1.GamePanel.typeCase;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

import java.io.File;
import java.util.ArrayList;


public class Hero extends Personnage {
	GamePanel panel;
	private enum direction {UP,DOWN,LEFT,RIGHT};
	private enum marche {Up0,Up1,Down0,Down1,Left0,Left1,Right0,Right1};
	private marche last_marche;
	private direction last_dir;
	private ProgressBar healthBar;
	private Label infoNiveau;
	private int vie;
	private boolean gotKey = false;
	private int vieMax=100;
	public int degatAttaqueDistance;
	private int compteurPotion = 0;
	private MediaPlayer mediaPlayer;


    public Hero(GamePanel panel, int posi_x, int posi_y, int vieInitiale) {
        super(panel, posi_x, posi_y, vieInitiale);
        this.panel = panel;
        this.vitesse = 5;
        this.degatAttaqueDistance = 20;
        this.vieMax = vieMax;
        this.vie = vieInitiale; // Initialise la vie du héros à la valeur spécifiée
        this.last_marche = marche.Up0;
        //Ajout de la barre de vie du héro
        Label vie = new Label("vie : ");
        vie.setFont(new Font("Arial", 24));
        this.panel.paneInfo.add(vie, 0, 0);
        this.healthBar = new ProgressBar(1.0); // Initialise à 100%
        healthBar.setStyle("-fx-accent: red;"); 
        this.panel.paneInfo.add(this.healthBar,1,0);
        this.panel.paneInfo.setHgap(30);
        this.infoNiveau = new Label("Niveau joueur: "+(this.panel.getNiveau()+1));
        this.infoNiveau.setFont(new Font("Arial", 24));
        this.panel.paneInfo.add(infoNiveau,2,0);
        //this.panel.getChildren().add(this.healthBar); // Ajoute la barre de vie à la scène
        this.drawPersonnage("file:src/res/imagesV2/hero/PD1.png");

    }


	//Mécanique de gesition et mise à jour de la vie du héro
    public void updateHealthBar() {
        Platform.runLater(() -> {
            double vieProportion = (double) this.vie / this.vieMax;
            this.healthBar.setProgress(vieProportion);
            this.infoNiveau.setText("Niveau joueur: "+(this.panel.getNiveau()+1));
            this.infoNiveau.setFont(new Font("Arial", 24));
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
	            this.panel.montrerMessage("Game Over",0);
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
		    if (this.gotKey && this.panel.labyrinthe.getCase(caseX, caseY).getTypeCase() == typeCase.TRESOR) {
		        return true;
		    }
		    return false;
		}
	  public boolean detectionPassage(int pixelX, int pixelY) {
		    int caseX = pixelX / this.panel.tileSize;
		    int caseY = pixelY / this.panel.tileSize;

		    // Vérifier si la case correspondante contient une potion
		    if (this.gotKey && this.panel.labyrinthe.getCase(caseX, caseY).getTypeCase() == typeCase.PORTE) {
		        return true;
		    }
		    return false;
		}
	  
	  public boolean detectionCle(int pixelX, int pixelY) {
		   int caseX = pixelX / this.panel.tileSize;
		    int caseY = pixelY / this.panel.tileSize;
		    if (this.panel.labyrinthe.getCase(caseX, caseY).getTypeCase() == typeCase.CLE) {
		    	this.panel.labyrinthe.supprimerCase(caseX, caseY);
		    	System.out.println("cle ok");
		    	return true;
		    	
		    }
		    return false;
	  }
	  
	  public boolean detectionPotion(int pixelX, int pixelY) {
		    int caseX = pixelX / this.panel.tileSize;
		    int caseY = pixelY / this.panel.tileSize;
		    
		    if (this.panel.labyrinthe.getCase(caseX, caseY).getTypeCase() == typeCase.POTION_MAGIQUE
		    		|| this.panel.labyrinthe.getCase(caseX, caseY).getTypeCase() == typeCase.POTION_PROTECTION
		    		|| this.panel.labyrinthe.getCase(caseX, caseY).getTypeCase() == typeCase.POTION_VIE) {
		    	return true;
		    }
		    return false;
		}
	  
	  public boolean detectionFeu(int pixelX, int pixelY) {
		    int caseX = pixelX / this.panel.tileSize;
		    int caseY = pixelY / this.panel.tileSize;

		    // Vérifier si la case correspondante contient du feu
		    if (this.panel.labyrinthe.getCase(caseX, caseY).getTypeCase() == typeCase.FEU) {
		        return true;
		    }
		    return false;
		}

		public void startPickUpMusic() {
	        String musicFile = "src/res/audio/ItemPicked.wav"; // Remplacer par le chemin de votre fichier audio
	        Media sound = new Media(new File(musicFile).toURI().toString());
	        mediaPlayer = new MediaPlayer(sound);
	        mediaPlayer.setStartTime(Duration.seconds(3));
	        mediaPlayer.setStopTime(Duration.seconds(5));
	        //mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Jouer en boucle
	        mediaPlayer.play();
	    }
		 public void stopMusic() {
		        if (mediaPlayer != null) {
		            mediaPlayer.stop();
		  }
		 }
		    
	  
	public void deplacerHero() {
		
		//System.out.println(this.imageView.getX()+" "+this.imageView.getY());
		
		this.panel.setOnKeyPressed(e -> {
            switch (e.getCode()) {
	            case P: {
	            	this.panel.togglePause();
	            }break;
	            case SPACE: {
	            	if (this.last_marche == marche.Up0 || this.last_marche == marche.Up1) {
	            		this.imageView.setImage(new Image("file:src/res/imagesV2/hero/player_attack_up_1.png"));
	            	}
	            	if (this.last_marche == marche.Down0 || this.last_marche == marche.Down1) {
	            		this.imageView.setImage(new Image("file:src/res/imagesV2/hero/player_attack_down_1.png"));
	            	}
	            	if (this.last_marche == marche.Left0 || this.last_marche == marche.Left1) {
	            		this.imageView.setImage(new Image("file:src/res/imagesV2/hero/player_attack_left_1.png"));
	            	}
	            	if (this.last_marche == marche.Right0 || this.last_marche == marche.Right1) {
	            		this.imageView.setImage(new Image("file:src/res/imagesV2/hero/player_attack_right_1.png"));
	            	}
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
	                
	                
	                
	        
	                int nextBottomY = nextY + this.panel.tileSize - marge; // Calcul de la position du bas avec la marge
	      

	
	                //boolean collision = this.panel.collisionMur(upperY, leftX) || this.panel.collisionMur(upperY, rightX);
	                // Vérifiez si les quatre coins sont libres
	               boolean collision = this.panel.collisionMur(nextY, leftX) || this.panel.collisionMur(nextY, rightX)
	                	|| this.panel.collisionMur(nextBottomY, leftX) || this.panel.collisionMur(nextBottomY, rightX);
	                if (this.imageView.getY()<=0) {
	                	collision = true;
	                }
	
	                
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
	                    	if (!this.panel.isPaused) {
	                    		if(this.last_marche!=marche.Up1) {
	                    			//this.drawPersonnage("file:src/res/imagesV2/hero/PU1.png");
	                    			this.imageView.setImage(new Image("file:src/res/imagesV2/hero/PU1.png"));
	                    			this.deplacerHaut();
	                    			this.last_marche = marche.Up1;
	                    		}else if (this.last_marche != marche.Up0) {
	                    			//this.drawPersonnage("file:src/res/imagesV2/hero/PU2.png");
	                    			this.imageView.setImage(new Image("file:src/res/imagesV2/hero/PU2.png"));
	                    			this.deplacerHaut();
	                    			this.last_marche = marche.Up0;
	                    		}
	                    		
	                    	}
	                        
	                        
		                        //POTION
		                        if (this.detectionPotion((int) this.imageView.getX(), (int) this.imageView.getY())) {
		                        	this.startPickUpMusic();
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
		                       
		                        //CLE
		                        if (this.detectionCle((int) this.imageView.getX(), (int) this.imageView.getY())) {
		                        	this.gotKey = true;
		                        }
		                        
		                        //TRESOR
		                        if (this.detectionVictoire((int) this.imageView.getX(), (int) this.imageView.getY())) {
		                        	this.startPickUpMusic();
	                            	System.out.println("victoire du heros");
	                                this.panel.isPaused = true;
	                                this.panel.getChildren().add(this.panel.pauseText);
	                    
	                                this.panel.montrerMessage("Vous avez gagné",2);
	                            }
		                        if (this.detectionPassage((int) this.imageView.getX(), (int) this.imageView.getY())) {
		                        	this.startPickUpMusic();
	                            	System.out.println("passage partie suivante");
	                                this.panel.isPaused = true;
	                                this.panel.getChildren().add(this.panel.pauseText);
	                    
	                                this.panel.montrerMessage("!!!!!! Next level !!!!",1);
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
	                
	                if (this.imageView.getY() >= this.panel.screenHeight) {
	                	collision = true;
	                }
	                
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
	                    	if (!this.panel.isPaused) {
	                    		if(this.last_marche!=marche.Down1) {
	                    			//this.drawPersonnage("file:src/res/imagesV2/hero/PD1.png");
	                    			this.last_marche = marche.Down1;
	                    			this.imageView.setImage(new Image("file:src/res/imagesV2/hero/PD1.png"));
	                    			this.deplacerBas();
	                    		}else if (this.last_marche != marche.Down0) {
	                    			//this.drawPersonnage("file:src/res/imagesV2/hero/PD2.png");
	                    			this.imageView.setImage(new Image("file:src/res/imagesV2/hero/PD2.png"));
	                    			this.last_marche = marche.Down0;
	                    			this.deplacerBas();
	                    		}
	                    	}
 
	                      //POTION
	                        if (this.detectionPotion((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	this.startPickUpMusic();
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
		                    //CLE
	                        if (this.detectionCle((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	this.gotKey = true;
	                        }
		                        
		                    //TRESOR
	                        if (this.detectionVictoire((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	this.startPickUpMusic();
                            	System.out.println("victoire du heros");
	                        	this.panel.isPaused = true;
	                        	this.panel.getChildren().add(this.panel.pauseText);
	                        	 this.panel.montrerMessage("Vous avez gagné",2);
	                        }
	                        if (this.detectionPassage((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	this.startPickUpMusic();
                            	System.out.println("passage partie suivante");
                                this.panel.isPaused = true;
                                this.panel.getChildren().add(this.panel.pauseText);
                    
                                this.panel.montrerMessage("!!!!!! Next level !!!!",1);
                            }
	                    }
	                     
	                    

	                    this.last_dir = direction.DOWN;
	                }
	                break;
	            }
  
	            case LEFT: {
	            	int marge = this.panel.tileSize / 10;
	                int nextX = (int) this.imageView.getX() + marge; // Déplacement vers la droite
	                int upperY = (int) this.imageView.getY() + marge;
	                int lowerY = upperY + this.panel.tileSize - marge * 2;
	                int rightX = nextX + this.panel.tileSize - marge;

	                //boolean collision = this.panel.collisionMur(upperY, rightX) || this.panel.collisionMur(lowerY, rightX);
	            	
	     
	                int leftX = nextX - marge;

	                boolean collision = this.panel.collisionMur(upperY, leftX) || this.panel.collisionMur(lowerY, leftX);

	                
	             // Convertir les coordonnées en pixels en coordonnées de grille
	                int caseX = (int) this.imageView.getX() / this.panel.tileSize;
	                int caseY = (int) this.imageView.getY() / this.panel.tileSize;
	             // Accéder à la case actuelle
	                Case caseActuelle = this.panel.labyrinthe.getCase(caseX, caseY);

	                if (this.imageView.getX()<=0) {
	                	collision = true;
	                }
	                
	                
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
	                    	if (!this.panel.isPaused) {
	                    		if(this.last_marche!=marche.Left1) {
	                    			//this.drawPersonnage("file:src/res/imagesV2/hero/PD1.png");
	                    			this.last_marche = marche.Left1;
	                    			this.imageView.setImage(new Image("file:src/res/imagesV2/hero/PL1.png"));
	                    			this.deplacerGauche();
	                    		}else if (this.last_marche != marche.Left0) {
	                    			//this.drawPersonnage("file:src/res/imagesV2/hero/PD2.png");
	                    			this.imageView.setImage(new Image("file:src/res/imagesV2/hero/PL2.png"));
	                    			this.last_marche = marche.Left0;
	                    			this.deplacerGauche();
	                    		}
	                
	                    	}

	                      
	                      //POTION
	                        if (this.detectionPotion((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	this.startPickUpMusic();
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
	                        //CLE
	                        if (this.detectionCle((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	this.gotKey = true;
	                        }
	                        
	                        //TRESOR
                            if (this.detectionVictoire((int) this.imageView.getX(), (int) this.imageView.getY())) {
                            	this.startPickUpMusic();
                            	System.out.println("victoire du heros");
                                this.panel.isPaused = true;
                                this.panel.getChildren().add(this.panel.pauseText);
                                this.panel.montrerMessage("Vous avez gagné",2);
                            }
                            if (this.detectionPassage((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	this.startPickUpMusic();
                            	System.out.println("passage partie suivante");
                                this.panel.isPaused = true;
                                this.panel.getChildren().add(this.panel.pauseText);
                    
                                this.panel.montrerMessage("!!!!!! Next level !!!!",1);
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
	                    	if (!this.panel.isPaused) {
	                    		if(this.last_marche!=marche.Right1) {
	                    			//this.drawPersonnage("file:src/res/imagesV2/hero/PD1.png");
	                    			this.last_marche = marche.Right1;
	                    			this.imageView.setImage(new Image("file:src/res/imagesV2/hero/PR1.png"));
	                    			this.deplacerDroite();
	                    		}else if (this.last_marche != marche.Right0) {
	                    			//this.drawPersonnage("file:src/res/imagesV2/hero/PD2.png");
	                    			this.imageView.setImage(new Image("file:src/res/imagesV2/hero/PR2.png"));
	                    			this.last_marche = marche.Right0;
	                    			this.deplacerDroite();
	                    		}
	                    	}

	                        
	                        
	                      //POTION
	                        if (this.detectionPotion((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	this.startPickUpMusic();
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
	                        
	                        //CLE
	                        if (this.detectionCle((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	this.gotKey = true;
	                        }
	                        
	                        //TRESOR
                            if (this.detectionVictoire((int) this.imageView.getX(), (int) this.imageView.getY())) {
                            	this.startPickUpMusic();
                            	System.out.println("victoire du heros");
                                this.panel.isPaused = true;
                                this.panel.getChildren().add(this.panel.pauseText);
                                this.panel.montrerMessage("Vous avez gagné",2);
                            }
                            if (this.detectionPassage((int) this.imageView.getX(), (int) this.imageView.getY())) {
	                        	this.startPickUpMusic();
                            	System.out.println("passage partie suivante");
                                this.panel.isPaused = true;
                                this.panel.getChildren().add(this.panel.pauseText);
                    
                                this.panel.montrerMessage("!!!!!! Next level !!!!",1);
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
