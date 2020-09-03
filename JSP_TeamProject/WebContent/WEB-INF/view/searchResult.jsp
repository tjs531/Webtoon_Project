<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모두의 웹툰(검색 결과)</title>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Cute+Font&family=Noto+Sans+KR&family=Noto+Serif+KR:wght@600&display=swap" rel="stylesheet">
<style type="text/css">
   .container {width: 100%; margin: 0 auto; font-family: 'Noto Serif KR', serif ;}
	#logo{width: 250px; cursor: pointer; float: left; height: 80px;}
	.header {width: 1200px; padding: 10px; height: 100px; margin: 0 auto;}
	.header #search {margin-right: 10px;}
	.header #login {margin-right: 10px;}
	.topMenu{width:400px; padding: 20px; float: right; margin-right: 10px; margin-top: 15px;}
	.topMenu #search{padding: 10px;}
	.topMenu button{padding:10px;}
    .box1 .web1 h2{margin-left: 20px;}
    .container section {width: 1200px; padding: 10px; margin: 0 auto; clear: both;}
    h2{margin-top: 0px;}
    #webtoonContainer {width: 500px; border: 1px solid black; margin: 5px; padding: 10px; position: relative;}
    ul {list-style-type: none; clear: both;}
    ul li:nth-child(1) {float: left; padding: 30px; padding-top: 0px;}
    ul li img{ border-radius: 10%;
    transform: scale(1.1);
    -webkit-transform: scale(1.1);
    -moz-transform: scale(1.1);
    -ms-transform: scale(1.1);
    -o-transform: scale(1.1);
    transition: all 0.2s ease-in-out;}
    ul li img:hover {cursor: pointer;
    transform: scale(1.2);
    -webkit-transform: scale(1.2);
    -moz-transform: scale(1.2);
    -ms-transform: scale(1.2);
    -o-transform: scale(1.2);}
    ul li:nth-child(2) {font-weight: bold; font-size: 1.2em; padding-top: 1px;}
    ul li:nth-child(3) {line-height: 25px;}
    ul li:not(:first-child){margin-top: 15px;}
    ul li .list{color: gray; font-weight: gray; font-weight: bold;}
    ul .thumbnail{width: 200px; height: 200px;}
</style>
</head>
<body>
   <div class="container">
      <div class="header">
         <img alt="모두의 웹툰" id="logo" src="/images/logo.png" onclick="goHome()">            
            <!-- 사이트 대표 아이콘 홈으로 돌아오는 링크 -->
         <div class="topMenu">
            <input type="text" id="search" placeholder="웹툰, 작가를 검색하세요" onkeydown="moveToResult()" value="${data.searchKeyword}">
               <button id="btn_login" onclick="moveToLogin()">로그인</button>
               <button id="signin" onclick="moveToJoin()">회원가입</button>
            </div>
        </div>
        <section>
           <div class="box1">
                <div class="web1"><h2>상위 검색 결과</h2></div>
                <hr>
         <c:forEach items="${result}" var="item">
            <div class="webtoonContainer">
               <ul class="itemRow">
                  <li><img class="thumbnail" src=" ${item.w_thumbnail }" onclick="moveToDetail(${item.w_no	})"></li>
                  <li><a href="/webtoon/detail?w_no=${item.w_no}">${item.w_title }</a></li>
                  <li><span class="list" id="ctnt">내용</span> &nbsp; ${item.w_story }</li>
                  <li><span class="list">작가</span> &nbsp; ${item.w_writer}</li>
                  <li><span class="list">장르</span> &nbsp; ${item.w_genre}</li>
               </ul>
            </div>
         </c:forEach>
            </div>
        </section>
   </div>
   <script type="text/javascript">
	   function moveToResult() {
			if(event.keyCode == 13){
				var result = search.value
				location.href = '/searchResult?result='+result
			}
		}
      function moveToLogin() {
         location.href = '/login'
      }
       function moveToJoin() {
         location.href = '/join'
      }
       function goHome() {
		location.href = '/home'
	}
       function moveToDetail(w_no) {
		location.href = '/webtoon/detail?w_no='+w_no
	}
   </script>
</body>
</html>