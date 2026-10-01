import javax.swing.*;
import java.awt.*;

public class MainWindow {
    JFrame frame = new JFrame("XML Reader");

    MainWindow(){}

    public void XMLReaderScreen(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(new InputPanel());

        frame.setVisible(true);
    }
}
