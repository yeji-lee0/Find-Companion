<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<script type="text/javascript" src="<c:url value='/resources/js/writePost.js'/>"></script>

			<title>글쓰기</title>
			<link rel="stylesheet" href="<c:url value='/resources/css/reset.css'/>">
			<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
			<link rel="stylesheet" href="<c:url value='/resources/css/postEdit.css'/>">
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
				<section id="write-section">
					<form action="writePostOk" method="post">

						<div>
							<input type="hidden" name="memberId" value="${loginUser.id}">

							<select name="categoryId" id="category-option" class="input">
								<option value="0">카테고리</option>
								<c:forEach var="category" items="${categorys}">
									<option value="${category.categoryId }">
										${category.categoryName }
									</option>
								</c:forEach>
							</select>




							<label for="title-input">제목</label>
							<input class="input" type="text" id="title-input" name="title" placeholder="제목을 적어주세요." maxlength="100">


							<label for="meeting-date-input">일정</label>
							<input class="input" type="date" id="meeting-date-input" name="meetingDate" max="9999-12-31">

						</div>

						<!-- <label for="content-input">내용</label> -->
						<textarea class="input" name="content" id="content-input" placeholder="내용을 적어주세요."
							maxlength="1000"></textarea>
						<div>0/1000</div>


						<div id="taste-container" style="display: none;">
							<!-- 취향 선택 필드가 동적으로 삽입될 영역 -->
						</div>

						<div class="btns">
							<button class="button" type="button" onclick="window.history.back();">취소</button>
							<input type="submit" value="등록" id="upload-btn" class="button">

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