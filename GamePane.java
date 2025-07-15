/*
 * Name: Enioluwa Olabode
 * Class: COMP 167
 * Date: April 23, 2025
 * Description: GamePane class that sets up and controls the logic for the Anime Flip Memory Card Game.
 *              Includes UI controls, game board
*/
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.control.Label;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
//propeties of the class
public class GamePane extends BorderPane {

    private CardGridPane cardGridPane;
    private HBox commandPane;
    private Button exitButton;
    private Button newGameButton;
    private ComboBox<String> levelSelector;
    private AudioClip matchSound;
    private AudioClip noMatchSound;
    private int turnCount = 0;
    private Label turnLabel;


    private int rows = 8;
    private int cols = 8;

    private int numClicks = 0;
    private Card clickedCardOne = null;
    private Card clickedCardTwo = null;

    public GamePane() {
        this(64);
    }

    public GamePane(int cardSize) {
        cardGridPane = new CardGridPane(cardSize);
        //loading sound audio clips for clicking
        matchSound = new AudioClip(getClass().getResource("/sounds/anime.mp3").toExternalForm());
        noMatchSound = new AudioClip(getClass().getResource("/sounds/lasers.mp3").toExternalForm());
        turnLabel = new Label("Turns: 0");
        HBox topPane = new HBox(10, turnLabel);
        topPane.setPadding(new Insets(10));
        this.setTop(topPane);





        cardGridPane.initCards(rows, cols);
        addCardListeners();

        commandPane = new HBox();
        commandPane.setPadding(new Insets(10));
        commandPane.setSpacing(10);
        //various level selecters each level based on diffrent rows and columns
        levelSelector = new ComboBox<>(FXCollections.observableArrayList(
                "Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6"
        ));
        levelSelector.setValue("Level 6");

        levelSelector.setOnAction(e -> {
            String selected = levelSelector.getValue();
//if statements to ensure the rows and colums work
            if (selected.equals("Level 1")) { rows = 2; cols = 3; }
            else if (selected.equals("Level 2")) { rows = 2; cols = 4; }
            else if (selected.equals("Level 3")) { rows = 4; cols = 4; }
            else if (selected.equals("Level 4")) { rows = 4; cols = 6; }
            else if (selected.equals("Level 5")) { rows = 6; cols = 6; }
            else if (selected.equals("Level 6")) { rows = 8; cols = 8; }
//initalizing cards and listeners
            cardGridPane.initCards(rows, cols);
            addCardListeners();
        });
//command button and dropdown button
        newGameButton = new Button("New Game");
        newGameButton.setOnAction(e -> {
            cardGridPane.initCards(rows, cols);
            addCardListeners();
        });

        exitButton = new Button("Exit");
        exitButton.setOnAction(e -> System.exit(0));
//change board size based on level
        commandPane.getChildren().addAll(levelSelector, newGameButton, exitButton, turnLabel);
        this.setTop(commandPane);


        this.setTop(commandPane);
        this.setCenter(cardGridPane);

    }
//new game
    private void addCardListeners() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Card card = cardGridPane.getCard(i, j);

                if (!card.isDisabled()) {
                    card.setOnMousePressed(e -> {
                        if (card.isFlipped() || card.isMatched() || numClicks == 2) return;

                        card.setFlipped(true);
                        card.flipCard();

                        if (numClicks == 0) {
                            clickedCardOne = card;
                            numClicks = 1;
                        } else {
                            clickedCardTwo = card;
                            numClicks = 2;
//animation for flipping the cards
                            new javafx.animation.AnimationTimer() {
                                private long start = System.nanoTime();
                                @Override
                                public void handle(long now) {
                                    if (now - start >= 800_000_000) {
                                        if (clickedCardOne.getPath().equals(clickedCardTwo.getPath())) {
                                            if (matchSound != null) matchSound.play(); // ✅ Play match sound
                                            clickedCardOne.setMatched();
                                            clickedCardTwo.setMatched();
                                        } else {
                                            if (noMatchSound != null) noMatchSound.play(); // ✅ Play no-match sound
                                            clickedCardOne.setFlipped(false);
                                            clickedCardTwo.setFlipped(false);
                                            clickedCardOne.flipCard();
                                            clickedCardTwo.flipCard();
                                        }
//if statements to makesure match v no match

                                        clickedCardOne = null;
                                        clickedCardTwo = null;
                                        numClicks = 0;
                                        turnCount++;
                                        turnLabel.setText("Turns: " + turnCount);

// Check all cards matched
                                        int matchedCount = 0;
                                        for (int i = 0; i < cardGridPane.getCurrentRows(); i++) {
                                            for (int j = 0; j < cardGridPane.getCurrentCols(); j++) {
                                                if (cardGridPane.getCard(i, j).isMatched()) {
                                                    matchedCount++;
                                                }
                                            }
                                        }
//add mouse event
                                        int totalCards = cardGridPane.getCurrentRows() * cardGridPane.getCurrentCols();
                                        if (matchedCount == totalCards) {
                                            checkGameOver(); // ✅ Call the alert
                                        }


                                        stop();


                                    }
                                }
                            }.start();
                        }
                    });
                }
            }
        }
    }
    private void checkGameOver() {
        int matchedCount = 0;
        int totalCards = cardGridPane.getCurrentRows() * cardGridPane.getCurrentCols();

        for (int i = 0; i < cardGridPane.getCurrentRows(); i++) {
            for (int j = 0; j < cardGridPane.getCurrentCols(); j++) {
                Card c = cardGridPane.getCard(i, j);
                if (c != null && c.isMatched()) {
                    matchedCount++;
                }
            }
        }

        System.out.println("Matched: " + matchedCount + " / " + totalCards);
//information alert game over button
        if (matchedCount == totalCards) {
            javafx.application.Platform.runLater(() -> {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("Game complete in " + turnCount + " turns!");
                alert.show();
            });
        }
    }

//getters and setters encasulated
    public Button getExitButton() {
        return exitButton;
    }

    public CardGridPane getCardGridPane() {
        return cardGridPane;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}