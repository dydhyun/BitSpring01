<%--
  Created by IntelliJ IDEA.
  User: bit
  Date: 2024-07-08
  Time: 오후 4:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello, JSP</title>
</head>
<body>
    <h1>hello.jsp 입니다.</h1>
    <!-- jsp 파일에 데이터 바인딩은 model 객체에 담긴 키값으로 매핑을 하고, el 표기법을 사용해서 매핑한다.-->
    <p>name: ${name}</p>
    <p>language: ${language}</p>

</body>
</html>
