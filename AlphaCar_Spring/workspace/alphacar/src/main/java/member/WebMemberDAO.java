package member;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class WebMemberDAO implements WebMemberService {

	@Autowired private SqlSession sql;
	
	@Override
	public WebMemberVO member_login(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return sql.selectOne("webmember.mapper.member_login", map);
	}

	@Override
	public boolean member_join(WebMemberVO vo) {
		// TODO Auto-generated method stub
		return sql.insert("webmember.mapper.member_join", vo) == 1 ? true : false;
	}

	@Override
	public boolean member_id_check(String id) {
		// TODO Auto-generated method stub
		return (Integer) sql.selectOne("webmember.mapper.id_check", id) == 0 ? true : false;
	}

	@Override
	public boolean member_social_email(WebMemberVO vo) {
		return (Integer) sql.selectOne("webmember.mapper.social_email", vo) == 0 ? false : true;
	}

	@Override
	public boolean member_social_insert(WebMemberVO vo) {
		return sql.insert("webmember.mapper.social_insert", vo) == 0 ? false : true;
	}

	@Override
	public boolean member_social_update(WebMemberVO vo) {
		return sql.update("webmember.mapper.social_update", vo) == 0 ? false : true;
	}

	@Override
	public WebMemberVO member_social_login(String kakao) {
		return sql.selectOne("webmember.mapper.social_login", kakao);
	}

	@Override
	public boolean login_info(WebMemberVO vo) {
		
		return sql.update("webmember.mapper.login_info", vo)== 0 ? false : true;
	}

	@Override
	public WebMemberVO auto_login(WebMemberVO auto) {
		
		return sql.selectOne("webmember.mapper.auto_login", auto);
	}
}
