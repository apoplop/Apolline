package test1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;

public class main extends Application {
    public void start(Stage primaryStage) {
        Scene welcomeScene = createWelcomeScene(primaryStage);
        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("Labyrinthe");
        primaryStage.show();
    }

    private void startGame(Stage primaryStage, String mapName) {
        GamePanel game = new GamePanel(mapName); // Passez le nom de la carte sélectionnée
        primaryStage.setScene(game.scene);
        game.startGameThread();
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }
    private Scene createWelcomeScene(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Text welcomeText = new Text("Bienvenue sur le jeu de labyrinthe ! \n Veuillez selectioner un niveau...");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Button map1Button = new Button("Niveau 1 (Carte 16x12)");
        map1Button.setOnAction(e -> startGame(primaryStage, "src/res/maps/map02.txt")); // Nom de la première carte

        Button map2Button = new Button("Niveau 2 (Carte 21x15)");
        map2Button.setOnAction(e -> startGame(primaryStage, "src/res/maps/map03.txt")); // Nom de la deuxième carte

        Button exitButton = new Button("Quitter");
        exitButton.setOnAction(e -> Platform.exit());

        layout.getChildren().addAll(welcomeText, map1Button, map2Button, exitButton);

        return new Scene(layout, 500, 300);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
