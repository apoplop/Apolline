package test1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test1.GamePanel.typeCase;

public class CasePotionProtection extends Case{

	public CasePotionProtection() {
		super(typeCase.POTION_PROTECTION);
		this.image = new Image("file:src/res/images/potion_protection.png");
		this.imageView = new ImageView(this.image);
	}

}
