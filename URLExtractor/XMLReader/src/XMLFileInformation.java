import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class XMLFileInformation extends JPanel {
    JTextArea xmlLogTextArea = new JTextArea(22, 40);
    JScrollPane scrollPane = new JScrollPane(xmlLogTextArea);

    public XMLFileInformation(){
        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(new TitledBorder("XML file information"), new EmptyBorder(0, 0, 0, 150)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx++;
        gbc.weightx = 5;
        gbc.fill = GridBagConstraints.VERTICAL;

        add(scrollPane, gbc);
        gbc.gridy++;
    }

    void setLogTextArea(String textInformation){
        System.out.println(textInformation);
        xmlLogTextArea.setText(textInformation);
    }

    String getLogTextArea(){
        return xmlLogTextArea.getText();
    }
}
