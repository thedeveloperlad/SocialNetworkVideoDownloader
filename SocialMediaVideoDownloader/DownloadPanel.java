import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DownloadPanel extends GridPane {

    Button downloadButton = new Button("Download");

    DownloadPanel() {
        HBox contentRow = new HBox(10);

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

        this.setMaxWidth(Double.MAX_VALUE);
        this.setPadding(new Insets(15));

        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setHgrow(Priority.ALWAYS);
        colConstraints.setFillWidth(true);
        this.getColumnConstraints().add(colConstraints);

        // innerGrid.setMaxWidth(Double.MAX_VALUE);
        this.add(fieldset, 0,0);
    }

    private void downloadButtonClick(ActionEvent event) {
        System.out.println("downloadButton clicked via method reference!");
        // String urlLink = inputTextField.getText();
    }
}
