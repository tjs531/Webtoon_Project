package com.allWebtoon.crawling;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

public class InsertWebtoon {
	public static void main(String[] args) throws UnsupportedEncodingException, IOException, ParseException {
		ArrayList<CrawWebtoonVO> list = new ArrayList<CrawWebtoonVO>();
		
		//1. 레진 데이터 DB 담기
//		list = Naver.getNaver(list);
//		list = Lezhin.getLezhin(list);
		list = Kakao.getKakao();
		for (int i = 0; i < list.size(); i++) {
			CrawWebtoonDAO.insWebtoonList(list.get(i));
		}
//		레진 값 담기 완료(레진 웹 사이트 136개 확인 완료 및 DB 값 확인 완료)
	
	}
}