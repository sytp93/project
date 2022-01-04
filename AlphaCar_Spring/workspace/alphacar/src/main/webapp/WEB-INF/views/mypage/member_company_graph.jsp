<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="mypage_image">
    </div>
    <div class="mypage_select">
      <div class="mypage_userinfo">
        <a href=""><p class="mypage_userinfo_select">회원정보</p></a>
        <a href=""><p>보안설정</p></a>
      </div>
    </div>
  </nav>
  <!-- 메인 시작 -->
  <main class="mypage">
    <div id="page">
      <h3>${vo.store_name } </h3>
      	<canvas  id="myChart"></canvas>
    </div>
  </main>
  
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js"></script>
 
	<script type="text/javascript"  charset="UTF-8">         
	var csrfHeaderName = "${_csrf.headerName}";
	var csrfTokenValue = "${_csrf.token}";
	const number = '${store_number}';
	$.ajax({
	   type: "POST",
	    dataType: "json",
	    data: {store_number:number},
	    url: "month_list",
	    beforeSend: function(xhr){
	        xhr.setRequestHeader(csrfHeaderName, csrfTokenValue)
	    },
	   success : function( datas ) 
	      {             
	      tt = JSON.parse(datas);
	      console.log(tt[0].tt);
	      console.log(tt[1].tt);
	      var context = document
	       .getElementById('myChart')
	       .getContext('2d');            
	   var myChart = new Chart(context, {
	       type: 'bar', // 차트의 형태
	       data: { // 차트에 들어갈 데이터
	           labels: [
	               //x 축
	               '1','2','3','4','5','6','7','8','9','10','11','12'
	           ],
	           datasets: [
	               { //데이터
	                   label: '월별이용자수', //차트 제목
	                   fill: false, // line 형태일 때, 선 안쪽을 채우는지 안채우는지
	                   data: [
	                      tt[0].tt, tt[1].tt, tt[2].tt, tt[3].tt, tt[4].tt, tt[5].tt, tt[6].tt, tt[7].tt, tt[8].tt, tt[9].tt,tt[10].tt, tt[11].tt,//x축 label에 대응되는 데이터 값
	                   ],
	                   backgroundColor: [
	                       //색상
	                       'rgba(255, 99, 132, 0.2)',
	                       'rgba(54, 162, 235, 0.2)',
	                       'rgba(255, 206, 86, 0.2)',
	                       'rgba(75, 192, 192, 0.2)',
	                       'rgba(153, 102, 255, 0.2)',
	                       'rgba(255, 159, 64, 0.2)',
	                       'rgba(255, 99, 132, 0.2)',
	                       'rgba(54, 162, 235, 0.2)',
	                       'rgba(255, 206, 86, 0.2)',
	                       'rgba(75, 192, 192, 0.2)',
	                       'rgba(153, 102, 255, 0.2)',
	                       'rgba(255, 159, 64, 0.2)'
	                   ],
	                   borderColor: [
	                       //경계선 색상
	                       'rgba(255, 99, 132, 1)',
	                       'rgba(54, 162, 235, 1)',
	                       'rgba(255, 206, 86, 1)',
	                       'rgba(75, 192, 192, 1)',
	                       'rgba(153, 102, 255, 1)',
	                       'rgba(255, 159, 64, 1)',
	                       'rgba(255, 99, 132, 1)',
	                       'rgba(54, 162, 235, 1)',
	                       'rgba(255, 206, 86, 1)',
	                       'rgba(75, 192, 192, 1)',
	                       'rgba(153, 102, 255, 1)',
	                       'rgba(255, 159, 64, 1)'
	                   ],
	                   borderWidth: 1 //경계선 굵기
	               }/* ,
	               {
	                   label: 'test2',
	                   fill: false,
	                   data: [
	                       8, 34, 12, 24
	                   ],
	                   backgroundColor: 'rgb(157, 109, 12)',
	                   borderColor: 'rgb(157, 109, 12)'
	               } */
	           ]
	       },
	       options: {
	           scales: {
	               yAxes: [
	                   {
	                       ticks: {
	                           beginAtZero: true
	                       }
	                   }
	               ]
	           }
	       }
	   });
	   }, error : function (req, text) {
	      alert(text + ':' + req.status);
	   }
	});            
	</script>
