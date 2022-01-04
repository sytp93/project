<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="mypage_image"></div>
<div class="mypage_select">
	<div class="mypage_userinfo">
		<a href=""><p class="mypage_userinfo_select">회원정보</p></a> <a href=""><p>보안설정</p></a>
	</div>
</div>
</nav>
<!-- 메인 시작 -->
<main class="mypage">
	<div id="page">
		<form class="form company_update_form" name="dataForm" id="dataForm"
			onsubmit="return registerAction()">
			<h1>신규 세차장 등록</h1>
			<div class="company_update">
				<div>
					<h3>세차장 이름</h3>
					<input type="text" name="store_name" id="store_name">
					<div id="storeError"></div>
				</div>
				<div>
					<h3>우편번호</h3>
					<button type="button" onclick='daum_post()'>우편번호 찾기</button>
					<input type="text" name="store_post" id="store_post" value="${vo.store_post }"><br />
				</div>
				<div>
					<h3>주소</h3>
					<input type="text" name="store_addr" value="${vo.store_addr }" id="store_addr">
				</div>
				<div>
					<h3>상세주소</h3>
					<input type="text" name="store_detail_addr" id="store_detail_addr"
						value="${vo.store_detail_addr }">
				</div>
				<div>
					<h3>전화번호</h3>
					<input type="text" name="store_tel" id="store_tel" onkeyup="checkTel()">
					<div id="telError"></div>
				</div>
				<div>
					<h3>영업시간</h3>
					<input type="text" name="store_time" id="store_time">
				</div>
				<div>
					<h3>휴무일</h3>
					<input type="text" name="store_dayoff" id="store_dayoff">
				</div>
				<div class="company_introduce">
					<h3>세차장 소개</h3>
					<input type="text" name="introduce" id="introduce">
					<!-- <textarea name="" id=""></textarea> -->
				</div>
				<div>
					<h3>베이수</h3>
					<input type="text" name="inventory" id="inventory" onkeyup="checkInventory()">
					<div id="invenError"></div>
				</div>
				<div>
					<h3>가격</h3>
					<input type="number" name="store_price" id="store_price" onkeyup="checkPrice()">
					<div id="priceError"></div>
				</div>
				<div>
					<h3>사업주 이름</h3>
					<input type="text" name="store_master_name" id="store_master_name" onkeyup="checkMaster()">
					<div id="masterError"></div>
				</div>
				<div>
					<h3>사업자 번호</h3>
					<input type="text" name="store_registration_number" id="store_registration_number" onkeyup="checkRegi()">
					<a id='btn_regi' onclick="regiDupl()">사업자 등록번호 중복검사</a>
					<div id="regiError"></div>
				</div>
				<div class="join_profile_images">
					<h3>사진</h3>
					<div>				
						<div class="join_profile_image">
							<img onclick="choose_image()"
								class="join_profile_image1">
						</div>	
						<div class="join_profile_image">
							<img onclick="choose_image()"
								class="join_profile_image2">
						</div>	
						<div class="join_profile_image">
							<img onclick="choose_image()"
								class="join_profile_image3">
						</div>
					</div>				
				</div>
			</div>
			<div style="display: none" id="articlefileChange"></div>
	        <input style="display: none" multiple="multiple" type="file" class='input_file' id='input_file' name="file" accept="image/*" >
			<button type="button" onclick="check()">등록하기</button>
		</form>
	</div>
</main>
<script type="text/javascript" src='js/file_check.js'></script>
<!-- 파일 업로드 스크립트 -->
<script type="text/javascript" src='js/img_upload.js'></script>

<!-- 다음 주소 검색 API -->
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
const regName =  /^[가-힣]{2,4}$/
const regDigit = /[0-9]/g;
const regRegi = /[0-9]{10}/g;
const regTel = /^010([0-9]{3,4})([0-9]{4})$/;

let name = document.getElementById("store_name");
let post = document.getElementById("store_post");
let addr = document.getElementById("store_addr");
let addr2 = document.getElementById("store_detail_addr");
let tel = document.getElementById("store_tel");
let time = document.getElementById("store_time");
let dayoff = document.getElementById("store_dayoff");
let introduce = document.getElementById("introduce");
let inventory = document.getElementById("inventory");
let price = document.getElementById("store_price");
let master = document.getElementById("store_master_name");
let regi = document.getElementById("store_registration_number");

let teToken = false;
let inToken = false;
let prToken = false;
let maToken = false;
let reToken = false;
let r2Token = false;

function regiDupl() {
	$.ajax({
		url : 'regiDupl.mp'
		, data : {id:regi.value}
		, type : 'post'
		, async : false
		, success : function (res) {
			if (res == true) {
				alert("사용 가능한 사업자등록번호입니다.");
				r2Token = true;
			} else {
				alert("중복되는 번호가 있습니다.");
			}
		}, error : function ( req, text ) {
			alert(text + ':' + req.status);
		}
	});
}

function checkTel() {
	if(!regTel.test(tel.value)) {
		document.getElementById("telError").innerText = "-를 제외한 휴대폰번호를 입력해주세요.";
		document.getElementById("telError").style.color = "red";
	}else {
		document.getElementById("telError").innerText = "전화번호가 입력되었습니다.";
		document.getElementById("telError").style.color = "green";
		teToken = true;
	}
}

