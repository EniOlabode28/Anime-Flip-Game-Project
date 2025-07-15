/*
 * Name: Enioluwa Olabode
 * Class: COMP 167 - Introduction to Programming
 * Date: April 23, 2025
 * Description: This is the entry point of the Anime Flip Game application.
 *              It initializes and displays the main game window using JavaFX.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * The start method sets up the primary stage and displays the game UI.
     * This is automatically called by the JavaFX runtime after launch().
     */
    @Override
    public void start(Stage primaryStage) {
        GamePane gamePane = new GamePane(); // Create the main game UI (8x8 grid by default)
        Scene scene = new Scene(gamePane, 600, 700); // Set the scene size (width x height)

        primaryStage.setTitle("Anime Flip Game"); // Set the title of the game window
        primaryStage.setScene(scene); // Attach the gamePane to the scene
        primaryStage.show(); // Display the stage (game window)
    }

    /**
     * Main method — launches the JavaFX application.
     */
    public static void main(String[] args) {
        launch(args); // Start the JavaFX application thread
    }
}
 ;