package test1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
	 public void start(Stage primaryStage) {
		 	GamePanel game = new GamePanel();
	        primaryStage.setTitle("Labyrinthe Simple");
	        primaryStage.setScene(game.scene);
	        primaryStage.setResizable(false);
	        game.startGameThread();
	        primaryStage.setOnCloseRequest(event -> {
	            // Arrêtez les opérations en cours, fermez les threads, etc.
	            Platform.exit(); // Cela ferme l'application JavaFX
	            System.exit(0); // Cela termine complètement le programme
	        });
	        primaryStage.show();
	       
	       
	        }
	 public static void main(String[] args) {
	        launch(args);
	    }
}
