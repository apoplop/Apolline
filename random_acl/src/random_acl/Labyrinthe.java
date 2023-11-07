package random_acl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Labyrinthe {
	
	GamePanel panel;
	String mapTable[][];
	
	public Labyrinthe(GamePanel panel, String mapName) {
		this.panel = panel;
		mapTable = mapLoader(mapName);
		drawMap(mapTable);
	}
	
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
	                this.panel.grid.add(c.getImageView(), j, i);
	        	}
	        }
		   this.panel.getChildren().addAll(this.panel.grid);
	 }

}
