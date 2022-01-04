<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <div class="sub_image">
        <img src="img/main_image.jpg" alt="">
    </div>
	<div class="page_header_text">
		<p>공지사항</p>
		<p>공지사항을 수정하는 페이지 입니다.</p>
	</div>
  </nav>

  <!-- 메인 시작 -->
  <main>
    <div id="page">
      <h1>작성 글 수정</h1>
		<form action="update_work.no" class="page_write" method="post">
			<input type="hidden" name='notice_id' value="${vo.notice_id}"/>
			<input type="hidden" name='attach' />
			<div class="page_write_index">
				<div class="page_search_index">
					<select name="notice_attribute" id="notice_search_index">
						<option value="N" ${vo.notice_attribute eq 'N' ? 'selected' : '' }>공지</option>			
						<option value="A" ${vo.notice_attribute eq 'A' ? 'selected' : '' }>알파카</option>						
						<option value="M" ${vo.notice_attribute eq 'M' ? 'selected' : '' }>점검</option>
					</select>
				</div>
				<div class="page_write_title">
					<input type="text" name="notice_title" value="${vo.notice_title }">
				</div>
			</div>
			<div class="page_write_space">
				<textarea id="summernote" name="notice_content" cols="100" >${vo.notice_content }</textarea>
			</div>
			<div class="page_write_button">
				<button>수정완료</button>
				<button type="button" onclick="location.href='list.no'">취소</button>
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