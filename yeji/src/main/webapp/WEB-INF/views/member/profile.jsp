<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

			<!DOCTYPE html>
			<html>

			<head>
				<meta charset="UTF-8">
				<meta name="viewport" content="width=device-width, initial-scale=1.0">
				<script type="text/javascript" src="<c:url value='/resources/js/profile.js'/>"></script>

				<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
				<title>프로필</title>
				<link rel="stylesheet" href="<c:url value='/resources/css/reset.css'/>">
				<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
				<link rel="stylesheet" href="<c:url value='/resources/css/profile.css'/>">
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
					<!-- <h1>프로필</h1> -->
					<div class="main-content">

						<!-- 회원정보 ---------------------------------------------------->
						<section id="profile-section">
							<div>
								<h1>회원정보</h1>
								<c:if test="${loginUser.id == user.id || loginUser.id==param.id}">
									<a class="button" href="<c:url value='/member/update' />">회원정보 수정<i
											class="bi bi-chevron-double-right"></i></a>
								</c:if>
							</div>
							<div>
								<label for="user-id">아이디</label>
								<input id="user-id" class="input" value="${user.id}" readonly>


								<label for="user-gender">성별</label>
								<input id="user-gender" class="input" value="${user.gender}" readonly>

								<label for="user-birth">출생연도</label>
								<input id="user-birth" class="input" value="<fmt:formatDate value='${user.birth}' pattern='yyyy' />"
									readonly />

								<label for="user-regdate">가입일</label>
								<input id="user-regdate" class="input" value="<fmt:formatDate value='${user.regDate}'
									pattern='yyyy/MM/dd' />" readonly />

								<label for="user-postCount">글 개수</label>
								<input id="user-postCount" class="input" value="${user.postCount}" readonly>

								<label for="user-commCount">댓글 개수</label>
								<input id="user-commCount" class="input" value="${user.commCount}" readonly>
							</div>



						</section>

						<!-- 회원의 취향 목록 ---------------------------------------------------->
						<section id="taste-list-div">
							<div>
								<h1>취향 목록</h1>
								<c:if test="${loginUser.id == user.id || loginUser.id==param.id}">
									<button class="button" id="edit-taste-btn">취향 수정 <i class="bi bi-chevron-down"></i></button>
								</c:if>
							</div>

							<div>
								<c:forEach var="categoryEntry" items="${user.groupedtaste}">

									<fieldset class="category-div">
										<legend>
											<h3>[ ${categoryEntry.key} ]</h3>
										</legend> <!-- 카테고리 이름 출력 -->

										<ul class="field-ul">
											<c:forEach var="fieldEntry" items="${categoryEntry.value}">
												<li>
													<h4>${fieldEntry.key}</h4> <!-- 필드 이름 출력 -->

													<ul class="taste-ul">
														<c:forEach var="taste" items="${fieldEntry.value}">
															<c:if test="${taste.selected}">
																<li>${taste.tasteName}</li> <!-- 취향 이름 출력 -->
															</c:if>
														</c:forEach>
													</ul>
												</li>
											</c:forEach>
										</ul>

									</fieldset>
								</c:forEach>
							</div>


						</section>

						<section id="taste-form-div">
							<h1>취향 수정</h1>
							<form action="tasteOk" method="post">
								<c:forEach var="categoryEntry" items="${user.groupedtaste}">
									<fieldset class="category-div form-category">

										<legend>
											<h3>[ ${categoryEntry.key} ]</h3>
										</legend> <!-- 카테고리 이름 출력 -->


										<c:forEach var="fieldEntry" items="${categoryEntry.value}">
											<div class="field-ul">
												<h4>${fieldEntry.key}</h4> <!-- 필드 이름 출력 -->
												<div class="taste-check-div">
													<div>
														<input type="checkbox" class="all-check-input hidden-checkbox"
															data-field="${categoryEntry.key}-${fieldEntry.key}"
															id="all-${categoryEntry.key}-${fieldEntry.key}">
														<label class="styled-checkbox" for="all-${categoryEntry.key}-${fieldEntry.key}">모두
															선택</label>
													</div>

													<c:forEach var="taste" items="${fieldEntry.value}">
														<c:if test="${ not empty taste.tasteName }">
															<div>
																<input type="checkbox" name="selectedTastes" value="${taste.tasteId}"
																	id="taste-${taste.tasteId}" ${taste.selected ? 'checked' : '' }
																	class="taste-input hidden-checkbox "
																	data-field="${categoryEntry.key}-${fieldEntry.key}">
																<label class="styled-checkbox" for="taste-${taste.tasteId}">${taste.tasteName}</label>
																<!-- 취향 이름 출력 -->
															</div>
														</c:if>
													</c:forEach>
												</div>
											</div>
										</c:forEach>


									</fieldset>
								</c:forEach>

								<input class="button" type="reset" value="취소" id="cancel-btn">
								<input class="button" type="submit" value="취향 수정" id="update-btn">
							</form>
						</section>

						<div id="post-comm">
							<!-- 회원의 글목록 -------------------------------------------------->
							<section id="post-section">
								<h1>글목록</h1>
								<table>
									<thead>
										<tr>
											<th>번호</th>
											<th>카테고리</th>
											<th>제목</th>
											<th>등록일</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="post" items="${user.postList }">
											<tr>
												<td>${post.postId }</td>
												<td>${post.categoryName }</td>
												<td><a href="<c:url value='/post/detail?pi=${post.postId }' />">
														${post.title }</a>
												</td>
												<td>
													<fmt:formatDate value="${post.uploadTime}" pattern="yyyy/MM/dd" />
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

								<nav class="pagination">
									<c:if test="${currentPostPage > 1}">
										<a href="<c:url value='/member/profile'>
			                    <c:param name='id' value='${param.id}' />
			                    <c:param name='cpp' value='${currentPostPage- 1}' />
			                    <c:param name='ccp' value='${param.ccp }' />
			                </c:url>"><i class="bi bi-chevron-left"></i></a>

									</c:if>
									<c:forEach begin="${postStartPage}" end="${postEndPage}" var="i">

										<a href="<c:url value='/member/profile'>
			                    <c:param name='id' value='${param.id}' />
			                    <c:param name='cpp' value='${i}' />
			                    <c:param name='ccp' value='${param.ccp}' />
			                </c:url>" class="${i == currentPostPage ? 'active' : ''}">${i}</a>
									</c:forEach>
									<c:if test="${currentPostPage < postTotalPage}">
										<a href="<c:url value='/member/profile'>
			                    <c:param name='id' value='${param.id}' />
			                    <c:param name='cpp' value='${currentPostPage + 1}' />
			                    <c:param name='ccp' value='${param.ccp}' />
			                </c:url>"><i class="bi bi-chevron-right"></i></a>

									</c:if>
								</nav>
							</section>

							<!-- 회원의 댓글목록 -------------------------------------------------->
							<section id="comm-section">
								<h1>댓글목록</h1>
								<table>
									<thead>
										<tr>
											<th>번호</th>
											<th>내용</th>
											<th>등록일</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="comm" items="${user.commList }">
											<tr>
												<td>${comm.postId }</td>
												<td><a href="<c:url value='/post/detail?pi=${comm.postId }' />">${comm.content }</a></td>
												<td>
													<fmt:formatDate value="${comm.uploadTime}" pattern="yyyy/MM/dd" />
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<div class="pagination">
									<c:if test="${currentCommPage > 1}">
										<a href="<c:url value='/member/profile'>
																<c:param name='id' value='${param.id}' />
																<c:param name='cpp' value='${param.cpp}' />
																<c:param name='ccp' value='${currentCommPage - 1}' />
															</c:url>">
											<i class="bi bi-chevron-left"></i>
										</a>

									</c:if>

									<c:forEach begin="${commStartPage}" end="${commEndPage}" var="i">
										<a href="<c:url value='/member/profile'>
												<c:param name='id' value='${param.id}' />
												<c:param name='cpp' value='${param.cpp}' />
												<c:param name='ccp' value='${i}' /></c:url>" class=" ${i==currentCommPage ? 'active' : '' }">${i}</a>

									</c:forEach>

									<c:if test="${currentCommPage < commTotalPage}">
										<a href="<c:url value='/member/profile'>
																	<c:param name='id' value='${param.id}' />
																	<c:param name='cpp' value='${param.cpp}' />
																	<c:param name='ccp' value='${currentCommPage + 1}' />
														</c:url>">
											<i class="bi bi-chevron-right"></i></a>

									</c:if>
								</div>
							</section>

						</div>
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