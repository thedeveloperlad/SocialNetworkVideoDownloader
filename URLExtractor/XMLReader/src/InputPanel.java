import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;

public class InputPanel extends JPanel implements ActionListener {

    JLabel inputLabel = new JLabel("Enter XML URL:");
    JTextField inputField = new JTextField(20);
    JButton uploadFileButton = new JButton("Upload XML File");
    JButton submitButton = new JButton("Generate File");

    JFileChooser fileChooser = new JFileChooser();
    private XMLFileInformation xmlFileInformation;
    /*
    private XMLFileInformation xmlFileInformation;

    public InputPanel(XMLFileInformation xmlFileInformation) {
        this.xmlFileInformation = xmlFileInformation;
    }
    */

    public InputPanel(XMLFileInformation xmlFileInformation){
        this.xmlFileInformation = xmlFileInformation;

        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(new TitledBorder("Input"), new EmptyBorder(0, 0, 0, 150)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.weightx = 1.0;      // Grow horizontally
        gbc.weighty = 0.5;      // Grow vertically
        gbc.anchor = GridBagConstraints.WEST;

        uploadFileButton.addActionListener(this::uploadXMLFile);
        submitButton.addActionListener(this::generateXMLFile);

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

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
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

    public void generateXMLFile(ActionEvent env) {
        if (inputField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter XML URL");
        } else  {
            File xmlFile = new File(inputField.getText());
            String finalText = "";
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();

                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                NodeList nList = doc.getElementsByTagName("staff");
                System.out.println("----------------------------");

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    System.out.println("\nCurrent Element :" + nNode.getNodeName());
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        /*System.out.println("Staff id : "
                                + eElement.getAttribute("id"));
                        System.out.println("First Name : "
                                + eElement.getElementsByTagName("firstname")
                                .item(0).getTextContent());
                        System.out.println("Last Name : "
                                + eElement.getElementsByTagName("lastname")
                                .item(0).getTextContent());
                        System.out.println("Nick Name : "
                                + eElement.getElementsByTagName("nickname")
                                .item(0).getTextContent());
                        System.out.println("Salary : "
                                + eElement.getElementsByTagName("salary")
                                .item(0).getTextContent());*/
                        finalText += "Staff id : " + eElement.getAttribute("id") + "\n" +
                                "First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent() + "\n" +
                                "Last Name : "  + eElement.getElementsByTagName("lastname").item(0).getTextContent() + "\n" +
                                "Nick Name : "  + eElement.getElementsByTagName("nickname").item(0).getTextContent() + "\n" +
                                "Salary : " + eElement.getElementsByTagName("salary") .item(0).getTextContent() + "\n" + "\n";
                        System.out.println(finalText);
                        if (xmlFileInformation != null) {
                            xmlFileInformation.setLogTextArea(finalText);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
