package com.hanul.alphacar;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import common.CommonService;
import member.WebMemberServiceImpl;
import member.WebMemberVO;

@Controller
public class HomeMemberController {
	
	@Autowired private WebMemberServiceImpl service;
	@Autowired private CommonService common;
	
	String kakao_client_id = "5c5deb7b4d423a3f31e34f160359f421";
	
	// 로그아웃 처리 요청
	@RequestMapping ("/homeLogout")
	public String logout (HttpSession session) {
		// 세션에 담긴 로그인 정보를 삭제한다.
		session.removeAttribute("loginInfo");
		session.removeAttribute("revid");
		return "redirect:/";	
	}
	
	//로그인 처리 요청
	@ResponseBody
	@RequestMapping("/webLogin")
	public Boolean webLogin(HttpSession session, String customer_email, String customer_pw,
							String chk) throws Exception {
		
		// ip 주소와 컴퓨터 네임을 찾는다.
		String ip_addr = Inet4Address.getLocalHost().getHostAddress();
		String com_name = Inet4Address.getLocalHost().getHostName();
		
		System.out.println(ip_addr+com_name+chk);
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("customer_email", customer_email);
		map.put("customer_pw", customer_pw);
		map.put("chk", chk);
		WebMemberVO vo = service.member_login(map);
		session.setAttribute("loginInfo", vo);
		
		// 체크박스가 true 이면 vo에 값을 저장 서비스 실행
		if (chk.equals("true") ) {
			vo.setIp_addr(ip_addr);
			vo.setCom_name(com_name);
			service.login_info(vo);
			service.auto_login(vo);
		}
		
		return vo == null ? false : true;
	}
	
	//로그인 화면 요청
	@RequestMapping("/homeLogin")
	public String login(HttpSession session) {
		
		session.setAttribute("category", "homeLogin");
		return "member/login";
	}
	
	
	//회원가입 화면 요청
	@RequestMapping("/homeJoin")
	public String join() {
		return "member/join";
	}
	
	//이메일 주소 중복 검사
	@ResponseBody
	@RequestMapping("/email_dupl")
	public boolean id_check(String id) {
		return service.member_id_check(id);
	}

	//회원가입 요청
	@ResponseBody
	@RequestMapping(value = "/homeRegister", produces = "text/html; charset=utf-8")
	public String homeRegister(HttpSession session, WebMemberVO vo, HttpServletRequest req, String join_company,
			MultipartFile file) {
		StringBuffer msg = new StringBuffer("<script>");
		
		if (! file.isEmpty()) {
			vo.setCustomer_picture(common.fileUpload("profiles", file, session));
		}else {
			//전송한 이미지가 없을 경우 null을 입력
			vo.setCustomer_picture(null);
		}
		
		if (service.member_join(vo)) {
			msg.append("alert('회원가입을 축하드립니다.'); location='")
				.append(req.getContextPath()).append("'");
		} else {
			msg.append("alert('회원가입 실패'); location='homeJoin' ");
		}
		msg.append("</script>");
		return msg.toString();
	}
	
	// 카카오 로그인 요청
	@RequestMapping ("/kakaoLogin")
	public String kakaoLogin(HttpSession session) {
		
		
		// 카카오 로그인 연동 URL 생성
		String state = UUID.randomUUID().toString();
		
		session.setAttribute("state", state);
		
		StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/authorize?response_type=code");
		url.append("&client_id=").append(kakao_client_id);
		url.append("&state=").append(state);
		url.append("&redirect_uri=http://localhost:8989/alphacar/kakaocallback");
		return "redirect:" + url.toString();
	}
	
	// 카카오 Callback 메소드 선언
	@RequestMapping("/kakaocallback")
	public String kakaocallback(@RequestParam(required = false) String code, String state, 
			@RequestParam(required = false) String error, HttpSession session) {
		
		// state 값이 맞지 않거나 error 가 발생하면 토큰 발급 불가
		if (!state.equals( session.getAttribute("state")) || error != null) {
			return "redirect:/"; 	// 메인 페이지로 이동
		}
		StringBuffer url = new StringBuffer("https://kauth.kakao.com/oauth/token?grant_type=authorization_code");
		url.append("&client_id=").append(kakao_client_id);
		url.append("&code=").append(code);
		
		JSONObject json = new JSONObject( common.requestAPI(url));
		
		String token = json.getString("access_token");
		String type = json.getString("token_type");
		
		
		url = new StringBuffer("https://kapi.kakao.com/v2/user/me");
		json = new JSONObject( common.requestAPI(url, type + " " + token) );
		
 
		if (! json.isEmpty()) {

			// 회원정보 DB 에 값을 담아서 관리 _ MemberVO
			WebMemberVO vo = new WebMemberVO();
			vo.setSocial("K"); 
			vo.setCustomer_email(json.get("id").toString());
			
			// 소셜 로그인을 했을 경우 해당 정보를 저장하여 소셜 구분을 위함.
			json = json.getJSONObject("kakao_account");
			vo.setKakao(json.getString("email"));
			vo.setCustomer_name(json.getJSONObject("profile").getString("nickname"));
			
			// 카카오 최초 로그인인 경우 회원정보 저장 (insert)
			// 카카오 로그인 이력이 있어 회원정보가 있다면 변경 저장
			if (service.member_social_email(vo))
				service.member_social_update(vo);
			else
				service.member_social_insert(vo);
			
			WebMemberVO login = service.member_social_login(vo.getKakao());
			session.setAttribute("loginInfo", login);
		}
		
		return "redirect:/";
	}
}
