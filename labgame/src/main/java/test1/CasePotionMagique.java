package test1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test1.GamePanel.typeCase;

public class CasePotionMagique extends Case{

	public CasePotionMagique() {
		super(typeCase.POTION_MAGIQUE);
		this.image = new Image("file:src/res/images/potion_magique.png");
		this.imageView = new ImageView(this.image);
	}

}
