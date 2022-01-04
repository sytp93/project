package security;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import member.MemberService;
import member.MemberVO;
import member.WebMemberVO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements AuthenticationSuccessHandler{

	@Autowired
	MemberService memberservice;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
	 	
//    	ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
//    	
//    	WebMemberVO vo = new WebMemberVO();
//    	vo.setCode(ResponseDataCode.SUCCESS);
//    	vo.setStatus(ResponseDataStatus.SUCCESS);
//    	
//    	String prevPage = request.getSession().getAttribute("prevPage").toString();	//이전 페이지 가져오기
//    	 
//    	Map<String, String> items = new HashMap<String,String>();	
//    	items.put("url", prevPage);	// 이전 페이지 저장
//    	responseDataDTO.setItem(items);
//    	
//    	response.setCharacterEncoding("UTF-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().print(mapper.writeValueAsString(responseDataDTO));
//        response.getWriter().flush();
		
	}

}
