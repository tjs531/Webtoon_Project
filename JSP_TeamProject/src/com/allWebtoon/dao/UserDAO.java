package com.allWebtoon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.allWebtoon.db.JdbcSelectInterface;
import com.allWebtoon.db.JdbcTemplate;
import com.allWebtoon.db.JdbcUpdateInterface;
import com.allWebtoon.vo.UserVO;

public class UserDAO {
	
	public static void insUser(UserVO param) {
		
		String sql = "INSERT INTO t_w_user(u_no,u_id, u_password, u_name, u_birth, gender_no, u_email ) VALUES (seq_w_user_cmt.nextval, ?,?,?,?,?,?)";
		
		JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
			 @Override
			public void update(PreparedStatement ps) throws SQLException {
				ps.setNString(1,param.getUser_id());
				ps.setNString(2, param.getUser_password());
				ps.setNString(3, param.getName());
				ps.setNString(4, param.getBirth());
				if(param.getGender().equals("girl")) {
					ps.setInt(5, 1);
				} else {
					ps.setInt(5, 2);
				}
				ps.setNString(6, param.getEmail());
				
				System.out.println("회원가입 성공");
			}
		});
		
		
		for(String str : param.getU_genre()) {
			insU_genre(param, str);
		}
	}
	
	//0:에러발생, 1:로그인 성공, 2:비밀번호 틀림, 3:아이디 없음
		public static int login(UserVO param) {
			
			String sql = "SELECT u_no, u_password, u_name FROM t_w_user WHERE u_id=?";
					
			return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

				@Override
				public void prepared(PreparedStatement ps) throws SQLException {
					ps.setNString(1,  param.getUser_id());
					System.out.println(param.getUser_id());
				}

				@Override
				public int executeQuery(ResultSet rs) throws SQLException {
					
					if(rs.next()) {					//레코드가 있음
						String dbPw = rs.getNString("u_password");
						
						if(dbPw.equals(param.getUser_password())) {	//로그인 성공(비밀번호 맞을 경우)
							int i_user = rs.getInt("u_no");
							String nm = rs.getNString("u_name");
							param.setUser_password(null);
							param.setU_no(i_user);
							param.setName(nm);
							return 1;
						} else {								//로그인 실패.(비밀번호 틀릴 경우)
							return 2;
						}
					}else {							//레코드가 없음. (아이디 없음)
						return 3;						
					}
					
				}	
			});
		}
		
		
		public static void insU_genre(UserVO param,String str) {
			
			String sql = "INSERT INTO t_u_genre(u_no, genre_no) VALUES ((select u_no from t_w_user where u_id=?), (select genre_no from t_genre where genre_name=?))";
			
			JdbcTemplate.executeUpdate(sql, new JdbcUpdateInterface() {
				@Override
				public void update(PreparedStatement ps) throws SQLException {
					System.out.println(param.getUser_id()+ "," + str);
					ps.setNString(1,param.getUser_id());
					ps.setNString(2,str);
				}

			});
		}
}