function checkInventory() {
	if(!regDigit.test(inventory.value)) {
		document.getElementById("invenError").innerText = "숫자만 입력해주세요.";
		document.getElementById("invenError").style.color = "red";
	}else {
		document.getElementById("invenError").innerText = "베이수가 입력되었습니다.";
		document.getElementById("invenError").style.color = "green";
		inToken = true;
	}
}

function checkPrice() {
	if(!regDigit.test(price.value)) {
		document.getElementById("priceError").innerText = "숫자만 입력해주세요.";
		document.getElementById("priceError").style.color = "red";
	}else {
		document.getElementById("priceError").innerText = "가격이 입력되었습니다.";
		document.getElementById("priceError").style.color = "green";
		prToken = true;
	}
}

function checkMaster() {
	if(!regName.test(master.value)) {
		document.getElementById("masterError").innerText = "2~4자의 한글만 입력해주세요.";
		document.getElementById("masterError").style.color = "red";
	}else {
		document.getElementById("masterError").innerText = "이름이 입력되었습니다.";
		document.getElementById("masterError").style.color = "green";
		maToken = true;
	}
}

function checkRegi() {
	if(!regRegi.test(regi.value)) {
		document.getElementById("regiError").innerText = "-를 제외한 등록번호(10자리)를 입력해주세요.";
		document.getElementById("regiError").style.color = "red";
	}else {
		document.getElementById("regiError").innerText = "사업자 등록번호가 입력되었습니다.";
		document.getElementById("regiError").style.color = "green";
		reToken = true;
	}
}

function check() {
	
	if(name.value == "") {
		alert("세차장 이름을 입력하세요.");
		name.focus();
	}
	else if(post.value == "") {
		alert("우편번호를 입력하세요.");
		post.focus();
	}
	else if(addr.value == "") {
		alert("주소를 입력하세요.");
		addr.focus();
	}
	else if(addr2.value == "") {
		alert("상세주소를 입력하세요.");
		addr2.focus();
	}
	else if(!teToken) {
		alert("전화번호를 입력하세요.");
		tel.focus();
	}
	else if(time.value == "") {
		alert("영업시간을 입력하세요.");
		time.focus();
	}
	else if(dayoff.value == "") {
		alert("휴무일을 입력하세요.");
		dayoff.focus();
	}
	else if(introduce.value == "") {
		alert("세차장 소개를 입력하세요.");
		introduce.focus();
	}
	else if(!inToken) {
		alert("베이수를 입력하세요.");
		inventory.focus();
	}
	else if(!prToken) {
		alert("가격을 입력하세요.");
		price.focus();
	}
	else if(!maToken) {
		alert("사업주 이름을 입력하세요.");
		master.focus();
	}else if(!reToken) {
		alert("등록번호를 입력하세요.");
		regi.focus();
	}else if(!r2Token) {
		alert("등록번호 중복검사를 통과하세요.");
	}else {
		$('form').submit();
	}
	
}
</script>
<script type="text/javascript">
	function daum_post() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	            // 조회된 우편번호를 입력하기 위한 선언
	            // name 이 post 인 태그의 val(값)을 받아온 변수 (data) 내 zonecode 값을 담음
	            $('[name=store_post]').val( data.zonecode);
	            
	            // 지번 주소 : J  도로명 주소 : R
	            var addr =data.userSelectedType == 'J' ? data.jibunAddress : data.roadAddress;
	            
	            // 건물명이 있을 경우 추가
	            if ( data.buildingName != '') addr += ' ('+ data.buildingName + ')'; 
	            	$('[name=store_addr]').eq(0).val( addr );
	        }
	    }).open();
	}
	const image = document.querySelector('.join_profile_image');
	const input_file = document.querySelector('#input_file');
	//이미지 파일 누를 때 input_file도 같이 클릭
	function choose_image() {
		input_file.click();
	}
	//첨부파일 선택시 처리
	  $(document).on('change', '#input_file', function() {
	  	var attached1 = this.files[0];
	  	if (attached1) { // 첨부된 파일이 있을 경우
	  		$('.join_profile_image1').html('<img src="" />');
	  		var reader = new FileReader();
	  		reader.onload = function(e) {
	  			$('.join_profile_image1').attr('src', e.target.result);
	  		}
	  		reader.readAsDataURL(attached1);
	  	}
	  })
	   $(document).on('change', '#input_file', function() {
	  	var attached2 = this.files[1];
	  	if (attached2) { // 첨부된 파일이 있을 경우
	  		$('.join_profile_image2').html('<img src="" />');
	  		var reader = new FileReader();
	  		reader.onload = function(e) {
	  			$('.join_profile_image2').attr('src', e.target.result);
	  		}
	  		reader.readAsDataURL(attached2);
	  	}
	  })
	  $(document).on('change', '#input_file', function() {
	  	var attached3 = this.files[2];
	  	if (attached3) { // 첨부된 파일이 있을 경우
	  		$('.join_profile_image3').html('<img src="" />');
	  		var reader = new FileReader();
	  		reader.onload = function(e) {
	  			$('.join_profile_image3').attr('src', e.target.result);
	  		}
	  		reader.readAsDataURL(attached3);
	  	}
	  })
</script>



