<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
      <!DOCTYPE html>
      <html>

      <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>회원정보 수정</title>
        <script type="text/javascript" src="<c:url value='/resources/js/updateMemberInfo.js'/>"></script>
        <link rel="stylesheet" href="<c:url value='/resources/css/reset.css'/>">
        <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
        <link rel="stylesheet" href="<c:url value='/resources/css/profileUpdate.css'/>">
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


              <a href="<c:url value='/post/list' />" class="nav-item button">게시판</a>
              <a href="<c:url value='/member/profile' />" class="nav-item button">프로필</a>
              <a href="<c:url value='/member/logout' />" class="nav-item button">로그아웃</a>

            </div>
          </nav>
        </header>
        <main>
          <section id="modi-div">


            <h1>회원정보 수정</h1>
            <form action="updateOk" method="post">

              <div class="id-div">
                <label for="id-input">아이디</label>
                <input class="input" type="text" name="id" id="id-input" value="${loginUser.id}" readonly required />

              </div>

              <div class="password-div">

                <label for="password-input">비밀번호*</label>
                <input class="input" type="password" name="pw" id="password-input" minlength="8" maxlength="20"
                  value="${loginUser.pw}" required />
                <span class="check"></span>
                <span class="msg"></span>




                <label for="password-input2">비밀번호 확인*</label>
                <input class="input" type="password" id="password-input2" minlength="8" maxlength="20" required />
                <span class="check"></span>
                <span class="msg"></span>


              </div>

              <div class="name-div">
                <label for="name-input">이름</label>
                <input class="input" type="text" name="name" id="name-input" value="${loginUser.name}" readonly
                  required>

              </div>


              <div class="gender-div">
                <label for="gender-input">성별</label>
                <input class="input" type="text" id="gender-input" name="gender" value="${loginUser.gender}" readonly checked>

              </div>

              <div class="birth-div">
                <label for="birth-input">생년월일</label>
                <input class="input" type="text" name="birth" id="birth-input"
                  value="<fmt:formatDate value='${loginUser.birth}' pattern='yyyy-MM-dd' />" readonly>
              </div>

              <div class="email-div">
                <label for="email-input">이메일*</label>
                <input class="input" type="email" name="email" id="email-input" value="${loginUser.email}"
                  maxlength="50" required>
                <span class="check"></span>
                <span class="msg"></span>
              </div>

              <input type="submit" value="수정" class="modi-btn button">
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