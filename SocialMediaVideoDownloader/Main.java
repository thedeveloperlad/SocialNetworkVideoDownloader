import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.swing.*;
import javafx.event.ActionEvent;

import java.io.File;

public class Main extends Application{
    Label inputLabel = new Label("Insert URL: ");
    TextField inputTextField = new TextField();
    Button submitButton = new Button("Submit");
    CheckBox twitterCheckBox = new CheckBox("Twitter");
    CheckBox facebookCheckBox = new CheckBox("Facebook");
    CheckBox instagramCheckBox = new CheckBox("Instagram");
    CheckBox mlbCheckBox = new CheckBox("MLB");

    Button downloadButton = new Button("Download");

    /* videoPane or imagePane */
    String userHome = System.getProperty("user.home");
    File file = new File(userHome + "/Downloads/HR8eJuVb0AAIs7S.jfif");
    String imageUrl = file.toURI().toString();
    Image image = new Image(imageUrl);
    // Image image = new Image(getClass().getResourceAsStream("/images/logo.png"));
    ImageView imageView = new ImageView(image);

    TwitterParser twitterParser = new TwitterParser();

    @Override
    public void start(Stage primaryStage) {

        GridPane gridPane = new GridPane();

        gridPane.add(inputPanel(), 0, 0);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setMaxWidth(Double.MAX_VALUE);

        gridPane.add(videoPane(), 0, 1);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setMaxWidth(Double.MAX_VALUE);

        gridPane.add(downloadInfoPane(), 0, 2);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setMaxWidth(Double.MAX_VALUE);

        ColumnConstraints column = new javafx.scene.layout.ColumnConstraints();
        column.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        gridPane.getColumnConstraints().add(column);


        //VBox root = new VBox(10, textField, button);
        //gridPane.add(root, 0,0);
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.getIcons().add(new Image("/app_icons/app_icon.png"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Social Media Downloader");
        primaryStage.show();
    }

    private void submitButtonClick(ActionEvent event) {
        System.out.println("submitButton clicked via method reference!");
        String urlLink = inputTextField.getText();

        if(twitterCheckBox.isSelected())
        {
            System.out.println("Twitter Checkbox is checked");
            twitterParser.readTwitterAttributes(urlLink);
            // Do Twitter parser
        } else if(facebookCheckBox.isSelected())
        {
            System.out.println("Facebook Checkbox is checked");
            // Do Facebook parser
        } else if(instagramCheckBox.isSelected())
        {
            System.out.println("Instagram Checkbox is checked");
            // Do Instagram parser
        } else if(mlbCheckBox.isSelected())
        {
            System.out.println("MLB Checkbox is checked");
            // Do MLB parser
        }

    }

    private void downloadButtonClick(ActionEvent event) {
        System.out.println("downloadButton clicked via method reference!");
        String urlLink = inputTextField.getText();


    }

    public GridPane inputPanel() {
        HBox contentRow = new HBox(10);
        HBox checkboxRow = new HBox(10);
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

        inputLabel.setMaxWidth(Double.MAX_VALUE);
        inputTextField.setMaxWidth(280);
        HBox.setHgrow(inputTextField, Priority.SOMETIMES);
        // submitButton.setMaxWidth(Double.MAX_VALUE);

        submitButton.setOnAction(this::submitButtonClick);

        contentRow.getChildren().addAll(inputLabel, inputTextField, submitButton);
        contentRow.getChildren().addAll(twitterCheckBox, facebookCheckBox, instagramCheckBox, mlbCheckBox);

        Label paneTitle = new Label(" Input "); // Spaces prevent line collision
        paneTitle.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        paneTitle.setStyle("-fx-background-color: -fx-background;");

        StackPane fieldset = new StackPane();
        fieldset.setStyle("-fx-border-color: #b0b0b0; -fx-border-width: 1px; -fx-border-radius: 3px;");
        fieldset.getChildren().addAll(contentRow, paneTitle);

        // StackPane fieldsetCheckbox = new StackPane();
        // fieldsetCheckbox.getChildren().addAll(checkboxRow);

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
        // innerGrid.add(fieldsetCheckbox, 0,1);

        return innerGrid;
    }

    public GridPane videoPane() {
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

        inputLabel.setMaxWidth(Double.MAX_VALUE);
        inputTextField.setMaxWidth(280);
        HBox.setHgrow(inputTextField, Priority.SOMETIMES);
        // submitButton.setMaxWidth(Double.MAX_VALUE);

        submitButton.setOnAction(this::submitButtonClick);

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

    public GridPane downloadInfoPane() {
        HBox contentRow = new HBox(10);
        GridPane innerGrid = new GridPane();

        contentRow.setPadding(new Insets(8, 20, 10, 20));
        contentRow.setAlignment(Pos.CENTER);

        downloadButton.setOnAction(this::downloadButtonClick);

        contentRow.getChildren().addAll(downloadButton);

        Label paneTitle = new Label(" Download "); // Spaces prevent line collision
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

    public GridPane inputPanelExtra() {
        VBox vBox = new VBox(10);
        GridPane innerGrid = new GridPane();
        Label paneTitle = new Label(" - Input EXTRA - ");
        paneTitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 16));

        StackPane fieldset = new StackPane();
        fieldset.getChildren().addAll(innerGrid, paneTitle);

        StackPane.setAlignment(paneTitle, Pos.TOP_LEFT);
        StackPane.setMargin(paneTitle, new Insets(-10, 0, 0, 15));
        //innerGrid.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        //innerGrid.add(new Button("JavaFX Button in GridPane"), 0, 0);
        vBox.setPadding(new Insets(10, 20, 10, 20)); //new Insets(20));
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(paneTitle, inputLabel, inputTextField, submitButton);

        innerGrid.setMaxSize(300, 300);

        innerGrid.setGridLinesVisible(true);
        innerGrid.add(vBox, 0,0);


        return innerGrid;
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
