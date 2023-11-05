package test1;

import javafx.application.Application;
import javafx.stage.Stage;

public class main extends Application {
	 public void start(Stage primaryStage) {
		 	GamePanel game = new GamePanel();
			Labyrinthe labyrinthe = new Labyrinthe(game, "src/utils/map02.txt");
	        primaryStage.setTitle("Labyrinthe Simple");
	        primaryStage.setScene(game.scene);
	        primaryStage.setResizable(false);
	        primaryStage.show();
	        game.startGameThread();
	        }
	 public static void main(String[] args) {
	        launch(args);
	        
	    }

}
