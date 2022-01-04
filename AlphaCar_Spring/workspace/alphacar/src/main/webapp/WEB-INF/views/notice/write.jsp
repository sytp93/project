<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <div class="sub_image">
        <img src="img/main_image.jpg" alt="">
    </div>
	<div class="page_header_text">
		<p>공지사항</p>
		<p>새로운 공지사항을 작성하는 페이지 입니다.</p>
	</div>
  </nav>

  <!-- 메인 시작 -->
  <main>
    <div id="page">
      <h1>신규글 작성</h1>
		<form action="insert.no" class="page_write" method="post">
			<div class="page_write_index">
				<div class="page_search_index">
					<select name="notice_attribute" id="notice_search_index">
						<option value="N" selected="selected">공지</option>
						<option value="A">알파카</option>
						<option value="M">점검</option>
					</select>
				</div>
				<div class="page_write_title">
					<input type="text" placeholder="제목" name="notice_title">
				</div>
			</div>
			<div class="page_write_space">
				<textarea id="summernote" name="notice_content" cols="100" >
				</textarea>
			</div>
			<div class="page_write_button">
				<button>작성완료</button>
				<button type="button" onclick="location.href='list.no'" >취소</button>
			</div>
		</form>
    </div>
  </main>
  <!-- 서머노트를 위해 추가해야할 부분 -->
  <script src="resources/js/summernote-lite.js"></script>
  <script src="resources/js/lang/summernote-ko-KR.js"></script>
  <link rel="stylesheet" href="resources/css/summernote-lite.css">

  <script>
	$('#summernote').summernote({
	      height: 500,
	      lang: "ko-KR",
	      toolbar: [
	           // [groupName, [list of button]]
	           ['fontname', ['fontname']],
	           ['fontsize', ['fontsize']],
	           ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
	           ['color', ['forecolor','color']],
	           ['para', ['ul', 'ol', 'paragraph']],
	           ['height', ['height']],
		       // 그림첨부, 링크만들기, 동영상첨부
		 		   ['insert',['picture','link']],
	           ['view', ['help']]
	         ],
	       fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
	       fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
	    });
	</script>