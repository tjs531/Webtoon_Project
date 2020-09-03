package com.allWebtoon.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.WebtoonVO;
@WebServlet("/home")
public class HomeSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<WebtoonVO> list = new ArrayList<WebtoonVO>();
		
		list = WebtoonListDAO.selRandomWebtoonList(list, 1, 5);
	//	for(WebtoonVO vo : WebtoonListDAO.selRandomWebtoonList(list, 1, 5)) {
	//		list.add(vo);
	//	}
	//	list = WebtoonListDAO.selRandomWebtoonList(list, 3, 5);
	//	list = WebtoonListDAO.selRandomWebtoonList(list, 4, 5);
		
		request.setAttribute("list", list);
		
		ViewResolver.viewForward("home", request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
