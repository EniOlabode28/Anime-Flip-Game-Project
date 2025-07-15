/*
 * name: enioluwa olabode
 * class: comp 167
 * date: april 23, 2025
 * description: cardgridpane sets up and manages the grid of cards used in the anime flip memory game.
 *              it handles layout, image assignment, shuffling, and level-based resizing of the grid.
 */

import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.Collections;

public class CardGridPane extends GridPane {

    private Card[][] cards;
    private ArrayList<String> cardList;
    private final int MAXROWS = 8;
    private final int MAXCOLS = 8;
    private int currentRows = 8;
    private int currentCols = 8;
    private int cardSize = 64;

    // no-arg constructor (default 64x64 cards)
    public CardGridPane() {
        this(64);
    }

    // constructor with custom card size
    public CardGridPane(int cardSize) {
        this.cardSize = cardSize;
        cards = new Card[MAXROWS][MAXCOLS];
        cardList = new ArrayList<>();
        initGrid();
    }

    // initialize 8x8 grid of card objects and add them to the pane
    private void initGrid() {
        for (int i = 0; i < MAXROWS; i++) {
            for (int j = 0; j < MAXCOLS; j++) {
                Card card = new Card();
                card.setCardAndImageSize(cardSize, cardSize);
                cards[i][j] = card;
                this.add(card, j, i); // add card at column (j), row (i)
            }
        }
    }

    // fill the cardlist with image paths (each image added twice)
    public void createCardImageList(int size) {
        cardList.clear();
        int counter = 0;
        for (int i = 0; i < size / 2; i++) {
            cardList.add("images/image_" + counter + ".jpg");
            cardList.add("images/image_" + counter + ".jpg");
            counter++;
        }
    }

    // shuffle the card images
    public void shuffleImages() {
        Collections.shuffle(cardList);
    }

    // assign image paths to cards and flip them to face-up
    public void setCardImages() {
        int index = 0;
        for (int i = 0; i < currentRows; i++) {
            for (int j = 0; j < currentCols; j++) {
                Card card = cards[i][j];
                String path = cardList.get(index++);
                card.setPath(path);
                card.setFlipped(true);
                card.flipCard();
            }
        }
    }

    // return the card at a specific row and column
    public Card getCard(int r, int c) {
        return cards[r][c];
    }

    // initialize the cards for the selected level (grid size)
    public void initCards(int rows, int cols) {
        this.currentRows = rows;
        this.currentCols = cols;
        int totalCards = rows * cols;

        createCardImageList(totalCards);
        shuffleImages();

        int index = 0;
        for (int i = 0; i < MAXROWS; i++) {
            for (int j = 0; j < MAXCOLS; j++) {
                Card card = cards[i][j];

                if (i < currentRows && j < currentCols) {
                    String path = cardList.get(index++);
                    card.setPath(path);
                    card.setFlipped(false);
                    card.flipCard();
                    card.setDisable(false);
                    card.setStyle("");
                } else {
                    card.setDisable(true);
                    card.getChildren().clear();
                    card.setStyle("-fx-background-color: white;");
                }
            }
        }
    }

    // setters and getters for current grid dimensions
    public void setCurrentRows(int rows) {
        this.currentRows = rows;
    }

    public void setCurrentCols(int cols) {
        this.currentCols = cols;
    }

    public int getCurrentRows() {
        return currentRows;
    }

    public int getCurrentCols() {
        return currentCols;
    }
}
