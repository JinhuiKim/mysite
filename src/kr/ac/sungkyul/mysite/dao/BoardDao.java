package kr.ac.sungkyul.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.ac.sungkyul.mysite.vo.BoardVo;

public class BoardDao {
	private Connection getConnection() throws SQLException {
		Connection conn = null ;
		try {
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch( ClassNotFoundException e ) {
			e.printStackTrace();
		} 
		
		return conn;
	}
	
	public void insert( BoardVo vo ){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try{
			conn = getConnection();
			
			String sql = "insert into board values(seq_board.nextval, ?, ?, SYSDATE, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString( 1, vo.getTitle() );
			pstmt.setString( 2, vo.getContent() );
			
			pstmt.executeUpdate();
			
		} catch( SQLException e ) {
			e.printStackTrace();
		} finally {
			try{
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			} catch( SQLException e ) {
				e.printStackTrace();
			}
		}		
	}
}
