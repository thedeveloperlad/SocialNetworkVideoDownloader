import javax.swing.*;
import java.awt.*;

public class MainWindow {
    JFrame frame = new JFrame("XML Reader");
    //XMLFileInformation displayPanel = new XMLFileInformation();
    //InputPanel UserInfoPanel = new InputPanel(displayPanel);
    XMLFileInformation xmlInfoPanel;// = new XMLFileInformation();
    InputPanel inputPanel;// = new InputPanel();
    MainWindow(){}

    public void XMLReaderScreen(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);


        //frame.add(inputPanel = new InputPanel());
        //frame.add(xmlInfoPanel = new XMLFileInformation());

        XMLFileInformation xmlInfoPanel = new XMLFileInformation();
        InputPanel inputPanel = new InputPanel(xmlInfoPanel);

        frame.add(inputPanel);
        frame.add(xmlInfoPanel);
        //frame.add(new InputPanel());
        //frame.add(new XMLFileInformation());
        frame.add(new UserInfoPanel());

        frame.setVisible(true);
    }
}
