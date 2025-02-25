<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="ko">

    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>회원가입</title>

      <link rel="stylesheet" href="<c:url value='/resources/css/reset.css'/>">
      <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
      <link rel="stylesheet" href="<c:url value='/resources/css/join.css'/>">
      <script type="text/javascript" src="<c:url value='/resources/js/join.js'/>"></script>
      <!-- 폰트 -->
      <link rel="preconnect" href="https://fonts.googleapis.com">
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
      <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
    </head>

    <body>
      <header>


        <nav class="header-nav">
          <a id="logo" href="<c:url value='/' />">동행인 구해요?</a>


          <div class="nav-links">
            <a href="<c:url value='/' />" class="nav-item button">홈</a>
            <c:if test="${ empty loginUser }">
              <a href="<c:url value='/member/join'/>" class="nav-item button">회원가입</a>
              <a href="<c:url value='/member/login' />" class="nav-item button">로그인</a>
            </c:if>
            <c:if test="${not empty loginUser }">
              <a href="<c:url value='/post/list' />" class="nav-item button">게시판</a>
              <a href="<c:url value='/member/profile' />" class="nav-item button">프로필</a>
              <a href="<c:url value='/member/logout' />" class="nav-item button">로그아웃</a>
            </c:if>
          </div>
        </nav>
      </header>
      <main>
        <section id="join-div">
          <h1>회원가입</h1>
          <form action="joinOk" method="post">


            <div class="id-div">


              <label for="id-input">아이디</label>
              <input type="text" name="id" id="id-input" maxlength="20" required autofocus class="input" />

              <input type="button" value="중복 확인" class="id-check-btn button" />
              <span class="check"></span>
              <span class="msg">

              </span>
            </div>


            <div class="password-div">

              <label for="password-input">비밀번호</label>
              <input type="password" name="pw" id="password-input" maxlength="20" required class="input" />
              <span class="check"> </span>
              <span class="msg"></span>

              <label for="password-input2">비밀번호 확인</label>
              <input type="password" id="password-input2" maxlength="20" required class="input" />
              <span class="check"></span>
              <span class="msg"></span>


            </div>

            <div class="name-div">
              <label for="name-input">이름</label>
              <input type="text" name="name" id="name-input" maxlength="30" required class="input">
              <span class="check"></span>
              <span class="msg"></span>
            </div>


            <div class="gender-div">
              <label for="female">성별</label>
              <div>
                <input class="hidden-radio" type="radio" name="gender" id="female" value="F" checked>
                <label class="styled-radio" for="female">여자</label>
              </div>
              <div>
                <input class="hidden-radio" type="radio" name="gender" id="male" value="M">
                <label class="styled-radio" for="male">남자</label>
              </div>

            </div>

            <div class="birth-div">
              <!-- 서버에 전송할 yyyy-MM-dd 형식의 숨겨진 input, name을 "birth"로 설정 -->
              <input type="hidden" name="birth" id="birth-input">
              <label for="year-select">생년월일</label>


              <div>
                <select name="year" id="year-select" class="input">
                </select>
                <label for="year-select">년</label>
              </div>

              <div>
                <select name="month" id="month-select" class="input">
                </select>
                <label for="month-select">월</label>
              </div>

              <div>
                <select name="day" id="day-select" class="input">
                </select>
                <label for="day-select">일</label>
              </div>


            </div>
            <div class="email-div">
              <label for="email-input">이메일</label>
              <input type="email" name="email" id="email-input" placeholder="aaa@aaa.com" maxlength="50" required
                class="input">
              <span class="check"></span>
              <span class="msg"></span>
            </div>
            <div class="button-div">
              <input type="submit" value="회원가입" class="join-btn button">
              <input type="reset" value="다시쓰기" class="button">
            </div>
          </form>
          <span>이미 계정이 있으신가요? <a href="<c:url value='/member/login' />">로그인</a></span>
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