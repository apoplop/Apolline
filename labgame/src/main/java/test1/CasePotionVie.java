package test1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test1.GamePanel.typeCase;

public class CasePotionVie extends Case{

	public CasePotionVie() {
		super(typeCase.POTION_VIE);
		this.image = new Image("file:src/res/images/potion_vie.png");
		this.imageView = new ImageView(this.image);
	}

}
