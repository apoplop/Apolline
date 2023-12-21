package test1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test1.GamePanel.typeCase;
public class CaseCle extends Case{

	public CaseCle() {
		super(typeCase.CLE);
		this.image = new Image("file:src/res/imagesV2/objets/Key.png");
		this.imageView = new ImageView(this.image);
	}

}
