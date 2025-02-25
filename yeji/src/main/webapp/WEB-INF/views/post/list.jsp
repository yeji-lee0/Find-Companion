<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
			<!DOCTYPE html>
			<html lang="ko">

			<head>
				<meta charset="UTF-8">
				<meta name="viewport" content="width=device-width, initial-scale=1.0">
				<title>게시판</title>
				<script type="text/javascript" src="<c:url value='/resources/js/postList.js'/>"></script>
				<link rel="stylesheet" href="<c:url value='/resources/css/reset.css'/>">
				<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
				<link rel="stylesheet" href="<c:url value='/resources/css/postList.css'/>">
				<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
				<!-- 폰트 -->
				<link rel="preconnect" href="https://fonts.googleapis.com">
				<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
				<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
			<script>
        window.onload = function() {
            const errorMessage = '${errorMessage}';
            if (errorMessage) {
                alert(errorMessage);
            }
        };
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
					<div class="main-content">

						<section id="search-post-div">
							<h1>게시글 검색</h1>
							<form action="list" method="get">

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

								<input type="search" name="q" value="${param.q}" class="input">
								<input type="submit" value="검색" class="button">
							</form>
						</section>

						<section id="post-section">
							<div>
								<h1>글목록</h1>
								<a href="<c:url value='/post/write' />" class="button">글쓰기 <i
										class="bi bi-chevron-double-right"></i></a>
							</div>
							<table>
								<thead>
									<tr>
										<th>번호</th>
										<th>카테고리</th>
										<th>제목</th>
										<th>작성자</th>
										<th>작성일</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach var="post" items="${posts }">
										<tr>

											<td>${post.postId }</td>
											<td>${post.categoryName }</td>
											<td>
												<a href="detail?pi=${post.postId }">
													${post.title }(${post.commCount })</a>
											</td>
											<td>${post.memberId }</td>
											<td>
												<fmt:formatDate value="${post.uploadTime }" pattern="yyyy/MM/dd" />
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

							<nav class="pagination">

								<c:if test="${currentPage > 1}">
									<a href="<c:url value='/post/list'>
														<c:param name='cp'>${currentPage - 1}</c:param>
														<c:param name='cid'>${param.cid }</c:param>
														<c:param name='so'>${param.so }</c:param>
														<c:param name='q'>${param.q }</c:param>
													</c:url>">
										<i class="bi bi-chevron-left"></i>
									</a>
								</c:if>

								<c:forEach begin="${startPage}" end="${endPage}" var="i">
									<a href="
									<c:url value='/post/list'>
											<c:param name='cp'>${i}</c:param>
											<c:param name='cid'>${param.cid }</c:param>
											<c:param name='so'>${param.so }</c:param>
											<c:param name='q'>${param.q }</c:param>
										</c:url>" class="${i == currentPage ? 'active' : ''}">
										${i}
									</a>
								</c:forEach>


								<c:if test="${currentPage < TotalPage}">
									<a href="
									<c:url value='/post/list'>
												<c:param name='cp'>${currentPage + 1}</c:param>
												<c:param name='cid'>${param.cid }</c:param>
												<c:param name='so'>${param.so }</c:param>
												<c:param name='q'>${param.q }</c:param>
											</c:url>">
										<i class="bi bi-chevron-right"></i>
									</a>
								</c:if>

							</nav>

						</section>
					</div>

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