package test1;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test1.GamePanel.typeCase;
public class CasePorte extends Case{

	public CasePorte() {
		super(typeCase.PORTE);
		this.image = new Image("file:src/res/imagesV2/objets/Doors.png");
		this.imageView = new ImageView(this.image);
	}

}
