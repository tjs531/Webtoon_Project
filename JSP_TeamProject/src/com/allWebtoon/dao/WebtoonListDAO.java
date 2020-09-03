package com.allWebtoon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.allWebtoon.db.JdbcSelectInterface;
import com.allWebtoon.db.JdbcTemplate;
import com.allWebtoon.vo.SearchWebtoonVO;
import com.allWebtoon.vo.WebtoonVO;

public class WebtoonListDAO {
	// 홈화면 출력 
	public static ArrayList<WebtoonVO> selRandomWebtoonList(ArrayList<WebtoonVO> list, int platformNum, int randomLength){
	/*	String sql = " select w_no, w_title, w_story, w_thumbnail, w_link, plat_no "
					+ " from t_webtoon "
					+ " where plat_no = ? "
					+ " order by rand() limit ? ";*/
		
		String sql = " select w_no, w_title, w_story, w_thumbnail, w_link, plat_no "
				+ " from t_webtoon "
				+ " where plat_no = ? " ;
		
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			//물음표 넣을 때
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, platformNum);
			//	ps.setInt(2, randomLength);
			}
			@Override
			//while문으로 값 가져올 때
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					WebtoonVO vo = new WebtoonVO();
					vo.setW_no(rs.getInt("w_no"));
					vo.setW_title(rs.getNString("w_title"));
					vo.setW_story(rs.getNString("w_story"));
					vo.setW_thumbnail(rs.getNString("w_thumbnail"));
					vo.setW_plat_no(rs.getInt("plat_no"));
					list.add(vo);
				}
				return 1;
			}
		});
		
		return list;
	}
	// 검색 결과
	public static ArrayList<SearchWebtoonVO> selSearchList(SearchWebtoonVO vo, int randomLength){
		ArrayList<SearchWebtoonVO> list = new ArrayList<SearchWebtoonVO>();
		String sql = 
			"select w_no, w_thumbnail, w_title, w_story, plat_name, genre_name, w_link, w_writer from w_view where "
				+ " w_title like ? "
				+ " or w_story like ? "
				+ " or w_writer like ? ";

		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setNString(1, "%"+vo.getSearchKeyword()+"%");
				ps.setNString(2, "%"+vo.getSearchKeyword()+"%");
				ps.setNString(3, "%"+vo.getSearchKeyword()+"%");
				//ps.setInt(4, randomLength);
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					SearchWebtoonVO param = new SearchWebtoonVO();
					param.setW_no(rs.getInt("w_no"));
					param.setW_title(rs.getNString("w_title"));
					param.setW_story(rs.getNString("w_story"));
					param.setW_thumbnail(rs.getNString("w_thumbnail"));
					param.setW_genre(rs.getNString("genre_name"));
					param.setW_writer(rs.getNString("w_writer"));
					param.setW_link(rs.getNString("w_link"));
					param.setW_plat_nm(rs.getNString("plat_name"));
					list.add(param);
				}
				return 1;
			}
		});
		return list;
	}
	// 웹툰 디테일
		public static WebtoonVO webtoonDetail(int w_no) {
			WebtoonVO vo = new WebtoonVO();
			String sql = "SELECT distinct a.w_no, A.w_thumbnail, A.w_link, A.w_title, a.w_story as w_story, B.plat_name, d.w_writer "
					+" FROM t_webtoon A "
					+" INNER JOIN t_platform B " 
					+" ON A.plat_no = B.plat_no "
					+" INNER JOIN t_w_writer C "
					+" ON A.w_no = C.w_no "
                   +" inner join "
                   +" (select w_no,listagg(w_writer, ',') within group (order by w_no) as w_writer from t_w_writer group by w_no) D "
                   +" on a.w_no = d.w_no "
					+" WHERE A.w_no = ? ";

			JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

				@Override
				public void prepared(PreparedStatement ps) throws SQLException {
					ps.setInt(1, w_no);
				}

				@Override
				public int executeQuery(ResultSet rs) throws SQLException {
					if(rs.next()) {
						vo.setW_link(rs.getNString("w_link"));
						vo.setW_thumbnail(rs.getNString("w_thumbnail"));
						vo.setW_title(rs.getNString("w_title"));
						vo.setW_story(rs.getNString("w_story"));
						vo.setW_plat_nm(rs.getNString("plat_name"));
						vo.setW_writer(rs.getNString("w_writer"));
					}
					return 1;
				}
			});
			return vo;
		}
	
}