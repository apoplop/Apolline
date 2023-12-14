package test1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Case {
	private String typeCase;
	private Image image;
	private ImageView imageView;
    private int compteurCase;

	public Case (String typeCase) {
		this.typeCase = typeCase;
			
		switch(typeCase) {
		case "0": {
			this.image = new Image("file:src/res/images/grass.png");
			this.imageView = new ImageView(this.image);
			break;
		}
		case "1": {
			this.image = new Image("file:src/res/images/wall.png");
			this.imageView = new ImageView(this.image);
			break;
		}
		case "2": {
			this.image = new Image("file:src/res/images/potion_magique.png");
			this.imageView = new ImageView(this.image);
			break;
		}
		case "3": {
			this.image = new Image("file:src/res/images/potion_vie.png");
			this.imageView = new ImageView(this.image);
			break;
		}
		case "4": {
			this.image = new Image("file:src/res/images/potion_protection.png");
			this.imageView = new ImageView(this.image);
			break;
		}
		case "5": {
			this.image = new Image("file:src/res/images/piege_feu.png");
			this.imageView = new ImageView(this.image);
			break;
		}
		case "*": {
			this.image = new Image("file:src/res/images/tresor.png");
			this.imageView = new ImageView(this.image);
			break;
		}
		default:{
			this.image = new Image("file:src/res/images/grass.png");
			this.imageView = new ImageView(this.image);
			break;
		}	
		}	
	}
	
	
	
	public String getTypeCase() {
		return this.typeCase;
	}
	public ImageView getImageView() {
		return this.imageView;
	}
	public void setPosi(int x, int y) {
		this.imageView.setX(x);
		this.imageView.setY(y);
	}
	public void setImagePixel(int pixel_size) {
		this.imageView.setFitWidth(pixel_size);
        this.imageView.setFitHeight(pixel_size);
	}
	
	
	//Incrementer le compteur pour supprimer les potions après 3 utilisations
    public int getCompteurCase() {
        return compteurCase;
    }
    public void setCompteurCase(int compteurCase) {
        this.compteurCase = compteurCase;
    }
    public void incrementerCompteurCase() {
        this.compteurCase = this.compteurCase +1;
    }
	
	
	
	
}
