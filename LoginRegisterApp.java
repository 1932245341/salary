import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginRegisterApp extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField, newPasswordField;
    private JButton loginButton, registerButton, changePasswordButton,deleteUserButton;
    private Connection connection;

    private JButton logoutButton;
    public LoginRegisterApp() {
        // Initialize GUI components
        setTitle("UserAuthApp");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;

        JLabel usernameLabel = new JLabel("账号:");
        add(usernameLabel, constraints);

        constraints.gridx = 1;
        usernameField = new JTextField(20);
        add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel passwordLabel = new JLabel("密码:");
        add(passwordLabel, constraints);

        constraints.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, constraints);

        constraints.gridx = 1;
        newPasswordField = new JPasswordField(20);
        add(newPasswordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("登录");
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);

        registerButton = new JButton("注册");
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton);

        constraints.gridy = 3;
        changePasswordButton = new JButton("修改密码");
        changePasswordButton.addActionListener(this);
        buttonPanel.add(changePasswordButton);

        deleteUserButton = new JButton("注销用户");
        deleteUserButton.addActionListener(this);
        buttonPanel.add(deleteUserButton);


        add(buttonPanel, constraints);
        // Initialize MySQL connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/salary", "salary_csg", "1932245341");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());


        if (event.getSource() == loginButton) {
            loginUser(username, password);
        } else if (event.getSource() == registerButton) {
            registerUser(username, password);
        } else if (event.getSource() == changePasswordButton) {
            changePassword(username, password);
        } else if (event.getSource() == deleteUserButton) {
            deleteUser(username, password);
        }
    }

    private void loginUser(String username, String password) {
        try {
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "登录成功!");
                new MainApp(connection).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "账号或密码错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registerUser(String username, String password) {
        try {
            String query = "INSERT INTO user (username, password) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed.");
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(this, "Username already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }
    private void changePassword(String username, String password) {
        try {
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Prompt user for new password
                String newPassword = promptForNewPassword();
                if (newPassword != null) {
                    updatePassword(username, newPassword);
                }
            } else {
                JOptionPane.showMessageDialog(this, "密码错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private String promptForNewPassword() {
        JPasswordField newPasswordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(null, newPasswordField, "请输入新密码", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new String(newPasswordField.getPassword());
        } else {
            return null;
        }
    }
    private void updatePassword(String username, String newPassword) {
        try {
            String query = "UPDATE user SET password = ? WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "密码修改成功!");
            } else {
                JOptionPane.showMessageDialog(this, "密码修改失败.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void deleteUser(String username, String password) {
        try {
            String query = "DELETE FROM user WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "用户注销成功!");
            } else {
                JOptionPane.showMessageDialog(this, "注销用户失败.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginRegisterApp app = new LoginRegisterApp();
            app.setVisible(true);
        });
    }
}
