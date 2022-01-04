package security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	

	@Override
	public void configure(WebSecurity web) throws Exception {

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/ioTCarWash").permitAll();
		
		http.csrf().disable();
		
		http.formLogin()
		.loginPage("/homeLogin")
		//해당url로 접근하면 로그인페이지로 이동하게된다.
		.loginProcessingUrl("/webLogin")
		//사용자가 로그인화면에서 아이디/비밀번호를 입력후 전송버튼 클릭시 전송되는 url이다.
		//form태그의 action값과 매핑된다.
		.usernameParameter("customer_email")
		//로그인폼에 아이디와 비밀번호의 파라미터(name속성)명을 적어준다.
		.passwordParameter("customer_pw");
		
		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/homeLogout"))
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true);
		//logout으로 요청이 오면 로그아웃을 시켜버리고 / 경로로 리다이렉트 해주며
		//SecurityContextHolder에 저장된 세션정보를 지운다.
		
		
			
	}
	
}
