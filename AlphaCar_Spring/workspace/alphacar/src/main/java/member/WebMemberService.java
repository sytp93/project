package member;

import java.util.HashMap;

public interface WebMemberService {

	//아이디, 비번 일치하는 회원정보 조회
	WebMemberVO member_login(HashMap<String, String> map);
	
	//회원가입
	boolean member_join(WebMemberVO vo);
	
	//아이디 유효성 검사
	boolean member_id_check(String id);

	// 소셜 회원 정보 존재여부 (R)
	boolean member_social_email(WebMemberVO vo);	
	
	// 소셜 회원 정보 신규 저장(C)
	boolean member_social_insert(WebMemberVO vo);
	
	// 소셜 회원 정보 변경 저장(U)
	boolean member_social_update(WebMemberVO vo);
	
	WebMemberVO member_social_login(String kakao);
	
	// 자동 로그인 정보 저장
	boolean login_info(WebMemberVO vo);
	
	// 자동 로그인 하기
	WebMemberVO auto_login(WebMemberVO auto);
}
