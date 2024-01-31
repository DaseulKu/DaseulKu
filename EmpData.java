package jdbc.myproject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Emp {
	private int empno;
	private String ename;
	private String job;
	private int mgr;
	private String hiredate;
	private double sal;
	private double comm;
	private int deptno;


	public Emp(int empno, String ename, String job, int mgr, String hiredate, double sal, double comm, int deptno) {
		this.empno = empno;
		this.ename = ename;
		this.job = job;
		this.mgr = mgr;
		this.hiredate = hiredate;
		this.sal = sal;
		this.comm = comm;
		this.deptno = deptno;
	}

	public int getEmpno() {
		return empno;
	}


	public void setEmpno(int empno) {
		this.empno = empno;
	}


	public String getEname() {
		return ename;
	}


	public void setEname(String ename) {
		this.ename = ename;
	}


	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}


	public int getMgr() {
		return mgr;
	}


	public void setMgr(int mgr) {
		this.mgr = mgr;
	}


	public String getHiredate() {
		return hiredate;
	}


	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}


	public double getSal() {
		return sal;
	}


	public void setSal(double sal) {
		this.sal = sal;
	}


	public double getComm() {
		return comm;
	}


	public void setComm(double comm) {
		this.comm = comm;
	}


	public int getDeptno() {
		return deptno;
	}


	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}


	@Override
	public String toString() {
		return "Emp [empno=" + empno + ", ename=" + ename + ", job=" + job + ", mgr=" + mgr + ", hiredate=" + hiredate
				+ ", sal=" + sal + ", comm=" + comm + ", deptno=" + deptno + "]";
	}

}

interface DBOperations {
	void viewData(Connection connection);

	void insertData(Connection connection, Scanner scanner);

	void updateData(Connection connection, Scanner scanner);

	void deleteData(Connection connection, Scanner scanner);

}

public class EmpData implements DBOperations {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/firm";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "mysql";

	@Override
	public void viewData(Connection connection) {
		String sql = "select * from emp";
		List<Emp> list = new ArrayList<>();
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				int mgr = rs.getInt("mgr");
				String hiredate = rs.getString("hiredate");
				double sal = rs.getDouble("sal");
				double comm = rs.getDouble("comm");
				int deptno = rs.getInt("deptno");
				Emp emp = new Emp(empno, ename, job, mgr, hiredate, sal, comm, deptno);
				list.add(emp);
			}
		} catch (SQLException e) {
			System.out.println("유효하지 않은 정보입니다");
		}
		for (Emp emp : list) {
			System.out.println(emp.getEmpno() + "|" + emp.getEname() + "|" + emp.getJob() + "|" + emp.getMgr() + "|"
					+ emp.getHiredate() + "|" + emp.getSal() + "|" + emp.getComm() + "|" + emp.getDeptno());
		}
	}

	@Override
	public void insertData(Connection connection, Scanner scanner) {
		System.out.print("사원번호: ");
		int empno = Integer.parseInt(scanner.nextLine());
		System.out.print("사원이름: ");
		String ename = scanner.nextLine();
		System.out.print("사원직무: ");
		String job = scanner.nextLine();
		System.out.print("사수번호: ");
		int mgr = Integer.parseInt(scanner.nextLine());
		System.out.print("입사일: ");
		String hiredate = scanner.nextLine();
		System.out.print("월급: ");
		double sal = Double.parseDouble(scanner.nextLine());
		System.out.print("성과금: ");
		double comm = Double.parseDouble(scanner.nextLine());
		System.out.print("부서번호: ");
		int deptno = Integer.parseInt(scanner.nextLine());

		try (Statement stmt = connection.createStatement()) {
			String sql = "insert into emp (empno,ename,job,mgr,hiredate,sal,comm,deptno) values " + "(" + empno + ",'"
					+ ename + "','" + job + "'," + mgr + ", '" + hiredate + "'," + sal + "," + comm + ", " + deptno
					+ ")";
			int result = stmt.executeUpdate(sql);
			if (result == 1) {
				System.out.println("입력성공");
			} else {
				System.out.println("입력실패");
			}
		} catch (SQLException e) {
			System.out.println("유효하지 않은 정보입니다");
		}
	}

	@Override
	public void updateData(Connection connection, Scanner scanner) {
		System.out.print("수정할 사원의 사원번호를 입력하세요");
		int updatempno = Integer.parseInt(scanner.nextLine());
		String select = "select * from emp";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(select)) {
			while (rs.next()) {
				int empno = rs.getInt("empno");

				if (empno == updatempno) {
					System.out.println(updatempno + " 사원의 수정할 정보를 입력하세요");
				}
			}
			System.out.print("월급: ");
			double sal = Double.parseDouble(scanner.nextLine());
			System.out.print("성과금: ");
			double comm = Double.parseDouble(scanner.nextLine());
			System.out.print("사원직무: ");
			String job = scanner.nextLine();
			System.out.print("사수번호: ");
			int mgr = Integer.parseInt(scanner.nextLine());
			System.out.print("부서번호: ");
			int deptno = Integer.parseInt(scanner.nextLine());

			String sql = "update emp set sal = " + sal + ", comm = " + comm + ", job = '" + job + "', mgr = " + mgr
					+ ", deptno = " + deptno + " where empno = " + updatempno;
			int result = stmt.executeUpdate(sql);
			if (result >= 1) {
				System.out.println("정보 수정을 완료했습니다");
			} else {
				System.out.println("입력된 정보를 수정할 수 없습니다");
			}

		} catch (SQLException e) {
			System.out.println("유효하지 않은 정보입니다");
		}
	}

	@Override
	public void deleteData(Connection connection, Scanner scanner) {
		System.out.print("삭제할 사원명을 입력하세요");
		String deletename = scanner.nextLine();
		String sql = "delete from emp where ename = '" + deletename + "'";
		try (Statement stmt = connection.createStatement()) {
			int result = stmt.executeUpdate(sql);
			if (result >= 1) {
				System.out.println(deletename + " 사원의 정보를 삭제했습니다");
			} else {
				System.out.println(deletename + " 사원의 정보를 찾을 수 없습니다");
			}
		} catch (SQLException e) {
			System.out.println("유효하지 않은 정보입니다");
		}
	}

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {			
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			DBOperations empData = new EmpData();
			Scanner scanner = new Scanner(System.in);
			boolean exit = false;
			while (!exit) {
				System.out.println("1. 데이터 보기");// R
				System.out.println("2. 데이터 삽입");// C
				System.out.println("3. 데이터 수정");// U
				System.out.println("4. 데이터 삭제");// D
				System.out.println("5. 종료");
				System.out.print("선택하세요: ");

				String choice = scanner.nextLine();
				switch (choice) {
				case "1":
					empData.viewData(connection);
					break;
				case "2":
					empData.insertData(connection, scanner);
					break;
				case "3":
					empData.updateData(connection, scanner);
					break;
				case "4":
					empData.deleteData(connection, scanner);
					break;
				case "5":
					exit = true;
					break;
				default:
					System.out.println("유효하지 않은 선택입니다 다시 시도하세요");
					break;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB 연결 중 오류가 발생했습니다");
		}
	}
}