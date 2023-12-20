package test1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test1.GamePanel.typeCase;

public class CasePiegeFeu extends Case {

	public CasePiegeFeu() {
		super(typeCase.FEU);
		this.image = new Image("file:src/res/images/piege_feu.png");
		this.imageView = new ImageView(this.image);
	}

}
