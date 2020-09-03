package com.allWebtoon.view;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allWebtoon.api.KakaoAPI;
import com.allWebtoon.dao.UserDAO;
import com.allWebtoon.util.Const;
import com.allWebtoon.util.MyUtils;
import com.allWebtoon.util.ViewResolver;
import com.allWebtoon.vo.UserVO;

@WebServlet("/login")
public class LoginSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//플랫폼 넘버 구분(네이버 난수생성용, 로그인 창 들어갈 시 미리 난수값 받아서 attr에 넣어줌)
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		
		String platNo = request.getParameter("platNo");
		if(platNo != null && platNo.equals("1")) {
			String code = request.getParameter("code");
			String access_token = KakaoAPI.getAccessToken(code);
			HashMap<String, Object> userInfo = KakaoAPI.getUserInfo(access_token);
		    System.out.println("userInfo(HashMap<String, Object> : ");
		    System.out.println(userInfo);
		    response.sendRedirect("/home");
			return;
		}
		request.setAttribute("state", state);
		ViewResolver.accessForward("login", request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String encrypt_pw = MyUtils.encryptString(user_pw);
		
		UserVO param = new UserVO();
		
		
		param.setUser_id(user_id);
		param.setUser_password(encrypt_pw);
		
		int result = UserDAO.login(param);


		if(result != 1) {		//에러처리
			String msg = null;
			switch(result) {
				case 0:
				msg = "에러가 발생했습니다";
				break;
				case 2:
				msg = "비밀번호가 틀렸습니다.";
				break;
				case 3:
				msg = "아이디가 없음";
				break;
			}
			request.setAttribute("msg",msg);
			request.setAttribute("user_id", user_id);
			doGet(request,response);
			return;
		}
		
		HttpSession hs = request.getSession();
		hs.setAttribute(Const.LOGIN_USER,param);
		
		System.out.println("로그인성공");
		response.sendRedirect("/");
	}

}
