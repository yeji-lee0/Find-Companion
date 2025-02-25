<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

			<!DOCTYPE html>
			<html>

			<head>
				<meta charset="UTF-8">
				<meta name="viewport" content="width=device-width, initial-scale=1.0">
				<title>홈</title>
				<link rel="stylesheet" href="<c:url value='/resources/css/reset.css'/>">
				<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
				<link rel="stylesheet" href="<c:url value='/resources/css/home.css'/>">
				<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
				<!-- GSAP 라이브러리 -->
				<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.11.1/gsap.min.js"></script>
				<script src="https://cdnjs.cloudflare.com/ajax/libs/gsap/3.11.1/ScrollToPlugin.min.js"></script>
				<script type="text/javascript" src="<c:url value='/resources/js/home.js'/>"></script>
				<!-- 폰트 -->
				<link rel="preconnect" href="https://fonts.googleapis.com">
				<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
				<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
			<script>
        window.addEventListener("load", function () {
          
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

					<section id="taste-section">
						<div>
							<div>
								<h1>1. 취향을 선택해보세요.</h1>
								<p>프로필에서 카테고리에 따라 다양한 취향을 선택할 수 있습니다.<br>
									관심사와 취향이 맞는 동행인을 찾는 데 도움이 됩니다.
								</p>
							</div>
							<a href="<c:url value='/member/profile' />" class="button">프로필 가기 <i
									class="bi bi-chevron-double-right"></i></a>
						</div>
						<div id="tags-container">
							<div class="tags">
								<c:forEach var="categoryEntry" items="${groupedtaste}">

									<span class="tag categoryName">${categoryEntry.key}</span> <!-- 카테고리 이름 출력 -->


									<c:forEach var="fieldEntry" items="${categoryEntry.value}">

										<span class="tag">${fieldEntry.key}</span><!-- 필드 이름 출력 -->


										<c:forEach var="taste" items="${fieldEntry.value}">

											<span class="tag">${taste.tasteName}</span> <!-- 취향 이름 출력 -->

										</c:forEach>

									</c:forEach>

								</c:forEach>
							</div>
						</div>
					</section>

					<section id="post-section">

						<div class="description">

							<h1>2. 게시글을 검색 & 작성해보세요.</h1>
							<p>목적에 맞는 카테고리를 선택하고 구체적인 내용을 작성해봅시다.<br>
								게시글에서도 카테고리와 관련된 취향을 선택할 수 있습니다.
							</p>

						</div>

						<form action="<c:url value='/post/list' />" method="get">

							<select name="cid" id="category-option" class="input">
								<option value="0">카테고리</option>
								<c:forEach var="category" items="${categorys}">
									<option value="${category.categoryId }" ${param.cid==category.categoryId ? 'selected' :'' }>
										${category.categoryName }
									</option>
								</c:forEach>
							</select>

							<select name="so" id="search-option" class="input">
								<option value="all" ${param.so=='all' || empty param.so ? 'selected' :''}>전체</option>
								<option value="title" ${param.so=='title' ? 'selected' :'' }>제목</option>
								<option value="content" ${param.so=='content' ? 'selected' :'' }>내용</option>
								<option value="memberId" ${param.so=='memberId' ? 'selected' :'' }>작성자</option>
							</select>

							<input type="search" name="q" value="${param.q}" placeholder="검색어를 입력하세요." maxlength="20" class="input">
							<button class="button">
								동행인 찾으러 가기 <i class="bi bi-chevron-double-right"></i>
							</button>
						</form>





						<!-- </section>

					<section>

						<a class="button">회원가입하러 가기 <i class="bi bi-chevron-double-right"></i></a>
					</section> -->
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