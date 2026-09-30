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

public class Main extends Application {

    InputPanel inputPane = new InputPanel();
    ContentPanel contentPanel = new ContentPanel();
    DownloadPanel downloadPanel = new DownloadPanel();

    @Override
    public void start(Stage primaryStage) {

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setMaxWidth(Double.MAX_VALUE);
        // gridPane.setVgap(20);

        gridPane.add(inputPane, 0, 0);
        gridPane.add(contentPanel, 0, 2);
        gridPane.add(downloadPanel, 0, 3);

        ColumnConstraints column = new javafx.scene.layout.ColumnConstraints();
        column.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        gridPane.getColumnConstraints().add(column);


        //VBox root = new VBox(10, textField, button);
        Scene scene = new Scene(gridPane, 800, 600);
        primaryStage.getIcons().add(new Image("/app_icons/app_icon.png"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Social Media Downloader");
        primaryStage.show();
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

        // vBox.getChildren().addAll(paneTitle, inputLabel, inputTextField, submitButton);

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
