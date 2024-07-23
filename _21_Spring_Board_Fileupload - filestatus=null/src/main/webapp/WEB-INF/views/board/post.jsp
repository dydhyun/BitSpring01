<%--
  Created by IntelliJ IDEA.
  User: bitcamp
  Date: 24. 7. 15.
  Time: 오전 9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>

</head>
<body>
<div>
    <jsp:include page="${pageContext.request.contextPath}/header.jsp"></jsp:include>

    <main>
        <div class="container w-50 mt-5 mb-5">
            <h4>글 등록</h4>
        </div>
        <div class="container mt-3 w-50">
            <form id="post-form" action="/board/post.do" method="post" enctype="multipart/form-data">
                <c:if test="${loginMember.role eq 'ADMIN'}">
                    <div class="form-group">
                        <label for="type">카테고리</label>
                        <select class="form-select" name="type" id="type">
                            <option value="free" selected>자유게시판</option>
                            <option value="notice">공지사항</option>
                        </select>
                    </div>
                </c:if>
                <div class="form-group mt-3">
                    <label for="title">제목</label>
                    <input type="text" class="form-control" id="title" name="title" required>
                </div>
                <div class="form-group mt-3">
                    <label for="nickname">작성자</label>
                    <input type="text" class="form-control" id="nickname" name="nickname"
                           value="${loginMember.nickname}" readonly required>
                </div>
                <div class="form-group mt-3">
                    <label for="content">내용</label>
                    <textarea class="form-control" id="content" name="content" rows="10" required></textarea>
                </div>
                <div class="form-group mt-3">
                    <label for="uploadFiles">파일첨부</label>
                    <input class="form-control" type="file" name="uploadFiles" id="uploadFiles" multiple>
                    <div id="preview" class="mt-3 text-center"
                         data-placeholder="파일을 첨부하려면 파일선택 버튼을 누르세요.">
                    </div>
                </div>
                <div class="container mt-3 mb-5 w-50 d-flex justify-content-center">
                    <button type="submit" class="btn btn-outline-secondary">등록</button>
                </div>
            </form>
        </div>
    </main>

    <jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>
</div>
<script>
    // 추가된 파일들을 하나씩 담아줄 배열. File 객체가 저장
    const uploadFiles = [];

    $(() => {
        $("#uploadFiles").on("change", (e) => {
            // alert("파일 추가됨");

            // input 에 추가된 파일들 변수로 받기
            const files = e.target.files;

            // 변수로 받아온 파일들 배열로 변환
            const fileArr = Array.prototype.slice.call(files);


            // 이미지로더 호출하기
            for (file of fileArr) {
                // 미리보기 메서드 호출
                imageLoader(file);
            }

        });
    });

    // 미리보기 처리하는 메소드
    // 미리보기 될 파일은 서버나 데이터베이스에 저장된 상태가 아니기 때문에
    // 파일 자체를 Base64 인코딩 방식으로 변환해서 이미지로 호출해야 된다.
    // 이미지가 들어갈 태그 생성과 파일을 Base64 인코딩
    const imageLoader = (file)  => {
        // 추가된 파일 uploadFiles 배열에 담기
        uploadFiles.push(file);


        // reader 가 호출되면 실행될 이벤트 만들기
        let reader = new FileReader();
        reader.onload = (e) => {
            // 이미지를 표출할 img 태그 생성
            let img = document.createElement("img");

            img.classList.add("upload-file");

            // 이미지 인지아닌지 판단하기
            if(file.name.toLowerCase().match(/(.*?)\.(jpg|jpeg|png|gif|svg|bmp)$/)){
                img.src = e.target.result;
            } else {
                img.src = "/static/images/defaultFileImg.png";
            }


            // 미리보기 영역에 추가
            // makeDiv 메소드를 호출해서 만들어진 div 자체를 preview 영역에 추가
            $("#preview").append(makeDiv(img, file));


        }

        // 파일을 Base64 인코딩된 문자열로 로드
        // 이 메서드가 실행되면서 위에서 등록한 onload 이벤트가 함께 동작한다.
        reader.readAsDataURL(file);

    }

    // 미리보기 영역에 추가될 div 를 생성하는 메소드
    const makeDiv = (img, file) => {
        let div = document.createElement("div");

        div.classList.add("upload-file-div");

        // 삭제버튼 추가
        let btn = document.createElement("input");

        btn.classList.add("upload-file-delete-btn");

        btn.setAttribute("type", "button");
        btn.setAttribute("value", "x");

        //사용자 정의속성 추가하기
        btn.setAttribute("deleteFile", file.name);


        // p 태그 만들기 전에 이벤트 만들어주기 ~
        // x 버튼 클릭시 삭제 기능
        btn.onclick = (e) => {
            // 클릭된 버튼 변수로 받기
            const element = e.target;

            const deleteFileName = element.getAttribute("deleteFile");

            // uploadFiles[] 에서도 삭제해주기
            // 필터 이용하면 똑같은 파일 두개 올렸을때 둘 다 삭제 방지를 위한 index 주자
            // uploadFiles.filter(((file, index) => file.name !== deleteFileName || uploadFiles.indexOf(file) !== index));
            // 원본배열 바꾸기위해 for 문 사용해야 된다.
            for (let i = 0 ; i < uploadFiles.length; i++) {
                if(deleteFileName === uploadFiles[i].name) {
                    uploadFiles.splice(i, 1);
                }
            }


            // input 에서도 파일삭제
            // input type="file" 은 첨부된 파일들을 fileList 형태로 관리
            // fileList 는 File 객체에 바로 담을수 없기 때문에 DataTransfer 라는 클래스를 사용해서 변환 후에 담아줘야 된다.
            let dataTransfer = new DataTransfer();


            for(i in uploadFiles) {
                // uploadFiles 배열에 있는 File 객체를 하나씩 DataTransfer 객체에 담아준다.
                const file = uploadFiles[i];
                dataTransfer.items.add(file);

            }

            // input type="file" 에 fileList 형태로 밀어넣기
            $("#uploadFiles")[0].files = dataTransfer.files;

            // 클릭된 btn 태그를 소유하고 있는 부모 div 태그 삭제하기
            const parentDiv = element.parentNode;
            $(parentDiv).remove();
        }


        // 파일이름을 표출할 p 태그 생성
        let p = document.createElement("p");
        p.classList.add("upload-file-name");

        // innerText 로 대체 가능.
        p.textContent = file.name;

        // div 태그에 img, btn, p 태그 자식으로 추가
        div.appendChild(img);
        div.appendChild(btn);
        div.appendChild(p);

        return div;
    }


</script>
</body>
</html>
