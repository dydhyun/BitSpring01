<%--
  Created by IntelliJ IDEA.
  User: bitcamp
  Date: 24. 7. 15.
  Time: 오전 9:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
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
                <c:forEach items="${noticeList}" var="notice">
                    <div class="card" style="width: 18rem;">
                        <c:choose>
                            <c:when test="${notice.file != null and notice.file.filetype eq 'image'}">
                                <img class="bd-placeholder-img card-img-top" width="100%" height="180"
                                     src="/upload/${notice.file.filename}"
                                     alt="${notice.file.fileoriginname}">
                            </c:when>
                            <c:otherwise>
                                <svg class="bd-placeholder-img card-img-top" width="100%" height="180" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Image cap" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#868e96"></rect></svg>
                            </c:otherwise>
                        </c:choose>
                        <div class="card-body">
                            <h5 class="card-title">${notice.boardDto.title}</h5>
                            <p class="card-text">작성일:
                                <!--fmt:parseDate: LocalDateTime 타입을 Date 타입으로 변환-->
<%--                                <fmt:parseDate value="${notice.regdate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedRegdate" type="both"/>--%>
                                <!--fmt:formatDate: Date타입의 날짜를 형식 지정에 맞게 표출-->
<%--                                <fmt:formatDate value="${parsedRegdate}" pattern="yyyy-MM-dd"></fmt:formatDate>--%>
                                <javatime:format value="${notice.boardDto.regdate}" pattern="yyyy-MM-dd"/>
                            </p>
                            <a href="/board/update-cnt.do?id=${notice.boardDto.id}&type=notice" class="btn btn-outline-secondary btn-sm">자세히 보기</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="container mt-3 w-50">
                <form id="search-form" action="/board/notice-list.do" method="post">
                    <input type="hidden" name="pageNum" value="${page.cri.pageNum}">
                    <input type="hidden" name="amount" value="${page.cri.amount}">
                    <input type="hidden" name="endPage" value="${page.endPage}">
                    <div class="row d-flex justify-content-center">
                        <div class="col-6">
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

            <c:if test="${loginMember ne null and loginMember.role eq 'ADMIN'}">
<%--        <c:if test="${loginMember != null && loginMember.role == 'ADMIN'}">    --%>
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

            const zeroDate = (date) => {
                return date < 10 ? `0\${date}` : date;
            }

            const makeImageElement = (file) => {
                console.log(file);
                if(typeof file != 'undefined' && file.filetype === 'image') {
                    return `<img class="bd-placeholder-img card-img-top" width="100%" height="180" src="/upload/\${file.filename}" alt=\${file.fileoriginname}>`
                }

                return `<svg class="bd-placeholder-img card-img-top" width="100%" height="180" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Image cap" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#868e96"></rect></svg>`;
            }

            $(window).on("scroll", (e) => {
                // 현재 스크롤의 위치
                const scrollTop = $(window).scrollTop();
                // 브라우저의 세로길이(스크롤 길이는 포함되지 않음)
                const windowHeight = window.innerHeight;
                // 웹 문서의 세로 길이(스크롤 길이 포함됨)
                const documentHeight = document.documentElement.scrollHeight;

                // 스크롤이 바닥에 닿았는지 여부
                const isBottom = documentHeight <= scrollTop + windowHeight;

                /*console.log(`scrollTop: \${scrollTop}`);
                console.log(`windowHeight: \${windowHeight}`);
                console.log(`documentHeight: \${documentHeight}`);
                console.log(`isBottom: \${isBottom}`);*/

                if(isBottom) {
                    // 현재 페이지의 번호가 마지막 페이지의 번호와 같으면 함수 종료
                    if($("input[name='pageNum']").val() >= $("input[name='endPage']").val()) {
                        return;
                    } else {
                        // 스크롤이 바닥에 닿으면 현재 페이지 번호 + 1
                        $("input[name='pageNum']").val(parseInt($("input[name='pageNum']").val()) + 1);

                        $.ajax({
                            url: '/board/notice-list-ajax.do',
                            type: 'post',
                            data: $("#search-form").serialize(),
                            success: (obj) => {
                                console.log(obj);
                                let htmlStr = "";
                                for(let i = 0; i < obj.noticeList.length; i++) {
                                    htmlStr += `
                                        <div class="card" style="width: 18rem;">
                                            \${makeImageElement(obj.noticeList[i].file)}
                                            <div class="card-body">
                                                <h5 class="card-title">\${obj.noticeList[i].boardDto.title}</h5>
                                                <p class="card-text">작성일: \${obj.noticeList[i].boardDto.regdate[0]}-\${zeroDate(obj.noticeList[i].boardDto.regdate[1])}-\${zeroDate(obj.noticeList[i].boardDto.regdate[2])}</p>
                                                <a href="/board/update-cnt.do?id=\${obj.noticeList[i].boardDto.id}&type=notice" class="btn btn-outline-secondary btn-sm">자세히 보기</a>
                                            </div>
                                        </div>
                                    `;
                                }
                                // console.log(htmlStr);
                                $(".card-wrapper").append(htmlStr);
                            },
                            error: (err) => {
                                console.log(err);
                            }
                        });
                    }
                }
            });
        });
    </script>
</body>
</html>
