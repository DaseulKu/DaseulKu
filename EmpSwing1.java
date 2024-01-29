package jdbc.myproject2;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EmpSwing1 extends JFrame {
	JTextField tf1 = new JTextField(5);
	JTextField tf2 = new JTextField(5);
	JTextField tf3 = new JTextField(5);
	JTextField tf4 = new JTextField(5);
	JTextField tf5 = new JTextField(5);
	JTextField tf6 = new JTextField(5);
	JTextField tf7 = new JTextField(5);
	JTextField tf8 = new JTextField(5);

	JButton bt1 = new JButton("전체내용");
	JButton bt2 = new JButton("입력");
	JButton bt3 = new JButton("이름검색");
	JButton bt4 = new JButton("수정");
	JButton bt5 = new JButton("삭제");

	JTextArea ta = new JTextArea(10, 80);
	Connection conn;
	Statement stmt;

	public EmpSwing1() {
		String url = "jdbc:mysql://localhost:3306/firm";
		String id = "root";
		String pass = "mysql";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pass);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		JLabel lb1 = new JLabel("사원번호");
		JLabel lb2 = new JLabel("사원이름");
		JLabel lb3 = new JLabel("사원직무");
		JLabel lb4 = new JLabel("사수번호");
		JLabel lb5 = new JLabel("입사년도");
		JLabel lb6 = new JLabel("월급");
		JLabel lb7 = new JLabel("상여금");
		JLabel lb8 = new JLabel("부서번호");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("EMP 관리");
		this.setLocation(500, 300);
		this.setSize(1000, 300);
		this.setVisible(true);
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		JPanel jp1 = new JPanel(new FlowLayout());
		jp1.add(bt1);
		jp1.add(bt2);
		jp1.add(bt3);
		jp1.add(bt4);
		jp1.add(bt5);
		con.add(jp1, BorderLayout.SOUTH);

		JScrollPane scroll = new JScrollPane(ta);
		JPanel jp2 = new JPanel(new FlowLayout());
		jp2.add(scroll);
		con.add(jp2, BorderLayout.CENTER);

		JPanel jp3 = new JPanel(new FlowLayout());
		con.add(jp3, BorderLayout.NORTH);
		jp3.add(lb1);
		jp3.add(tf1);
		jp3.add(lb2);
		jp3.add(tf2);
		jp3.add(lb3);
		jp3.add(tf3);
		jp3.add(lb4);
		jp3.add(tf4);
		jp3.add(lb5);
		jp3.add(tf5);
		jp3.add(lb6);
		jp3.add(tf6);
		jp3.add(lb7);
		jp3.add(tf7);
		jp3.add(lb8);
		jp3.add(tf8);

		bt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view();
			}
		});
		bt2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insert();
				clearTextField();
				view();
			}
		});
		bt3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				search();

			}
		});
		bt4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				update();

			}
		});
		bt5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
	}

	private void clearTextField() {
		tf1.setText("");
		tf2.setText("");
		tf3.setText("");
		tf4.setText("");
		tf5.setText("");
		tf6.setText("");
		tf7.setText("");
		tf8.setText("");
	}

	public void view() {
		String sql = "select empno,ename,job,mgr,hiredate, sal,comm,deptno from emp";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			ta.setText("");
			while (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				int mgr = rs.getInt("mgr");
				String hiredate = rs.getString("hiredate");
				double sal = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				int deptno = rs.getInt("deptno");
				String str = String.format("%s, %s, %s, %s, %s, %s, %s, %s\n", empno, ename, job, mgr, hiredate, sal,
						comm, deptno);
				ta.append(str);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert() {
		String sql = String.format("insert into emp values(%s,'%s','%s',%s,'%s',%s,%s,%s)", tf1.getText(),
				tf2.getText(), tf3.getText(), tf4.getText(), tf5.getText(), tf6.getText(), tf7.getText(),
				tf8.getText());
		try {
			int result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void search() {
		String searchname = tf2.getText();
		String sql = String.format("select empno,ename,job,mgr,hiredate, sal,comm,deptno from emp where ename = '%s'",
				searchname);
		try {
			ResultSet rs = stmt.executeQuery(sql);
			ta.setText("");
			if (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				int mgr = rs.getInt("mgr");
				String hiredate = rs.getString("hiredate");
				double sal = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				int deptno = rs.getInt("deptno");

				tf1.setText(String.valueOf(empno));
				tf2.setText(ename);
				tf3.setText(job);
				tf4.setText(String.valueOf(mgr));
				tf5.setText(hiredate);
				tf6.setText(String.valueOf(sal));
				tf7.setText(String.valueOf(comm));
				tf8.setText(String.valueOf(deptno));
			} else {
				clearTextField();
				ta.append("입력한 정보가 없습니다");

			}
		} catch (SQLException e) {
			e.printStackTrace();
			ta.append("유효하지 않은 정보입니다");
		}
	}

	public void update() {
		try {
			String sql = String.format(
					"update emp set ename = '%s', job = '%s', mgr = %s, hiredate = '%s', sal = %s, comm = %s, deptno = %s where empno = %s",
					tf2.getText(), tf3.getText(), tf4.getText(), tf5.getText(), tf6.getText(), tf7.getText(),
					tf8.getText(), tf1.getText());

			stmt = conn.createStatement();
			int updated = stmt.executeUpdate(sql);

			if (updated >= 1) {
				ta.append("정보를 수정했습니다");
			} else {
				ta.append("입력된 정보를 수정할 수 없습니다");
			}
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			ta.append("유효하지 않은 정보입니다");
		}
	}

	public void delete() {
		try {
			stmt = conn.createStatement();
			String sql = String.format("delete from emp where empno = %s",tf1.getText());
			int deleted = stmt.executeUpdate(sql);
			if (deleted >= 1) {
				clearTextField();
				ta.append("정보를 삭제했습니다");
			} else {
				clearTextField();
				ta.append("입력된 정보를 삭제할 수 없습니다");
			}
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			ta.append("유효하지 않은 정보입니다");
		}
	}

	public static void main(String[] args) {
		new EmpSwing1();
	}
}
