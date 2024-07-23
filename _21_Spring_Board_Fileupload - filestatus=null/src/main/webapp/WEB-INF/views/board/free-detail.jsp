<%--
  Created by IntelliJ IDEA.
  User: bitcamp
  Date: 24. 7. 15.
  Time: 오전 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>

</head>
<body>
    <div>
        <jsp:include page="${pageContext.request.contextPath}/header.jsp"></jsp:include>

        <main>
            <div class="container w-50 mt-5 mb-5">
                <h4>자유게시글 상세</h4>
            </div>
            <div class="container mt-3 w-50">
                <form id="modify-form" action="/board/modify.do" method="post">
                    <input type="hidden" name="id" value="${freeBoard.id}">
                    <input type="hidden" name="type" value="free">
                    <input type="hidden" name="type" value="originFiles" id="originFiles">
<%--                    객체배열 형태의 originFiles 배열을 문자열로 변환하여 input 에 담아 전송.--%>

                    <div class="form-group">
                        <label for="title">제목</label>
                        <input type="text" class="form-control" id="title" name="title" value="${freeBoard.title}" required>
                    </div>
                    <div class="form-group mt-3">
                        <label for="nickname">작성자</label>
                        <input type="text" class="form-control" id="nickname" name="nickname" value="${freeBoard.nickname}" readonly>
                    </div>
                    <div class="form-group mt-3">
                        <label for="content">내용</label>
                        <textarea class="form-control" id="content" name="content" rows="10" required>${freeBoard.content}</textarea>
                    </div>
                    <div class="form-group mt-3">
                        <label for="regdate">등록일</label>
                        <input type="text" class="form-control" id="regdate" name="regdate"
                               value="<javatime:format value="${freeBoard.regdate}" pattern="yyyy-MM-dd"/>" readonly required>
                    </div>
                    <div class="form-group mt-3">
                        <label for="moddate">수정일</label>
                        <input type="text" class="form-control" id="moddate" name="moddate"
                               value="<javatime:format value="${freeBoard.moddate}" pattern="yyyy-MM-dd"/>" readonly required>
                    </div>
                    <div class="form-group mt-3">
                        <label for="cnt">조회수</label>
                        <input type="text" class="form-control" id="cnt" name="cnt" value="${freeBoard.cnt}" readonly required>
                    </div>
                    <div class="form-group mt-3">
                        <label for="uploadFiles">파일첨부</label>
                        <input class="form-control" type="file" name="uploadFiles" id="uploadFiles" multiple>
                        <div id="image-preview">
                            <input type="file" id="changeFiles" name="changeFiles" style="display: none;"
                                   multiple>
                            <p style="color: red; font-size:0.9rem;">
                                파일을 변경하려면 이미지를 클릭하세요. 파일을 다운로드하려면 파일이름을 클릭하세요. 파일을 추가하려면 파일 선택 버튼을 클릭하세요.
                            </p>
                            <div id="preview" class="mt-3 text-center"
                                 data-placeholder="파일을 첨부하려면 파일선택 버튼을 누르세요.">
                                <c:forEach items="${fileList}" var="file">
                                    <div class="upload-file-div">

                                        <input type="hidden" id="fileId${status.index}" value="${file.id}">
                                        <input type="hidden" id="filename${status.index}" value="${file.filename}">
