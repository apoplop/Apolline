package test1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;

public class Labyrinthe {
	
	GamePanel panel;
	String mapTable[][];
    private Case[][] cases; //stockage des instances de cases
	@SuppressWarnings("exports")
	public GridPane grid = new GridPane();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList<Monstre> listeMonstre = new ArrayList();
	Hero hero;
	
	
	@SuppressWarnings("rawtypes")
	ArrayList cordonneeCase = new ArrayList();

//    public Case getCase(int x, int y) {
//        for (Node node : grid.getChildren()) {
//            // Vérifiez si le Node est une instance de ImageView
//            if (node instanceof ImageView) {
//                // Vérifiez si les coordonnées de la grille correspondent
//                if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
//                    // Trouvé la Case correspondante, créez et renvoyez une instance de Case
//                    String typeCase = mapTable[y][x];
//                    return new Case(typeCase);
//                }
//            }
//        }
//        return null; // Si aucune Case n'est trouvée aux coordonnées spécifiées
//    }
	
    public Case getCase(int x, int y) {
        return cases[y][x]; // Retournez l'instance de Case stockée
    }
    
	
	public Labyrinthe(GamePanel panel, String mapName) {
		this.panel = panel;
		mapTable = mapLoader(mapName);
		cases = new Case[mapTable.length][mapTable[0].length]; // Initialisez le tableau de Case
	    drawMap(mapTable);
	}
	


	 @SuppressWarnings("resource")
	public static String[][] mapLoader (String mapName){
	    	int ligne = 0;
	    	int colonne = 0;
	    	String mapTable[][] = null;
	    	try {
	            // objet FileReader pour ouvrir le fichier
	            FileReader lecteurFichier = new FileReader(mapName);
	            // objet BufferedReader pour lire le fichier plus efficacement
	            BufferedReader lecteurBuffer = new BufferedReader(lecteurFichier);

	            String ligneF;
	            // Lire le fichier ligne par ligne jusqu'à la fin
	            while ((ligneF = lecteurBuffer.readLine()) != null) {
	            	ligne++;
	            	String[] col = ligneF.split(" ");
	            	colonne = col.length;
	            }
	            
	            mapTable = new String[ligne][colonne];
	            // Fermer les flux
	            lecteurBuffer.close();
	            lecteurFichier.close();
	            
	            // Réinitialiser le lecteur
	            FileReader lecteurF = new FileReader(mapName);
	            //objet BufferedReader pour lire le fichier plus efficacement
	            BufferedReader lecteurB = new BufferedReader(lecteurF);
	            String lig;
	            int i =0;
	            // Lisez le fichier ligne par ligne jusqu'à la fin
	            while ((lig = lecteurB.readLine()) != null) {
	            	String[] c = lig.split(" ");
	            	mapTable[i] = c;
	            	i++;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	return mapTable ;
	    }
	 
	 public void drawMap (String[][] mapTable) {
			  for (int i=0; i<mapTable.length;i++) {
		        	for (int j=0; j<mapTable[i].length;j++) {
		        		Case c = new Case(mapTable[i][j]);
		                c.setImagePixel(this.panel.tileSize);
		                c.setPosi(i, j);
		                this.grid.add(c.getImageView(), j, i);
		                cases[i][j] = c; // Stockez l'instance de Case dans le tableau (pour le compteur de case)

		        	}
		        }
			   this.panel.getChildren().add(this.grid);
	       	
	 }
	 
	 public void ajoutMonstre(Monstre m) {
		 this.listeMonstre.add(m);
	 }
	 public void supprimerMonstre(Monstre m) {
		 this.listeMonstre.remove(m);
		 this.panel.getChildren().remove(m.imageView);  
	}
	 public void supprimerCase(int x, int y) {
			this.mapTable[y][x] = "0";
			Case c = new Case("0");
		c.setImagePixel(this.panel.tileSize);
		 
		// Parcourir les enfants du GridPane pour trouver la case correspondante
		    for (Node node : this.grid.getChildren()) {
		        if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
		            // Assurez-vous que le Node est une instance de ImageView
		            if (node instanceof ImageView) {
		                ImageView imageView = (ImageView) node;
		                Image imageHerbe = new Image("file:src/res/images/grass.png");
		                imageView.setImage(imageHerbe); // Mettre à jour l'image
		                break; // Sortir de la boucle une fois la case trouvée et mise à jour
		            }
		        }
		    }

	}


}
