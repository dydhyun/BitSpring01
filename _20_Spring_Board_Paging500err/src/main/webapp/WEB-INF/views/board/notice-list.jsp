<%--
  Created by IntelliJ IDEA.
  User: bit
  Date: 2024-07-12
  Time: 오후 6:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%--작성일에 표시 될 날짜형식 변경하기 위한 라이브러리 --%>
<html>
<head>
</head>
<body>
    <div>

        <jsp:include page="${pageContext.request.contextPath}/header.jsp"></jsp:include>

        <main>
            <div class="container mt-5 w-75">
                <h4>공지사항</h4>
            </div>
            <div class="container mt-3 mb-5 w-75 card-wrapper">

                <c:forEach items="${noticeList}" var="eachNotice">
                    <div class="card" style="width: 18rem;">
                        <svg class="bd-placeholder-img card-img-top" width="100%" height="180" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Image cap" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#868e96"></rect></svg>
                        <div class="card-body">
                            <h5 class="card-title">${eachNotice.title}</h5>
                            <p class="card-text">작성일:

                            </p>
                            <a href="/board/update-cnt.do?id=${eachNotice.id}&type=notice" class="btn btn-outline-secondary btn-sm">자세히 보기</a>
                        </div>
                    </div>
                </c:forEach>

            </div>
            <div class="container mt-3 w-50">
                <form id="searchForm" action="/board/notice-list.do" method="post">
                    <input type="hidden" name="pageNum" value="${page.cri.pageNum}">
                    <input type="hidden" name="amount" value="${page.cri.amount}">
                    <input type="hidden" name="endPage" value="${page.cri.endPage}">
                    <div class="row d-flex justify-content-center">
                        <div class="col-6">
                            <div class="row">
                                <div class="col-11">
                                    <input type="text" class="form-control w-100" name="searchKeyword" value="${searchMap.searchKeyword}">
                                </div>
                                <div class="col-1 d-flex align-items-center">
                                    <i class="bi bi-search" id="searchIcon"></i>
                                    <button type="submit" id="btnSearch">검색</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

<%--            ne 혹은 != 둘 다 사용가능하다--%>
<%--            <c:if test="${loginMember ne null and loginMember.role eq 'ADMIN'}">--%>
            <c:if test="${loginMember != null && loginMember.role == 'ADMIN'}">
                <div class="container mt-3 mb-5 w-50 d-flex justify-content-end">
                    <button type="button" class="btn btn-outline-secondary" onclick="location.href='/board/post.do'">글 등록</button>
                </div>
            </c:if>
        </main>

        <jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>

    </div>
<script>
    $(() => {
       $("#search-icon").on("click", (e) => {
           $("#search-form").submit();
       });

       $(window).on("scroll", (e) => {
           // 현재 스크롤의 위치
           const scrollTop = $(window).scrollTop();
           // 브라우저의 세로길이 (스크롤 길이는 포함되지 않음)
           const windowHeight = window.innerHeight;
           // 웹 문서의 세로길이 (스크롤 길이 포함)
           const documentHeight = document.documentElement.scrollHeight;

           // 스크롤이 바닥에 닿았는지 여부
           const isBottom = documentHeight <= scrollTop + windowHeight;

           console.log(`scrollTop:  \${scrollTop}`);
           console.log(`windowHeight:  \${windowHeight}`);
           console.log(`documentHeight:  \${documentHeight}`);
           console.log(`isBottom:  \${isBottom}`);
       });

    });

</script>
</body>
</html>
