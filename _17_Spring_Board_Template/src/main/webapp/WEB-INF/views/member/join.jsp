<%--
  Created by IntelliJ IDEA.
  User: bit
  Date: 2024-07-12
  Time: 오후 4:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
    <div>

        <jsp:include page="${pageContext.request.contextPath}/header.jsp"></jsp:include>

        <main>
            <div class="container mt-5 w-25">
                <h4>회원가입</h4>
            </div>
            <div class="container mt-3 mb-5 w-25">
                <form id="joinForm" method="post">
                    <div class="form-group">
                        <label for="username">아이디</label>
                        <input type="text" class="form-control" id="username" name="username" required>
                        <div class="d-flex justify-content-end mt-2">
                            <button type="button" class="btn btn-secondary" id="btnIdCheck">중복체크</button>
                        </div>
                    </div>
                    <div class="form-group mt-3">
                        <label for="password">비밀번호</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                        <p id="pwValidation" style="color: red; font-size: 0.8rem;">
                            비밀번호는 영문자, 숫자, 특수문자 조합의 9자리 이상으로 설정해주세요.
                        </p>
                    </div>
                    <div class="form-group mt-3">
                        <label for="passwordChk">비밀번호 확인</label>
                        <input type="password" class="form-control" id="passwordChk" name="passwordChk" required>
                        <p id="pwCheckResult" style="font-size: 0.8rem;"></p>
                    </div>
                    <div class="form-group mt-3">
                        <label for="nickname">닉네임</label>
                        <input type="text" class="form-control" id="nickname" name="nickname" required>
                    </div>
                    <div class="form-group mt-3">
                        <label for="email">이메일</label>
                        <input type="text" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="form-group mt-3">
                        <label for="tel">휴대폰</label>
                        <input type="text" class="form-control" id="tel" name="tel" placeholder="숫자만 입력하세요.">
                    </div>
                    <div class="form-group mt-3">
                        <button type="submit" class="btn btn-outline-secondary w-100">회원가입</button>
                    </div>
                </form>
            </div>
        </main>


        <jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>

    </div>
</body>
</html>
