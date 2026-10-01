import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class InputPanel extends JPanel{

    JLabel inputLabel = new JLabel("Enter XML URL:");
    JTextField inputField = new JTextField(20);
    JButton uploadFileButton = new JButton("Upload XML File");
    JButton submitButton = new JButton("Generate File");

    public InputPanel(){
        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(new TitledBorder("Input"), new EmptyBorder(0, 0, 0, 150)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.weightx = 1.0;      // Grow horizontally
        gbc.weighty = 0.5;      // Grow vertically
        gbc.anchor = GridBagConstraints.WEST;

        // submitButton.addActionListener(this::submitButton);

        add(inputLabel, gbc);
        gbc.gridx++;
        add(inputField, gbc);
        gbc.gridx++;
        add(uploadFileButton, gbc);
        gbc.gridx++;
        add(submitButton, gbc);
    }

    public String getStatusValue() {
        return inputField.getText();
    }

    public void setStatusValue(String value) {
        inputField.setText(value);
    }
}
