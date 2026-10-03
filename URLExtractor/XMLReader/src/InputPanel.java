import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.util.List;

public class InputPanel extends JPanel implements ActionListener {

    JLabel inputLabel = new JLabel("Enter XML URL:");
    JTextField inputField = new JTextField(20);
    JButton uploadFileButton = new JButton("Upload XML File");
    JButton submitButton = new JButton("Generate File");

    JFileChooser fileChooser = new JFileChooser();

    public InputPanel(){
        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(new TitledBorder("Input"), new EmptyBorder(0, 0, 0, 150)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.weightx = 1.0;      // Grow horizontally
        gbc.weighty = 0.5;      // Grow vertically
        gbc.anchor = GridBagConstraints.WEST;

        uploadFileButton.addActionListener(this::uploadXMLFile);
        // submitButton.addActionListener(this::submitButton);

        add(inputLabel, gbc);
        gbc.gridx++;
        add(inputField, gbc);
        gbc.gridx++;
        add(uploadFileButton, gbc);
        gbc.gridx++;
        add(submitButton, gbc);
    }

    public void uploadXMLFile(ActionEvent e) {
        System.out.println("uploadXMLFile button");
        // String userInput = inputField.getText();

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File fileSelected = fileChooser.getSelectedFile();
            System.out.println("File selected: " + fileSelected.getAbsolutePath());

            if (!fileSelected.getAbsolutePath().contains(".xml")) { // path.trim().toLowerCase().endsWith(".xml");
                System.out.println("Invalid file, only supports XML files");
            } else {
                inputField.setText(fileSelected.getAbsolutePath());
            }
        } else {
            System.out.println("Dialog is closed.");
        }

        /*if (!userInput.contains("http://")) {
            userInput = "http://" + userInput;
        }*/
    }

    public String getStatusValue() {
        return inputField.getText();
    }

    public void setStatusValue(String value) {
        inputField.setText(value);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
