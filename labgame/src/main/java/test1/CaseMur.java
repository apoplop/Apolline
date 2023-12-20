package test1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test1.GamePanel.typeCase;

public class CaseMur extends Case{

	public CaseMur() {
		super(typeCase.MUR);
		this.image = new Image("file:src/res/images/wall.png");
		this.imageView = new ImageView(this.image);
	}

}
