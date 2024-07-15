<%--
  Created by IntelliJ IDEA.
  User: bit
  Date: 2024-07-15
  Time: 오전 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>name-tel</title>
    <style>
        div > p{
            display: inline-block;
            margin-left: 10px;
        }
    </style>
</head>
<body>
    <form action="/name-tel.do" method="post">
        <input type="text" name="name">
        <input type="text" name="tel">
        <input type="submit" value="전송">
    </form>
    <div>
<%--        java 에서 전송한 데이터를 el 표기법으로 표출한다.
            자바에서도 데이터 전송할 때 키: 벨류 매핑된 데이터로 오기 때문에,
            키를 이용해 밸류값을 사용한다.
--%>
<%--        <p>name의 attributeName 로 지정한 nm: ${nm}</p>--%>
<%--    <br>--%>
<%--        <p>tel의 attributeName 로 지정한 tl:${tl}</p>--%>

<%--        자바에서 전송한 데이터가 클래스 타입일 경우 정송한 키, 맴버변수명 으로 데이터를 꺼내서 사용할 수 있다.--%>
<%--        <p>nameTelDto 객체의 name: ${nameTelDto.name}</p>--%>
<%--    <br>--%>
<%--        <p>nameTelDto 객체의 tel: ${nameTelDto.tel}</p>--%>

    </div>

    <form action="/name-tel.do" method="post">
        <input type="submit" value="리스트 보기">
    </form>
    <%--        jstl의 forEach 구문으로 List 형태로 자바에서 넘어온 데이터를 하나씩 가져온다--%>
    <c:forEach items="${nameTelList}" var="eachListNameABC">
        <div>
            <p>name: ${eachListNameABC.name}</p>
            <p>tel: ${eachListNameABC.tel}</p>
        </div>
    </c:forEach>

</body>
</html>
