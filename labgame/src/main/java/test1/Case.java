package test1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test1.GamePanel.typeCase;

public class Case {
	private typeCase typeCase;
	protected Image image;
	protected ImageView imageView;
    private int compteurCase;

	public Case (typeCase type) {
		this.typeCase = type;
	}
	
	
	
	public typeCase getTypeCase() {
		return this.typeCase;
	}
	@SuppressWarnings("exports")
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
