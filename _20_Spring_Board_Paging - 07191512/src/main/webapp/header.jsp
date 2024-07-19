<%--
  Created by IntelliJ IDEA.
  User: bitcamp
  Date: 24. 7. 12.
  Time: 오후 4:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
    <header class="container">
        <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
            <button type="button" class="btn btn-outline-light btn-lg text-secondary" onclick="location.href='..'">홈</button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/board/free-list.do">자유게시판</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/board/notice-list.do">공지사항</a>
                    </li>
                    <c:choose>
                        <c:when test="${loginMember eq null}">
                            <li class="nav-item">
                                <a class="nav-link" href="/member/login.do">로그인</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/member/join.do">회원가입</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link">${loginMember.nickname}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/member/logout.do">로그아웃</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </nav>
    </header>
</body>
</html>
