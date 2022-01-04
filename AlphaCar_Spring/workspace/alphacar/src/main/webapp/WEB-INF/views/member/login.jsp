<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <div class="login_page">
      <div class="text_logo">
        <img src="img/alphacarLogo_text_black_500px.png" alt="alphaCar" onclick="location.href='<c:url value="/" />'"
      	style="cursor: pointer;">
      </div>
      <form class="form" method="post" action="webLogin">
        <input type="text" placeholder="이메일" class="customer_email" id="customer_email" />
        <input type="password" placeholder="비밀번호" class="customer_pw" id="customer_pw"
        				onkeypress = "if (event.keyCode == 13) go_login()" />
        <label><input type="checkbox" name="alwaysLogin" class="login_always" id="login_always"
        				onclick="login_check()">로그인 항상 유지</label>
        <button type="button" onclick="go_login()">로그인</button>
        <a href='kakaoLogin'><img src='img/kakao_login_large_wide.png' class='social_login' /></a>
      </form>
      <div class="login_page_bottom_button">
        <button type="button" onclick="location.href='searchPw'">비밀번호 찾기</button>
        <button type="button" onclick="location.href='searchId'">아이디 찾기</button>
        <button type="button" onclick="location.href='homeJoin'">회원가입</button>
      </div>
    </div>
    
    <script type="text/javascript">
	// 자동 로그인 체크박스		

			function login_check(){
				if($('#login_always').is(':checked') == true){
					console.log("체크되엇습니다");
				} else {
					$('#login_always').is(':checked') == false;	
					console.log("체크해제");
				}
				
			}
			
			function go_login() {
				var checked = $('#login_always').prop("checked");
				// 아이디 입력값이 없으면
				if($('#customer_email').val() == '') {
					alert('이메일을 입력하세요!');
					$('#customer_email').focus();
					return;
				// 비밀번호 입력값이 없으면 	
				} else if($('#customer_pw').val() == '' ) {
					alert('비밀번호를 입력하세요!');
					$('#customer_pw').focus();
					return;
				} 
				
	 				if (checked) {
						console.log('checked');
						
					}else{
						console.log('아닙니다');
					}
				 
				// login 을 위한 ajax 통신 설정 
  				$.ajax({
					url : 'webLogin',
					data : {customer_email:$('#customer_email').val(), customer_pw:$('#customer_pw').val(),chk:checked },
					success : function(response) {
						if ( response ) {
							location = '<c:url value ="/" />';

						} else {
							alert("이메일이나 비밀번호가 일치하지 않습니다.")
						}
					}, error : function (req, text) {
						alert(text + ':' + req.status);
					}
				});
				 
				 
			}
			
			
		</script>	
	