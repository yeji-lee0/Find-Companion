<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="ko">

    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>로그인</title>
      <script type="text/javascript" src="<c:url value='/resources/js/login.js'/>"></script>
      <link rel="stylesheet" href="<c:url value='/resources/css/reset.css'/>">
      <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
      <link rel="stylesheet" href="<c:url value='/resources/css/login.css'/>">
      <!-- 폰트 -->
      <link rel="preconnect" href="https://fonts.googleapis.com">
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
      <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
      <script>
        window.addEventListener("load", function () {
          var message = '${message}'; // 회원가입 환영 메시지
          if (message) {
            alert(message);
          }
          const errorMessage = '${errorMessage}'; // 세션에서 에러 메시지 가져오기
          if (errorMessage) {
            alert(errorMessage);
          }
        });
      </script>


    </head>

    <body>
      <header>


        <nav class="header-nav">
          <a id="logo" href="<c:url value='/' />">동행인 구해요?</a>


          <div class="nav-links">
            <a href="<c:url value='/' />" class="nav-item button">홈</a>
          
              <a href="<c:url value='/member/join'/>" class="nav-item button">회원가입</a>
              <a href="<c:url value='/member/login' />" class="nav-item button">로그인</a>
        
            
          </div>
        </nav>
      </header>
      <main>

        <section id="login-div">
          <h1>로그인</h1>
          <form action="login" method="post">

            
              <label for="id-input">아이디</label>
              <input class="input" type="text" name="id" id="id-input" value="${id}" required autofocus>

           
            
              <label for="password-input">비밀번호</label>
              <input class="input" type="password" name="pw" id="password-input" required>

           
          
              <span class="msg">${loginFailMsg}</span>
           

           
              <input type="submit" value="로그인" class="login-btn button">
              <a href="<c:url value='/member/join'/>">회원가입</a>
            </div>
          </form>
        </section>
      </main>

      <footer>

        <div>
          <p>&copy; 2024 동행인 구해요? All rights reserved.</p>
          <p>문의: support@donghaengin.com | 전화: 010-1234-5678</p>
        </div>
        <div>
          <a href="#">사이트 소개 |</a>
          <a href="#"> 이용약관 |</a>
          <a href="#"> 개인정보 처리방침</a>
        </div>
        <div class="social-media">
          <a href="https://facebook.com"><i class="bi bi-facebook"></i></a>
          <a href="https://twitter.com"><i class="bi bi-twitter"></i></a>
          <a href="https://instagram.com"><i class="bi bi-instagram"></i></a>
        </div>

      </footer>
    </body>

    </html>