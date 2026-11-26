import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

public class DownloadPanel extends GridPane {

    Label statusLabel = new Label("");
    Button downloadButton = new Button("Download");
    Label fileName = new Label("File Name: ");
    TextField outputFileNameField = new TextField();
    CheckBox defaultCheckBox = new CheckBox();
    InputPanel inputPanel;
    ProgressBar progressBar = new ProgressBar(0);
    // progressBar.setPrefWidth(300);

    DownloadPanel(InputPanel intPanel) {
        this.inputPanel = intPanel;
        HBox contentRow = new HBox(10);

        contentRow.setPadding(new Insets(8, 20, 10, 20));
        contentRow.setAlignment(Pos.CENTER);

        downloadButton.setOnAction(this::downloadButtonClick);

        defaultCheckBox.setSelected(true);
        defaultCheckBox.setOnAction(event -> {
            if (defaultCheckBox.isSelected()) {
                outputFileNameField.disableProperty().bind(defaultCheckBox.selectedProperty());
            } else {
                outputFileNameField.disableProperty().bind(defaultCheckBox.selectedProperty());
            }
        });
        outputFileNameField.setPrefWidth(380);
        defaultCheckBox.setText("Default Name");

        contentRow.getChildren().addAll(fileName, outputFileNameField, defaultCheckBox, downloadButton, statusLabel);

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
        String filePathName;
        if (defaultCheckBox.isSelected()) {
            filePathName = inputPanel.getUrlText();
        } else {
            if(!outputFileNameField.getText().isEmpty()) {
                filePathName = outputFileNameField.getText();
            } else {
                filePathName = "NameEmpty_DefaultName";
            }
        }

        try{
            if(inputPanel.getUrlText().contains("dailymotion")){
                System.out.println("Inside if URL: " + inputPanel.getUrlText());
                Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads");
                downloadM3u8ToMp4(inputPanel.getUrlText(), downloadsPath.toString() + "\\output_video.mp4");

                // @ Testing file name path
                // downloadM3u8ToMp4(inputPanel.getUrlText(), downloadsPath.toString() + "\\"+ filePathName+".mp4");
            } else {
                safeFile(inputPanel.getUrlText(), filePathName, defaultCheckBox.isSelected());
            }
        } catch(Exception e){
            System.out.println("Exception caught!");
        }

    }

    public void setText(String text){
        outputFileNameField.textProperty().setValue(text);
    }

    // Method added from page:
    public void safeFile(String src, String saveFileName, boolean checkbox) throws IOException {
        downloadButton.setDisable(true);
        statusLabel.setText("Downloading...");

        Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads");

        DownloadTask downloadTask = new DownloadTask(src, downloadsPath.toString(), saveFileName, checkbox);

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

    // DailyMotion Download
    public static void downloadM3u8ToMp4(String m3u8Url, String outputMp4Path) throws Exception {
        List<String> command = new ArrayList<>();
        command.add(".\\ffmpeg-n9.0-latest-win64-gpl-9.0\\bin\\ffmpeg"); // Or full path, e.g., "C:\\ffmpeg\\bin\\ffmpeg.exe"

        // Pass a User-Agent header so Dailymotion's CDN does not reject the request
        command.add("-user_agent");
        command.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64)");

        // Input M3U8 URL
        command.add("-i");
        command.add(m3u8Url);

        // Copy video/audio streams directly without re-encoding (very fast)
        command.add("-c");
        command.add("copy");

        // Fix AAC audio bitstream format for MP4 containers
        command.add("-bsf:a");
        command.add("aac_adtstoasc");

        // Overwrite output file if it already exists
        command.add("-y");
        command.add(outputMp4Path);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true); // Merge stderr into stdout so the process doesn't hang

        Process process = processBuilder.start();

        // Read FFmpeg console output to prevent buffer deadlock and monitor progress
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Video successfully saved to: " + outputMp4Path);
        } else {
            throw new RuntimeException("FFmpeg failed with exit code: " + exitCode);
        }
    }

    private static class DownloadTask extends Task<Void> {
        private String fileUrl;
        private final String savePath;
        private final String fileName;
        private final boolean fileNameCheckBox;

        public DownloadTask(String fileUrl, String savePath,  String name, boolean checkbox) {
            this.fileUrl = fileUrl;
            this.savePath = savePath;
            this.fileName = name;
            this.fileNameCheckBox = checkbox;
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

            String name = "";
            if(fileNameCheckBox)
            {
                indexname = fileUrl.lastIndexOf("/");
                name = fileUrl.substring(indexname, fileUrl.length());
            } else {
                name = fileName;
            }

            if(!name.contains("/"))
            {
                name = "/"+ name + ".mp4";

            }

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
