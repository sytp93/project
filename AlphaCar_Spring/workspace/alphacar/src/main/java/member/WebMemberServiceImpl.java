package member;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebMemberServiceImpl implements WebMemberService {

	@Autowired private WebMemberDAO dao;

	@Override
	public WebMemberVO member_login(HashMap<String, String> map) {
		return dao.member_login(map);
	}

	@Override
	public boolean member_join(WebMemberVO vo) {
		return dao.member_join(vo);
	}

	@Override
	public boolean member_id_check(String id) {
		return dao.member_id_check(id);
	}

	@Override
	public boolean member_social_email(WebMemberVO vo) {
		return dao.member_social_email(vo);
	}

	@Override
	public boolean member_social_insert(WebMemberVO vo) {
		return dao.member_social_insert(vo);
	}

	@Override
	public boolean member_social_update(WebMemberVO vo) {
		return dao.member_social_update(vo);
	}

	@Override
	public WebMemberVO member_social_login(String kakao) {
		return dao.member_social_login(kakao);
	}

	@Override
	public boolean login_info(WebMemberVO vo) {
		// TODO Auto-generated method stub
		return dao.login_info(vo);
	}

	@Override
	public WebMemberVO auto_login(WebMemberVO auto) {
		// TODO Auto-generated method stub
		return dao.auto_login(auto);
	}

}
