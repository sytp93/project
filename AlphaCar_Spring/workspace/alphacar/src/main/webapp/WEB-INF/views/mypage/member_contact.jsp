<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
  <main>
    <div id="page">
      <h1>내 1:1 문의 내역</h1>
      <div class="page_index">
      <form action="memberContact.mp" method="post">
		    <input type="hidden" name="curPage" value="1" /> 
	        <div class="page_select">
	          <ul>
	           	<li onclick="location.href='memberContact.mp'">전체</li>
		          <li onclick="location.href='memberContact.mp?search=user-info'">고객</li>
		          <li onclick="location.href='memberContact.mp?search=store'">가게</li>
		          <li onclick="location.href='memberContact.mp?search=app_web'">모바일/홈페이지</li>
		          <li onclick="location.href='memberContact.mp?search=alphacar'">알파카</li>
	          </ul>
	        </div>
        </form>
        <!-- service 검색기능 -->
        <form action="memberContact.mp" method="get" class="page_search">
      		<input type="hidden" name="curPage" value="1" /> 
          <div class="page_search_index">
            <select name="service_search_index" id="service_search_index">
              <option value="all" ${page.search eq 'all' ? 'selected' : '' }>전체</option>
	            <option value="user-info" ${page.search eq 'user-info' ? 'selected' : '' }>고객</option>
	            <option value="store" ${page.search eq 'store' ? 'selected' : '' }>가게</option>
	            <option value="app_web" ${page.search eq 'app_web' ? 'selected' : '' }>모바일/홈페이지</option>
	            <option value="alphacar" ${page.search eq 'alphacar' ? 'selected' : '' }>알파카</option>
            </select>
          </div>
          <div class="page_search_box">
           <input type="text" placeholder="search" name="keyword" value="${page.keyword}">
           <i class="fas fa-search" onclick='$("form").submit()'></i>
         </div>
        </form>
      </div>
      <!-- notice 글 목록  -->
      <div class="page_list">
        <div class="page_list_name">
          <h3>글</h3>
	        <h3>작성자</h3>
	        <h3>조회수</h3>
	        <h3>활동</h3>
        </div>
       <div class="page_list_box">
      	<c:forEach items="${page.list}" var="vo">
      		<div class="page_list_content">
	          <div class="page_list_content_title">
	          	<c:if test="${loginInfo.customer_email eq vo.customer_email || loginInfo.admin eq 'A'}">
		          	<c:forEach begin="1" end='${vo.qna_indent }' var='i'>
									${i eq vo.qna_indent ? "<img src='img/re.gif' />" : "&nbsp;&nbsp;" }
								</c:forEach>			
	            	<a href='detail.qna?qna_id=${vo.qna_id }'>
	            	<c:if test="${vo.qna_attribute eq 'C'}">
	              	<p>[고객]</p>
	              </c:if>
	              <c:if test="${vo.qna_attribute eq 'S'}">
	              	<p>[가게]</p>
	              </c:if>
	              <c:if test="${vo.qna_attribute eq 'M'}">
	              	<p>[모바일/홈페이지]</p>
	              </c:if>
	              <c:if test="${vo.qna_attribute eq 'A'}">
	              	<p>[알파카]</p>
	              </c:if>
	              <p>${vo.qna_title}</p>
	            </a>
	            </c:if>
	          	<c:if test="${loginInfo.customer_email ne vo.customer_email && loginInfo.admin ne 'A'}">
	          		<c:forEach begin="1" end='${vo.qna_indent }' var='i'>
									${i eq vo.qna_indent ? "<img src='img/re.gif' />" : "&nbsp;&nbsp;" }
								</c:forEach>			
	            	<a href='check.qna?qna_id=${vo.qna_id }'>
	            	<c:if test="${vo.qna_attribute eq 'C'}">
	              	<p>[고객]</p>
	              </c:if>
	              <c:if test="${vo.qna_attribute eq 'S'}">
	              	<p>[가게]</p>
	              </c:if>
	              <c:if test="${vo.qna_attribute eq 'M'}">
	              	<p>[모바일/홈페이지]</p>
	              </c:if>
	              <c:if test="${vo.qna_attribute eq 'A'}">
	              	<p>[알파카]</p>
	              </c:if>
	              <p>${vo.qna_title}</p>
	            </a>
	            </c:if>
	              
	          </div>
	          <p>${vo.customer_name}</p>
	          <p>${vo.qna_readcnt}</p>
	          <p>${vo.qna_time}</p>
	        </div>
				</c:forEach> 
	     </div>  
      </div>
      <div class="page_content_create">
        <button type="button" onclick="location.href='write.qna'">글 작성</button>
      </div>
      
      <!-- 페이징 처리 -->
      <div class="notice_paging">
        <jsp:include page="/WEB-INF/views/include/page.jsp" />  
      </div>    
    </div>
  </main>
  
 <script type="text/javascript">
 
 </script>