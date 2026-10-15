import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.concurrent.Task;

public class DownloadPanel extends GridPane {

    Label statusLabel = new Label("");
    Button downloadButton = new Button("Download");
    InputPanel inputPanel;
    ProgressBar progressBar = new ProgressBar(0);
    // progressBar.setPrefWidth(300);

    DownloadPanel(InputPanel intPanel) {
        this.inputPanel = intPanel;
        HBox contentRow = new HBox(10);

        contentRow.setPadding(new Insets(8, 20, 10, 20));
        contentRow.setAlignment(Pos.CENTER);

        downloadButton.setOnAction(this::downloadButtonClick);

        contentRow.getChildren().addAll(downloadButton, statusLabel);

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
        System.out.println("URL: " + inputPanel.getUrlText());
        try{
            safeFile(inputPanel.getUrlText());
        } catch(Exception e){
            System.out.println("Exception caught!");
        }

    }

    // Method added from page:
    public void safeFile(String src) throws IOException {
        downloadButton.setDisable(true);
        statusLabel.setText("Downloading...");


        Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads");

        DownloadTask downloadTask = new DownloadTask(src, downloadsPath.toString());

        progressBar.progressProperty().bind(downloadTask.progressProperty());

        // Handle lifecycle updates safely on the JavaFX Thread
        downloadTask.setOnSucceeded(event -> {
            statusLabel.setText("Download complete!");
            downloadButton.setDisable(false);
            statusLabel.setText("");
        });

        downloadTask.setOnFailed(event -> {
            statusLabel.setText("Download failed: " + downloadTask.getException().getMessage());
            downloadButton.setDisable(false);
        });

        // 4. Run the Task on a Background Thread
        Thread thread = new Thread(downloadTask);
        thread.setDaemon(true); // Ensures thread closes if application closes
        thread.start();

        //Exctract the name of the video from the src attribute
        /*int indexname = src.lastIndexOf("/");

        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }

        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname, src.length());

        System.out.println(name);

        //Open a URL Stream
        URL url = new URL(src);
        InputStream in = url.openStream();
        OutputStream out = new BufferedOutputStream(new FileOutputStream( downloadsPath + name));
        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();*/
    }

    private static class DownloadTask extends Task<Void> {
        private String fileUrl;
        private final String savePath;

        public DownloadTask(String fileUrl, String savePath) {
            this.fileUrl = fileUrl;
            this.savePath = savePath;
        }

        @Override
        protected Void call() throws Exception {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Get total file size to determine percentage calculation
            long fileLength = connection.getContentLengthLong();

            int indexname = fileUrl.lastIndexOf("/");

            if (indexname == fileUrl.length()) {
                fileUrl = fileUrl.substring(1, indexname);
            }

            indexname = fileUrl.lastIndexOf("/");
            String name = fileUrl.substring(indexname, fileUrl.length());

            System.out.println("Name File: " + name);

            try (InputStream input = new BufferedInputStream(connection.getInputStream());
                 FileOutputStream output = new FileOutputStream(savePath + name)) {

                byte[] data = new byte[4096];
                long totalBytesRead = 0;
                int bytesRead;

                while ((bytesRead = input.read(data)) != -1) {
                    // Check if user canceled task
                    if (isCancelled()) {
                        break;
                    }

                    output.write(data, 0, bytesRead);
                    totalBytesRead += bytesRead;

                    // If file size is known, update progress (current value, max value)
                    if (fileLength > 0) {
                        updateProgress(totalBytesRead, fileLength);
                    } else {
                        // Fallback: Indeterminate status if Content-Length is missing
                        updateProgress(-1, 100);
                    }
                }
            }
            return null;
        }
    }
}
