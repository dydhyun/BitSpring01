<%--
  Created by IntelliJ IDEA.
  User: bitcamp
  Date: 24. 7. 12.
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
                <form id="join-form" action="/member/join.do" method="post">
                    <div class="form-group">
                        <label for="username">아이디</label>
                        <input type="text" class="form-control" id="username" name="username" required>
                        <div class="d-flex justify-content-end mt-2">
                            <button type="button" class="btn btn-secondary" id="btn-id-check">중복체크</button>
                        </div>
                    </div>
                    <div class="form-group mt-3">
                        <label for="password">비밀번호</label>
                        <input type="password" class="form-control" id="password" name="password" required autocomplete="off">
                        <p id="pw-validation" style="color: red; font-size: 0.8rem;">
                            비밀번호는 영문자, 숫자, 특수문자 조합의 9자리 이상으로 설정해주세요.
                        </p>
                    </div>
                    <div class="form-group mt-3">
                        <label for="password-chk">비밀번호 확인</label>
                        <input type="password" class="form-control" id="password-chk" name="password-chk" required autocomplete="off">
                        <p id="pw-confirm" style="font-size: 0.8rem;"></p>
                    </div>
                    <div class="form-group mt-3">
                        <label for="nickname">닉네임</label>
                        <input type="text" class="form-control" id="nickname" name="nickname" required>
                        <button type="button" class="btn btn-secondary" id="btn-nickname-check">중복체크</button>
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

    <script>
        $(() => {
            // 아이디 중복체크 여부
            let idCheck = false;
            // 닉네임 중복체크
            let nicknameCheck = false;

            // 비밀번호 유효성 검사
            let passwordCheck = false;
            // 비밀번호 일치 여부
            let passwordConfirm = false;


            // ajax를 통한 아이디 중복체크
            $("#btn-id-check").on("click", (e) => {
                // 중복체크 버튼 클릭 시 아이디값이 비어 있으면
                if($("#username").val() === "") {
                    alert("아이디를 입력하세요.");
                    $("#username").focus();
                    return;
                }

                // ajax를 이용해서 백엔드와 비동기 통신
                $.ajax({
                    url: "/member/usernameCheck.do",
                    type: "post",
                    data: $("#join-form").serialize(),
                    success: (obj) => {
                        console.log(obj)
                        // if(obj === "usernamePass"){
                        //     alert("사용 가능한 아이디 입니다.");
                        // }else
                        //     alert("중복된 아이디 입니다.");
                        // console.log(typeof obj);

                        // 속성값을 사용하기 위해 Json String 을 jsonObject 로 변경
                        const jsonObj = JSON.parse(obj);
                        // console.log(jsonObj);

                        // console.log(obj.usernameCheckMsg);
                        // console.log(jsonObj.usernameCheckMsg);
                        if (jsonObj.usernameCheckMsg === "usernamePass"){
                            if(confirm(`사용가능한 아이디 입니다. \${\$("#username").val()}를 사용하시겠습니가?`)) {
                                idCheck = true;
                                $("#btn-id-check").attr("disabled",true);
                            }
                            return;
                        }
                        alert("중복된 아이디 입니다.");
                        idCheck = false;
                        $("#username").focus();

                    },
                    error: (err) => {
                        console.log(err);
                    }
                });
            });


            // 닉네임 중복체크
            $("#btn-nickname-check").on("click", (e) => {
                if($("#nickname").val() === ""){
                    alert("닉네임 입력하세요.")
                    $("#nickname").focus();
                    return;
                }

                // ajax 사용
                $.ajax({
                    url: "/member/nicknameCheck.do",
                    type: "post",
                    data: $("#join-form").serialize(),
                    success: (obj) => {
                        console.log(obj);
                        const jsonNicknameObj = JSON.parse(obj);
                        console.log(jsonNicknameObj);

                        if(jsonNicknameObj.nicknameCheckMsg === "nicknamePass"){
                            if( confirm("사용가능한 닉네임 입니다. 사용하시겠습니까?") ){
                                nicknameCheck = true;
                                $("#btn-nickname-check").attr("disabled",true);
                            }
                            return;
                        }
                        alert("중복된 닉네임 입니다.");
                        nicknameCheck = false;
                        $("#nickname").focus();
                    },
                    error: (err) => {
                        console.log(err);
                    }
                });
            });


            // 중복체크를 하고 아이디 사용하겠다 했는데, 아이디값을 바꿀때 다시 중복체크 버튼 활성화 시키는 기능
            $("#username").on("change", (e) => {
                idCheck = false;
                $("#btn-id-check").attr("disabled",false);
            });

            // 중복체크를 하고 닉네임 사용하겠다 했는데, 닉네임을 바꿀때 다시 중복체크 버튼 활성화 시키는 기능
            $("#nickname").on("change", (e) => {
                nicknameCheck = false;
                $("#btn-nickname-check").attr("disabled",false);
            });


            // 비밀번호 유효성 검사 메서드
            const validatePassword = (pw) => {
                return /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*+=-]).{9,}$/.test(pw);
            }

            // 비밀번호 input의 내용이 변경되면 validatePassword 메서드로 유효성 검사 진행
            $("#password").on("change", (e) => {
                if(validatePassword($("#password").val())){
                    passwordCheck = true;
                    $("#pw-validation").hide();
                } else {
                    passwordCheck = false;
                    $("#pw-validation").show();
                }

                if ($("#password").val() === $("#password-chk").val()) {
                    passwordConfirm = true;
                    $("#pw-confirm").text("비밀번호가 일치합니다.");
                    $("#pw-confirm").css("color", "green");
                }
                else {
                    passwordConfirm = false;
                    $("#pw-confirm").text("비밀번호가 일치하지 않습니다.");
                    $("#pw-confirm").css("color", "red");
                }
            })

            // 비밀번호 일치 확인
            $("#password-chk").on("change", (e) => {

                $("#pw-confirm").show();

                if ($("#password").val() === $("#password-chk").val()) {
                    passwordConfirm = true;
                    $("#pw-confirm").text("비밀번호가 일치합니다.");
                    $("#pw-confirm").css("color", "green");
                    return;
                }
                passwordConfirm = false;
                $("#pw-confirm").text("비밀번호가 일치하지 않습니다.");
                $("#pw-confirm").css("color", "red");
            });// 비밀번호 바꿨을 때, 일치합니다 유지 수정하기


            $("#join-form").on("submit", (e) =>{
                // id 중복체크 안됐을 때
                if(!idCheck){
                    e.preventDefault();
                    alert("아이디 중복체크 해주세요.");
                    return;
                }
                if(!passwordCheck){
                    e.preventDefault();
                    alert("비밀번호는 영문자, 숫자, 특수문자 조합의 9 자리 이상으로 지정하세요.")
                    return;
                }

                if(!passwordConfirm){
                    e.preventDefault();
                    alert("비밀번호가 일치하지 않습니다.");
                    return;
                }

                if(!nicknameCheck){
                    e.preventDefault();
                    alert("닉네임 중복체크 해주세요.");
                    return;
                }
            })

        });


    </script>
</body>
</html>
