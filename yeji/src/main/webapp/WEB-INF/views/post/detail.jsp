<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
			<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
				<!DOCTYPE html>
				<html lang="ko">

				<head>
					<meta charset="UTF-8">
					<meta name="viewport" content="width=device-width, initial-scale=1.0">
					<script type="text/javascript" src="<c:url value='/resources/js/postDetail.js'/>"></script>
					<title>상세보기</title>
					<link rel="stylesheet" href="<c:url value='/resources/css/reset.css'/>">
					<link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
					<link rel="stylesheet" href="<c:url value='/resources/css/postDetail.css'/>">
					<link rel="stylesheet"
						href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
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
					<main>
						<div id="main-content">

							<section id="post-section">

								<div>

									<h3>[ ${post.categoryName } ]</h3>
									<div>
										<h1>${post.title }</h1>

										<div>
											<i class="bi bi-person-fill"></i> <a href="<c:url value='/member/profile?id=${post.memberId }'/>">
												${post.memberId }
											</a>

											<i class="bi bi-pencil"></i>
											<fmt:formatDate value="${post.uploadTime }" pattern="yyyy.MM.dd.HH:mm" />

										</div>
									</div>




									<textarea disabled class="input">${post.content }</textarea>

									<label for="date-input"><i class="bi bi-calendar-check"></i></label>
									<input class="input" id="date-input" type="date" disabled
										value="<fmt:formatDate value='${post.meetingDate }' pattern='yyyy-MM-dd' />">



								</div>

								<div>
									<c:forEach var="categoryEntry" items="${post.groupedtaste}">

										<fieldset class="category-div">
											<!-- <legend>
												<h3>${categoryEntry.key}</h3>
											</legend> 카테고리 이름 출력 -->

											<ul class="field-ul">
												<c:forEach var="fieldEntry" items="${categoryEntry.value}">
													<li>
														<h4>${fieldEntry.key}</h4> <!-- 필드 이름 출력 -->

														<ul class="taste-ul">
															<c:forEach var="taste" items="${fieldEntry.value}">
																<c:if test="${taste.selected}">
																	<li class="button">${taste.tasteName}</li> <!-- 취향 이름 출력 -->
																</c:if>
															</c:forEach>
														</ul>
													</li>
												</c:forEach>
											</ul>
									</c:forEach>
								</div>
								<nav>
									<a class="button" href="<c:url value='/post/list'/>">목록</a>

									<c:if test="${loginUser.id == post.memberId}">
										<a class="button" href="<c:url value='/post/editPost?pid=${post.postId}'/>">수정</a>

										<form action="<c:url value='/post/deletePost'/>" method="post">
											<input class="button" type="hidden" name="postId" value="${post.postId}">
											<input class="button" type="submit" value="삭제" onclick="return confirm('게시글을 삭제하시겠습니까?');">
										</form>

									</c:if>
								</nav>
							</section>


							<section id="comm-section">
								<h1>댓글</h1>

								<table id="comm-table">
									<c:forEach var="comm" items="${post.commList }">
										<tr>
											<td>${comm.content}</td>
											<td>
												<div>
													<i class="bi bi-person-fill"></i>
													<a href="<c:url value='/member/profile?id=${comm.memberId }'/>">
														${comm.memberId}</a>
												</div>

												<fmt:formatDate value="${comm.uploadTime }" pattern="yyyy.MM.dd.HH:mm" />
											</td>


											<td>

												<c:if test="${loginUser.id == comm.memberId}">

													<button class="edit-btn button" id="edit-${comm.commId}-btn"
														onclick="toggleEditForm(${comm.commId})">수정</button>

													<form action="deleteComm" method="post">
														<input type="hidden" name="postId" value="${post.postId}" />
														<input type="hidden" name="commId" value="${comm.commId}" />
														<input class="button" type="submit" value="삭제" onclick="return confirm('댓글을 삭제하시겠습니까?');" />
													</form>
												</c:if>


											</td>
										</tr>
										<tr id="edit-form-${comm.commId}" style="display: none;">

											<td colspan="3">
												<form action="updateComm" method="post" class="update-comm-form">
													<input type="hidden" name="postId" value="${post.postId }">
													<input type="hidden" name="commId" value="${comm.commId}">
													<textarea class="input" name="content" maxlength="300" required>${comm.content}</textarea>

													<!-- <span>
														${fn:length(comm.content)}/300
													</span> -->


													<input class="button" type="submit" value="수정">
													<input class="button" type="reset" value="취소" onclick="toggleEditForm(${comm.commId})">

												</form>
											</td>



										</tr>
									</c:forEach>
								</table>

								<form action="writeComm" method="post">
									<input type="hidden" name="postId" value="${post.postId }">
									<input type="hidden" name="memberId" value="${loginUser.id }">
									<textarea class="input" name="content" placeholder="댓글을 입력해주세요." maxlength="300" required></textarea>
									<span>0/300</span>
									<input class="button" type="submit" value="등록" disabled>
								</form>


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
					<script>
						// 수정 폼의 표시 여부를 토글하는 함수
						function toggleEditForm(commId) {
							var form = document.getElementById("edit-form-" + commId);
							form.style.display = (form.style.display === "none") ? "table-row" : "none";
						}
					</script>
				</body>

				</html>