<%--                                        밑에 표시된 파일을 클릭 했을 때 파일 선택창이 뜨도록 input type="file" 하나 생성--%>
                                        <input type="file" id="changeFile${file.id}" name="changeFile${file.id}" style="display: none;"
                                                onchange="changeFile(${file.id},event)">
                                        <c:if test="${status.last}">
                                            <input type="hidden" id="filecnt" name="filecnt" value="${status.count}">
                                        </c:if>

                                        <c:choose>
                                            <c:when test="${file.filetype eq 'image'}">
                                                <img id="img${file.id}"
                                                     src="/upload/${file.filename}"
                                                     class="upload-file"
                                                     alt="${file.fileoriginname}"
                                                     onclick="fileClick(${file.id})">
                                            </c:when>
                                            <c:otherwise>
                                                <img id="img${file.id}"
                                                     src="/static/images/defaultFileImg.png"
                                                     class="upload-file"
                                                     alt="${file.fileoriginname}"
                                                     onclick="fileClick(${file.id})">
                                            </c:otherwise>
                                        </c:choose>
                                        <input type="button" value="x"
                                               deleteFile="${file.id}" class="upload-file-delete-btn"
                                               onclick="deleteFile(event)">
                                        <p id="filename${file.id}" class="upload-file-name">
                                            ${file.fileoriginname}
                                        </p>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <c:if test="${loginMember ne null and loginMember.id eq freeBoard.WRITER_ID}">
                        <div class="container mt-3 mb-5 w-50 text-center">
                            <button type="submit" id="btn-update" class="btn btn-outline-secondary">수정</button>
                            <button type="button" id="btn-delete" class="btn btn-outline-secondary ml-2" onclick="location.href='/board/delete.do?id=${freeBoard.id}&type=free'">삭제</button>
                        </div>
                    </c:if>
                </form>
            </div>
        </main>

        <jsp:include page="${pageContext.request.contextPath}/footer.jsp"></jsp:include>
    </div>
    <script>

        // 추가된 파일들을 담아줄 배열
        const uploadFiles = [];

        // 기존에 업로드 되어있는 파일들을 담을 배열
        // 게시글 번호, 파일번호, 파일명 을 객체 형태로 담아준다.
        const originFiles = [];

        // 변경된 파일들 담을 배열
        const changeFiles = [];


        $(() => {
            $("#modify-form").on("submit", (e) => {
                $("#regdate").val(`\${\$("#regdate").val()}T00:00:00`);
                $("#moddate").val(`\${\$("#moddate").val()}T00:00:00`);
            });


            // 돔객체가 모두 로드가 되면 업로드 되어있던 파일들을 originFiles 배열에 담기
            for (let i = 0; i < $("#filecnt").val(); i++){
                const originFileObj = {
                    board_id: $("input[name = 'id']").val(),
                    id: $("#fileId"+i).val(),
                    filename: $("#filename" + i).val(),
                    filestatus: "N" // 초기상태 변경없음:N , 변경됨:U , 삭제됨:D
                };

                originFiles.push(originFileObj);
            }

//********************************************* post 내용 *************************************************
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
//      **추가++
                // modify-form 이 서브밋 될 때
                // uploadFiles 배열에 담겨있는 파일들을 input name="uploadFiles"에 담기
                // changeFiles 배열에 담겨있는 파일들을 input name="changeFiles"에 담기
                // originFiles 배열에 담겨있는 객체 들을 문자열로 변환해 input name="originFiles"에 담기
                $("#modify-form").on("submit", (e) => {
                    let dataTransfer1 = new DataTransfer();
                    let dataTransfer2 = new DataTransfer();

                    for (i in uploadFiles){
                        const file = uploadFiles[i];
                        dataTransfer1.items.add(file);
                    }

                    $("#uploadFiles").files = dataTransfer1.files;

                    for (i in changeFiles) {
                        const file = changeFiles[i];
                        dataTransfer2.items.add(file);
                    }

                    $("#changeFiles").files = dataTransfer2.files;

                    // JSON 형태의 문자열로 바꿔서 벨류값으로 넣어주기
                    $("#originFiles").val(JSON.stringify(originFiles));
                });

            });

        });


        // 이미 게시글에 업로드 되어있어서 표출되어있는 파일들을 클릭했을 때 실행될 메서드
        const fileClick = (fileId) => {
            $("#changeFile" + fileId).click();
        };

        const changeFile = (fileId, e) => {
            // 하나 씩 진행되기에 미리보기에 띄운 내용과 비슷하게 진행한다
            const files = e.target.files;

            const fileArr = Array.prototype.slice.call(files);

            // multiple 아니기 때문에 하나씩만 업로드 된다. 그냥 [0]번째 꺼내 쓰면 됨
            changeFiles.push(fileArr[0]);

            const reader = new FileReader();

            reader.onload = (event) => {
                const img = document.getElementById("img" + fileId);
                const p = document.getElementById("filename" + fileId);

                if (fileArr[0].name.match(/(.*?)\.(jpg|jpeg|png|gif|bmp)$/)) {
                    img.src = event.target.result;
                } else {
                    img.src = "/static/images/defaultFileImg.png";
                }

                p.textContent = fileArr[0].name;
            }
            reader.readAsDataURL(fileArr[0]);

            // 기존에 originFiles 배열에 담긴 내용 변경하기
            for (let i = 0; i < originFiles.length; i++) {
                if (fileId === originFiles[i].id) {
                    originFiles[i].filestatus = "U";
                    originFiles[i].newfilename = fileArr[0].name;
                }
            }
        }

        const deleteFile = (e) => {
            // 클릭된 x 버튼 변수로 담기
            const element = e.target;

            const deleteFileId = element.getAttribute("deleteFile");

            // originFiles 배열에서 deleteFileId 와 id 가 같은 객체의 filestatus 를 D 로 변경
            for (let i = 0; i < originFiles.length; i++){
                if (deleteFileId == originFiles[i].id){
                    originFiles[i].filestatus = "D";
                }
            }

            // 부모 div 요소 삭제
            const parentDiv = element.parentNode;
            $(parentDiv).remove();


        };



//************************************* 미리보기 처리하는 메소드 *********************** post 에서 가져옴**********
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
