package com.bss.server;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Database {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	String info;
	String query;

	// 자바와 오라클 연결소스
	public boolean dbConnect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			System.out.println("드라이버 로딩에 실패");
			return false;
		}
		try {
			String url = "jdbc:oracle:thin:@211.238.142.30:1521:orcl";
			String userId = "scott";
			String userPass = "tiger";
			con = DriverManager.getConnection(url, userId, userPass);

		} catch (SQLException e) {
			System.out.println("커넥션 설정에 실패");
			return false;
		}
		
		return true;
	}

	// 오라클 연결해제
	public void dbDisConnection() {
		try {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (Exception ex) {
		}
	}

	// 회원가입
	public void insertMember(String info) {
		System.out.println(info);
		dbConnect();

		StringTokenizer st = new StringTokenizer(info, "|");

		String sql = "insert into BattleShip values(?,?,?)"; // 회원 추가

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, st.nextToken()); // 1번째 ?에 id대입
			pstmt.setString(2, st.nextToken()); // 2번째 ?에 pwd대입
			pstmt.setString(3, st.nextToken()); // 3번째 이름대입

			rs = pstmt.executeQuery(); // 쿼리 실행

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("새로운 레코드 추가에 실패");
		} finally {
			dbDisConnection();
		}
	}

	// 서버 실행시 자동으로 배틀쉽 테이블을 만들어줌.
	public void battleshipTable() {
		dbConnect();
		String sql = "create table Battleship(id varchar(20) not null,"
				+ "pwd varchar2(20) not null,email varchar2(30))";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

		} catch (SQLException e) {
			System.out.println("테이블이 존재합니다.");
		} catch (Exception e)
		{
			System.out.println("DataBase : battleShipTable() :" + e.getMessage());
		}
		finally {
			dbDisConnection();
		}

	}

	/*
	 * //전체유저 삭제 public void deleteAlluser(){ dbConnect(); query=
	 * "delete from battleship"; try{ pstmt = con.prepareStatement(query);
	 * rs=pstmt.executeQuery(); System.out.println("유저목록 전체삭제");
	 * }catch(Exception ex){} finally{ dbDisConnection(); } }
	 */

	/*
	 * //전체 유저 정보가져오기 public void allUserInfo(){ dbConnect(); String alluser="";
	 * query="select id,pwd,email from BattleShip"; try{ System.out.println(
	 * "전체 유저 검색시작"); pstmt = con.prepareStatement(query);
	 * rs=pstmt.executeQuery();
	 * 
	 * while(rs.next()){ alluser+=rs.getString("id")+"|"; }
	 * System.out.println(alluser);
	 * 
	 * }catch(Exception ex){} finally{ dbDisConnection(); } }
	 */

	// 아이디 중복체크
	public Boolean idCheck(String id) throws SQLException {

		if(!dbConnect())
			return false;
		
		ResultSet rs = null;
		System.out.println(id);
		int Cnt = 0;
		String SQL1 = "SELECT * FROM BattleShip WHERE ID=?";
		pstmt = con.prepareStatement(SQL1);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		/* rs = pstmt.executeQuery(); */
		while (rs.next()) {
			Cnt++;
		}
		dbDisConnection();
		if (Cnt > 0) {
			System.out.println("아이디있음");
			return true;
		} else {
			System.out.println("아이디없음");
			return false;
		}
	}

	public Boolean login(String id, String pwd) throws SQLException {
		
		if(!dbConnect())
			return false;
		
		ResultSet rs = null;

		String SQL1 = "SELECT id,pwd FROM battleship WHERE id like ?";
		pstmt = con.prepareStatement(SQL1);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			if (rs.getString("id").equals(id) && rs.getString("pwd").equals(pwd)) {
				System.out.println("로그인 성공");
				dbDisConnection();
				return true;
			}
		}
		dbDisConnection();
		return false;
	}

}
