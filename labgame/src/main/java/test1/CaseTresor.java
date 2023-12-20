package test1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test1.GamePanel.typeCase;
public class CaseTresor extends Case{

	public CaseTresor() {
		super(typeCase.TRESOR);
		this.image = new Image("file:src/res/images/tresor.png");
		this.imageView = new ImageView(this.image);
	}

}
