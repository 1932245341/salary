import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MainApp extends JFrame implements ActionListener {
    private JButton employeeButton, departmentButton, wageButton, attendanceButton;
    private JButton addButton, deleteButton, queryButton, modifyButton;
    private JPanel mainPanel;
    private Connection connection;

    public MainApp(Connection connection) {
        this.connection = connection;

        setTitle("工资管理主界面");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mainPanel = new JPanel(new BorderLayout());

        JPanel sidePanel = new JPanel(new GridLayout(8, 1, 10, 10)); // 修改行数，加上四个功能按钮
        employeeButton = new JButton("员工管理");
        employeeButton.addActionListener(this);
        departmentButton = new JButton("部门管理");
        departmentButton.addActionListener(this);
        wageButton = new JButton("工资管理");
        wageButton.addActionListener(this);
        attendanceButton = new JButton("考勤管理");
        attendanceButton.addActionListener(this);

        // 新增按钮
        addButton = new JButton("增加");
        addButton.addActionListener(this);
        deleteButton = new JButton("删除");
        deleteButton.addActionListener(this);
        queryButton = new JButton("查询");
        queryButton.addActionListener(this);
        modifyButton = new JButton("修改");
        modifyButton.addActionListener(this);

        sidePanel.add(employeeButton);
        sidePanel.add(departmentButton);
        sidePanel.add(wageButton);
        sidePanel.add(attendanceButton);
        sidePanel.add(addButton);
        sidePanel.add(deleteButton);
        sidePanel.add(queryButton);
        sidePanel.add(modifyButton);

        add(sidePanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == employeeButton) {
            displayTableData("employee");
        } else if (event.getSource() == departmentButton) {
            displayTableData("department");
        } else if (event.getSource() == wageButton) {
            displayTableData("wage");
        } else if (event.getSource() == attendanceButton) {
            displayTableData("attendance");
        } else if (event.getSource() == addButton) {
            // 启动增加功能窗口
            new AddRecord(connection).setVisible(true);
        } else if (event.getSource() == deleteButton) {
            // 启动删除功能窗口
            new DeleteRecord(connection).setVisible(true);
        } else if (event.getSource() == queryButton) {
            // 启动查询功能窗口
            new QueryRecord(connection).setVisible(true);
        } else if (event.getSource() == modifyButton) {
            // 启动修改功能窗口
            new ModifyRecord(connection).setVisible(true);
        }
    }

    private void displayTableData(String tableName) {
        try {
            String query = "SELECT * FROM " + tableName;
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            rs.last();
            int rowCount = rs.getRow();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "No data available in the table " + tableName);
                return;
            }
            rs.beforeFirst();

            Object[][] data = new Object[rowCount][columnCount];
            int rowIndex = 0;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    data[rowIndex][i - 1] = rs.getObject(i);
                }
                rowIndex++;
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            mainPanel.removeAll();
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            validate();
            repaint();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving data from table " + tableName + ": " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/salary", "salary_csg", "1932245341");
                new MainApp(connection).setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
