package test1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test1.GamePanel.typeCase;

public class CaseTerre extends Case{

	public CaseTerre() {
		super(typeCase.TERRE);
		this.image = new Image("file:src/res/images/grass.png");
		this.imageView = new ImageView(this.image);
	}

}
