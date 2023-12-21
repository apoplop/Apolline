package test1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private void startGame(Stage primaryStage, int niveau, String nomJoueur) {
    	VBox rootLayout = new VBox(); // Conteneur principal pour les deux parties
    	GridPane grid = new GridPane();
    	
    	GamePanel game = new GamePanel(nomJoueur,niveau-1); // Création du panneau de jeu
    	
    	Label infoJoueur = new Label("Nom joueur: "+nomJoueur);
    	infoJoueur.setFont(new Font("Arial", 24));

    	grid.add(game.paneInfo, 0, 0);
    	grid.add(infoJoueur, 1, 0);
    	grid.setHgap(100);
    	
        
        // Créer la seconde partie de la scène (par exemple, une barre d'informations)
        rootLayout.getChildren().addAll(game,grid); // Ajouter la barre d'informations au layout principal
        Scene mainScene = new Scene(rootLayout); // Créer la scène principale avec le VBox
        primaryStage.setScene(mainScene);
        game.requestFocus();
        
        game.startGameThread();
        
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

        
        
        
    
    private Scene createWelcomeScene(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        PlayerDataManager manager = new PlayerDataManager();
        

        // Champ de saisie pour le nom de l'utilisateur
        TextField nameField = new TextField();
        nameField.setPromptText("Entrez votre nom");

        Text welcomeText = new Text("Bienvenue sur le jeu de labyrinthe !\nVeuillez entrer votre nom et sélectionner un niveau...");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Bouton pour démarrer le jeu
        Button startGameButton = new Button("Démarrer le jeu");
        startGameButton.setOnAction(e -> {
            String playerName = nameField.getText(); // Récupérer le nom de l'utilisateur
            if (!playerName.trim().isEmpty()) {
            	int level = manager.getPlayerLevel(playerName); // Obtenez le niveau du joueur ou 1 s'il est nouveau
            	manager.savePlayerData(playerName, level); // Enregistrez ou mettez à jour le joueur
                startGame(primaryStage, level, playerName); // Démarrer le jeu avec le nom de l'utilisateur
            } else {
                // Afficher un message d'erreur ou demander à l'utilisateur d'entrer son nom
                System.out.println("Veuillez entrer votre nom.");
            }
        });

        Button exitButton = new Button("Quitter");
        exitButton.setOnAction(e -> Platform.exit());

        layout.getChildren().addAll(welcomeText, nameField, startGameButton, exitButton);

        return new Scene(layout, 500, 300);
    }

    


    public static void main(String[] args) {
        launch(args);
    }

}
