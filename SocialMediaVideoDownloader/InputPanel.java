import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InputPanel extends GridPane {

    Label inputLabel = new Label("Insert URL: ");
    TextField inputTextField = new TextField();
    Button submitButton = new Button("Submit");
    CheckBox twitterCheckBox = new CheckBox("Twitter");
    CheckBox facebookCheckBox = new CheckBox("Facebook");
    CheckBox instagramCheckBox = new CheckBox("Instagram");
    CheckBox mlbCheckBox = new CheckBox("MLB");

    URLParser urlParser = new URLParser();
    HTMLParser htmlParser = new HTMLParser();

    InputPanel(){
        HBox contentRow = new HBox(10);
        HBox checkboxRow = new HBox(10);
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

        this.setMaxWidth(Double.MAX_VALUE);
        this.setPadding(new Insets(15));

        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setHgrow(Priority.ALWAYS);
        colConstraints.setFillWidth(true);
        this.getColumnConstraints().add(colConstraints);

        // innerGrid.setMaxWidth(Double.MAX_VALUE);
        this.add(fieldset, 0,0);
    }

    /*
    public GridPane inputPanel() {
        HBox contentRow = new HBox(10);
        HBox checkboxRow = new HBox(10);
        GridPane innerGrid = new GridPane();

        contentRow.setPadding(new Insets(8, 20, 10, 20));
        contentRow.setAlignment(Pos.CENTER);
        // contentRow.setFillWidth(true);

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
    */

    private void submitButtonClick(ActionEvent event) {
        System.out.println("submitButton clicked via method reference!");
        String urlLink = inputTextField.getText();
        String getHtmlStr = urlParser.getStringFromURL(urlLink);

        String title = htmlParser.getTitle(htmlParser.getHtmlDocument(getHtmlStr));
        System.out.println("Title: " + title);

        if(twitterCheckBox.isSelected())
        {
            System.out.println("Twitter Checkbox is checked: ");
            // twitterParser.readTwitterAttributes(urlLink);
            // String getHtmlStr = urlParser.getStringFromURL(urlLink);
            System.out.println("HTML OBJECT STRING= \n");
            System.out.println(getHtmlStr);
            System.out.println("=HTML OBJECT STRING= \n");
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
}
