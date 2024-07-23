<%--
  Created by IntelliJ IDEA.
  User: bitcamp
  Date: 24. 7. 12.
  Time: 오후 5:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
    <div>
        <!--pageContext.request.contextPath: WebServer의 root path인 webapp 폴더(http://localhost:8090)-->
        <jsp:include page="${pageContext.request.contextPath}/header.jsp"></jsp:include>

        <main>
            <div class="container mt-5 w-25">
                <h4>로그인</h4>
            </div>
            <div class="container mt-4 mb-5 w-25">
                <form id="login-form" action="/member/login.do" method="post">
                    <div class="form-group">
                        <label for="username">아이디</label>
                        <input type="text" class="form-control" id="username" name="username" required>
                    </div>
                    <div class="form-group mt-4">
                        <label for="password">비밀번호</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="form-group mt-4">
                        <button type="submit" class="btn btn-outline-secondary w-100">로그인</button>
                    </div>
                </form>
            </div>
        </main>

        <jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>
    </div>
    <script>
        $(() => {
            // model, session, request에 담겨있는 데이터 javascript에서 꺼내서 사용하기
            const loginFailMsg = '${loginFailMsg}';

            if(loginFailMsg === 'idNotExist') {
                alert("존재하지 않는 아이디입니다.");
            } else if(loginFailMsg === 'wrongPassword') {
                alert("잘못된 비밀번호입니다.");
            }
        });
    </script>
</body>
</html>
