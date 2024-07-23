<%--
  Created by IntelliJ IDEA.
  User: bitcamp
  Date: 24. 7. 12.
  Time: 오후 5:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>--%>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<html>
<head>

</head>
<body>
    <div class="wrapper">
        <jsp:include page="${pageContext.request.contextPath}/header.jsp"></jsp:include>

        <main>
            <div class="container mt-5 w-50">
                <h4>자유게시판</h4>
            </div>
            <div class="container mt-3 w-50">
                <form id="search-form" action="/board/free-list.do" method="post">
                    <input type="hidden" name="pageNum" value="${page.cri.pageNum}">
                    <input type="hidden" name="amount" value="${page.cri.amount}">
                    <div class="row">
                        <div class="col-3">
                            <select class="form-select" name="searchCondition">
                                <option value="all"
                                        <c:if test="${searchMap == null || searchMap.searchCondition == 'all'}">
                                            selected
                                        </c:if>>전체</option>
                                <option value="title"
                                        <c:if test="${searchMap.searchCondition == 'title'}">
                                            selected
                                        </c:if>>제목</option>
                                <option value="content"
                                        <c:if test="${searchMap.searchCondition == 'content'}">
                                            selected
                                        </c:if>>내용</option>
                                <option value="writer"
                                        <c:if test="${searchMap.searchCondition == 'writer'}">
                                            selected
                                        </c:if>>작성자</option>
                            </select>
                        </div>
                        <div class="col-9">
                            <div class="row">
                                <div class="col-11">
                                    <input type="text" class="form-control w-100" name="searchKeyword" value="${searchMap.searchKeyword}">
                                </div>
                                <div class="col-1 d-flex align-items-center">
                                    <i class="bi bi-search" id="search-icon"></i>
                                    <button type="submit" id="btnSearch">검색</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="container mt-3 mb-5 w-50">
                <table class="table table-hover text-center">
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>등록일</th>
                            <th>조회수</th>
                        </tr>
                    </thead>
                    <tbody class="table-group-divider">
                        <c:forEach items="${freeBoardList}" var="freeBoard">
                            <tr class="board-tr" onclick="location.href='/board/update-cnt.do?id=${freeBoard.id}&type=free'">
                                <td>${freeBoard.id}</td>
                                <td>${freeBoard.title}</td>
                                <td>${freeBoard.nickname}</td>
                                <td>
                                    <javatime:format value="${freeBoard.regdate}" pattern="yyyy-MM-dd"/>
                                </td>
                                <td>${freeBoard.cnt}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <c:if test="${page.prev}">
                        <li class="page-item">
                            <a class="page-link link-secondary" aria-label="Previous" href="${page.cri.pageNum - 1}">
                                &laquo;
                            </a>
                        </li>
                    </c:if>

                    <c:forEach begin="${page.startPage}"
                               end="${page.endPage}"
                               var="number">
                        <li class="page-item">
                            <a class="page-link link-secondary" href="${number}">${number}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${page.next}">
                        <li class="page-item">
                            <a class="page-link link-secondary" aria-label="Next" href="${page.cri.pageNum + 1}">
                                &raquo;
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>

            <c:if test="${loginMember ne null}">
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
                $("input[name='pageNum']").val(1);
                $("#search-form").submit();
            });

            $("input[name='searchKeyword']").on("keypress", (e) => {
                if(e.key === 'Enter') {
                    $("input[name='pageNum']").val(1);
                }
            });

            $(".pagination a").on("click", (e) => {
                e.preventDefault();

                // console.log($(e.target).attr("href"));

                $("input[name='pageNum']").val($(e.target).attr("href"));

                $("#search-form").submit();
            });
        });
    </script>
</body>
</html>
