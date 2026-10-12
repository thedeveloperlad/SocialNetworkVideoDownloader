import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ContentPanel extends GridPane {

    String userHome = System.getProperty("user.home");
    File file = new File(userHome + "/Downloads/HR8eJuVb0AAIs7S.jfif");
    String imageUrl = file.toURI().toString();
    Image image = new Image(imageUrl);
    // Image image = new Image(getClass().getResourceAsStream("/images/logo.png"));
    Label name = new Label("Name");
    Label description = new Label("Description");
    Label title = new Label("Title");
    Label thumbnailUrl = new Label("Image URL");
    ImageView imageView = new ImageView(image);

    ContentPanel() {
        // HBox contentRow = new HBox(10);
        VBox contentRow = new VBox(10);
        GridBagConstraints gbc = new GridBagConstraints();
        // GridPane innerGrid = new GridPane();

        // Label paneTitle = new Label(" - Input - ");
        // paneTitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        contentRow.setPadding(new Insets(8, 20, 10, 20));
        contentRow.setAlignment(Pos.CENTER);
        // contentRow.setFillWidth(true);

        /*BorderStroke stroke = new BorderStroke(
                Color.DARKGREY,
                BorderStrokeStyle.DASHED,
                CornerRadii.EMPTY,
                new BorderWidths(3)
        );

        contentRow.setBorder(new Border(stroke));*/

        imageView.setFitWidth(300);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true); // Maintain layout proportions
        imageView.setSmooth(true);

        // inputLabel.setMaxWidth(Double.MAX_VALUE);
        // inputTextField.setMaxWidth(280);
        // HBox.setHgrow(inputTextField, Priority.SOMETIMES);
        // submitButton.setMaxWidth(Double.MAX_VALUE);

        // submitButton.setOnAction(this::submitButtonClick);

        // contentRow.getChildren().addAll(name, description, title, thumbnailUrl, imageView);

        contentRow.getChildren().addAll(name);
        contentRow.getChildren().addAll(description);
        // contentRow.getChildren().addAll(title);
        contentRow.getChildren().addAll(thumbnailUrl);

        contentRow.getChildren().addAll(imageView);

        // setName(fileStructure.getName());

        Label paneTitle = new Label(" Video - Image "); // Spaces prevent line collision
        paneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        paneTitle.setStyle("-fx-background-color: -fx-background;");

        StackPane fieldset = new StackPane();
        fieldset.setStyle("-fx-border-color: #b0b0b0; -fx-border-width: 1px; -fx-border-radius: 3px;");
        fieldset.getChildren().addAll(contentRow, paneTitle);

        StackPane.setAlignment(paneTitle, Pos.TOP_LEFT);
        StackPane.setMargin(paneTitle, new Insets(-9, 0, 0, 15));

        this.setMaxWidth(Double.MAX_VALUE);
        this.setPadding(new Insets(15));

        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setHgrow(Priority.ALWAYS);
        colConstraints.setFillWidth(true);
        this.getColumnConstraints().add(colConstraints);

        // innerGrid.setMaxWidth(Double.MAX_VALUE);
        this.add(fieldset, 0,0);
    }

    public void setImage(String imageUrl){
        // sFile file = new File(imageUrl);
        Image image = new Image(imageUrl);
        imageView.setImage(image);
    }

    public void setName(String text){
        name.setText(text);
    }

    public void setDescription(String text){
        description.setText(text);
    }

    public void setTitle(String text){
        title.setText(text);
    }

    public void setThumbnailUrl(String text){
        thumbnailUrl.setText(text);
    }

    private GridPane videoPane() {
        HBox contentRow = new HBox(10);
        GridPane innerGrid = new GridPane();

        // Label paneTitle = new Label(" - Input - ");
        // paneTitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        contentRow.setPadding(new Insets(8, 20, 10, 20));
        contentRow.setAlignment(Pos.CENTER);
        // contentRow.setFillWidth(true);

        /*BorderStroke stroke = new BorderStroke(
                Color.DARKGREY,
                BorderStrokeStyle.DASHED,
                CornerRadii.EMPTY,
                new BorderWidths(3)
        );

        contentRow.setBorder(new Border(stroke));*/

        imageView.setFitWidth(300);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true); // Maintain layout proportions
        imageView.setSmooth(true);

        // inputLabel.setMaxWidth(Double.MAX_VALUE);
        // inputTextField.setMaxWidth(280);
        // HBox.setHgrow(inputTextField, Priority.SOMETIMES);
        // submitButton.setMaxWidth(Double.MAX_VALUE);

        // submitButton.setOnAction(this::submitButtonClick);

        contentRow.getChildren().addAll(imageView);

        Label paneTitle = new Label(" Video - Image "); // Spaces prevent line collision
        paneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        paneTitle.setStyle("-fx-background-color: -fx-background;");

        StackPane fieldset = new StackPane();
        fieldset.setStyle("-fx-border-color: #b0b0b0; -fx-border-width: 1px; -fx-border-radius: 3px;");
        fieldset.getChildren().addAll(contentRow, paneTitle);

        StackPane.setAlignment(paneTitle, Pos.TOP_LEFT);
        StackPane.setMargin(paneTitle, new Insets(-9, 0, 0, 15));

        innerGrid.setMaxWidth(Double.MAX_VALUE);
        innerGrid.setPadding(new Insets(15));

        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setHgrow(Priority.ALWAYS);
        colConstraints.setFillWidth(true);
        innerGrid.getColumnConstraints().add(colConstraints);

        // innerGrid.setMaxWidth(Double.MAX_VALUE);
        innerGrid.add(fieldset, 0,0);

        return innerGrid;
    }
}
