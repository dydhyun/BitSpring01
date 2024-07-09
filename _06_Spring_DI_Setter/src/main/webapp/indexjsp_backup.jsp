<!--jsp 선언문-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<body>
    <h2>헬로ㅜㅇㄹ드!</h2>
    <!--루트폴더로 경로 잡고., bit.png 가져오라는 요청보내기-->
    <img src="${pageContext.request.contextPath}/images/bit.png">
</body>
</html>
