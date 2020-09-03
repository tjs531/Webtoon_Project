package com.allWebtoon.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allWebtoon.dao.WebtoonListDAO;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.WebtoonVO;

@WebServlet("/webtoon/detail")
public class WebtoonDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strW_no = request.getParameter("w_no");
		int w_no = Integer.parseInt(strW_no);
		
		WebtoonVO data = WebtoonListDAO.webtoonDetail(w_no);
		request.setAttribute("data", data);
		
		ViewResolver.viewForward("webtoonDetail", request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}