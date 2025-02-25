<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
			<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
				<!DOCTYPE html>
				<html lang="ko">

				<head>
					<meta charset="UTF-8">
					<meta name="viewport" content="width=device-width, initial-scale=1.0">
					<script type="text/javascript" src="<c:url value='/resources/js/editPost.js'/>"></script>
					<title>글수정</title>
					<link rel="stylesheet" href="<c:url value='/resources/css/reset.css'/>">
					<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
					<link rel="stylesheet" href="<c:url value='/resources/css/postEdit.css'/>">

					<!-- 폰트 -->
					<link rel="preconnect" href="https://fonts.googleapis.com">
					<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
					<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap"
						rel="stylesheet">
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
					<section id="edit-section">
						<form action="editPostOk" method="post">

							<div>
								<input type="hidden" name="postId" value="${post.postId}">
								<input type="hidden" name="memberId" value="${post.memberId}">

								<select class="input" name="categoryId" id="category-option">
									<option value="0">카테고리</option>
									<c:forEach var="category" items="${categorys}">
										<option value="${category.categoryId }" ${post.categoryId==category.categoryId ? 'selected' :''}>
											${category.categoryName }
										</option>
									</c:forEach>
								</select>

								<label for="title-input">제목</label>
								<input class="input" type="text" id="title-input" name="title" value="${post.title}"
									placeholder="제목을 적어주세요." maxlength="100">



								<label for="meeting-date-input">일정</label>
								<input class="input" type="date" id="meeting-date-input"
									value="<fmt:formatDate value='${post.meetingDate}' pattern='yyyy-MM-dd' />" name="meetingDate"
									max="9999-12-31">
							</div>

							<!-- <label for="content-input">내용</label> -->
							<textarea class="input" name="content" id="content-input" placeholder="내용을 적어주세요."
								maxlength="1000">${post.content}</textarea>
							<div>${fn:length(post.content)}/1000</div>


							<!-- 취향 선택 -->
							<div id="taste-container">
								<c:forEach var="categoryEntry" items="${post.groupedtaste}">
									<fieldset class="category-div">
										<legend>
											<h3>${categoryEntry.key} 취향 선택</h3>
										</legend> <!-- 카테고리 이름 출력 -->


										<c:forEach var="fieldEntry" items="${categoryEntry.value}">

											<div class="field-ul">
												<h4>${fieldEntry.key}</h4><!-- 필드 이름 출력 -->

												<div class="taste-ul">

													<c:forEach var="taste" items="${fieldEntry.value}">


														<c:if test="${ not empty taste.tasteName }">

															<input type="checkbox" name="selectedTastes" value="${taste.tasteId}"
																id="taste-${taste.tasteId}" ${taste.selected ?'checked':''}
																class="taste-input hidden-checkbox " data-field="${fieldEntry.key}">
															<label for="taste-${taste.tasteId}" class="styled-checkbox">${taste.tasteName}</label>
															<!-- 취향 이름 출력 -->
														</c:if>
													</c:forEach>
												</div>
											</div>


										</c:forEach>

									</fieldset>
								</c:forEach>
							</div>

							<div class="btns">
								<button class="button" type="button" onclick="window.history.back();">취소</button>
								<input class="button" type="submit" value="수정" id="update-btn">
							</div>
						</form>
					</section>
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