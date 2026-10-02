import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class UserInfoPanel extends JPanel{
    private JLabel firstName = new JLabel("First Name: ");
    private final JTextField  firstNameValue = new JTextField (15);
    private JLabel lastName = new JLabel("Last Name: ");
    private final JTextField  lastNameValue = new JTextField (15);
    private JLabel nickName = new JLabel("Nickname: ");
    private final JTextField  nickNameValue = new JTextField (15);
    private JLabel salary = new JLabel("Salary: ");
    private final JTextField  salaryValue = new JTextField (15);

    public UserInfoPanel(){
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(695, 100));
        setBorder(new CompoundBorder(new TitledBorder("XML User Information"), new EmptyBorder(0, 0, 0, 150)));
        GridBagConstraints gbc = new GridBagConstraints();
        /*
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        */

        gbc.gridx = 0; // First column
        gbc.gridy = 0; // First row
        gbc.weightx = 1.0;      // Grow horizontally
        gbc.weighty = 0.5;      // Grow vertically

        add(firstName, gbc);
        gbc.gridy++;
        add(lastName, gbc);
        gbc.gridy++;
        add(nickName, gbc);
        gbc.gridy++;
        add(salary, gbc);
        gbc.gridy++;

        gbc.gridx++;
        gbc.gridy = 0;
        // gbc.weightx = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        firstNameValue.setEditable(false);
        lastNameValue.setEditable(false);
        nickNameValue.setEditable(false);
        salaryValue.setEditable(false);

        firstNameValue.setSize(5,150);
        lastNameValue.setSize(5,150);
        nickNameValue.setSize(5,150);
        salaryValue.setSize(5,150);

        add(firstNameValue, gbc);
        gbc.gridy++;
        add(lastNameValue, gbc);
        gbc.gridy++;
        add(nickNameValue, gbc);
        gbc.gridy++;
        add(salaryValue, gbc);
        gbc.gridy++;
    }

    public String getFirstNameValue() {
        return firstNameValue.getText();
    }

    public void setFirstNameValue(String value) {
        firstNameValue.setText(value);
    }

    public String getLastNameValue() {
        return lastNameValue.getText();
    }

    public void setLastNameValue(String value) {
        lastNameValue.setText(value);
    }

    public String getNickNameValue() {
        return nickNameValue.getText();
    }

    public void setNickNameValue(String value) {
        nickNameValue.setText(value);
    }

    public String getSalaryValue() {
        return nickNameValue.getText();
    }

    public void setSalaryValue(String value) {
        salaryValue.setText(value);
    }
}
