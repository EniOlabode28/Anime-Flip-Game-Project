/*
 * name: enioluwa olabode
 * class: comp 167 - introduction to programming
 * date: april 23, 2025
 * description: this class represents a single card in the anime flip game.
 *              it handles the card's visual state, image loading, flipping behavior,
 *              and whether it has been matched.
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Card extends StackPane {

    private boolean flipped;
    private boolean matched;
    private String path;
    private Image image;
    private ImageView imageView;

    // static image shared across all cards for the card back
    public static final Image backImage = new Image(Card.class.getResourceAsStream("/images/imagesBack.jpg"));

    // no-arg constructor
    public Card() {
        this("");
        this.flipped = false;
        this.matched = false;
        this.path = "";
        this.image = null;
        this.imageView = new ImageView(image);
    }

    // constructor with image path
    public Card(String path) {
        this.flipped = false;
        this.matched = false;
        this.path = path;
        this.image = null;
        this.imageView = new ImageView(image);
        setPath(path);
    }

    // flips the card to show the front image or the back depending on state
    public void flipCard() {
        this.getChildren().clear();
        if (flipped && imageView != null) {
            this.getChildren().add(imageView); // show front of the card
        } else {
            ImageView backImageView = new ImageView(backImage); // show back of the card
            backImageView.setFitWidth(64); // default card width
            backImageView.setFitHeight(64); // default card height
            this.getChildren().add(backImageView);
        }
    }

    // sets both the card's layout size and the image dimensions
    public void setCardAndImageSize(int width, int height) {
        this.setPrefSize(width, height);
        if (imageView != null) {
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
        }
    }

    // marks the card as matched and visually removes the image
    public void setMatched() {
        matched = true;
        this.getChildren().clear();
        this.setStyle("-fx-background-color: black;");
    }

    // getters and setters
    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public boolean isMatched() {
        return matched;
    }

    public String getPath() {
        return path;
    }

    // sets the image path, loads the image, and creates the image view
    public void setPath(String path) {
        this.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: white;");
        this.path = path;
        try {
            var imageUrl = getClass().getResource("/" + path);
            if (imageUrl != null) {
                this.image = new Image(imageUrl.toString(), false); // disable caching
                this.imageView = new ImageView(this.image);
                this.imageView.setPreserveRatio(true);
                this.imageView.setFitWidth(64);
                this.imageView.setFitHeight(64);
            } else {
                System.err.println("error loading image: " + path);
            }
        } catch (Exception e) {
            System.err.println("error loading image: " + path);
        }
    }
}
