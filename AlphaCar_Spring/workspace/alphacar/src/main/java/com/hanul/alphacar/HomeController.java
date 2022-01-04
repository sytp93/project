package com.hanul.alphacar;

import java.net.MulticastSocket;
import java.util.List;
import java.util.Locale;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import homeMypage.HomeCompanyVO;
import homeMypage.HomeMyPageServiceImpl;
import homeNotice.HomeNoticePage;
import homeNotice.HomeNoticeServiceImpl;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired private HomeNoticeServiceImpl service;
	@Autowired private HomeMyPageServiceImpl companyService;
	@Autowired private HomeNoticePage page;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, 
			@RequestParam (defaultValue = "1") int curPage, HttpSession session) {

		page.setCurPage(curPage);
		model.addAttribute("page", service.notice_list(page));
		model.addAttribute("wash", companyService.company_list_all_fv());
//		List<HomeCompanyVO> vo = companyService.company_list_all_fv();
//		for (int i = 0; i < vo.size(); i++) {
//			System.out.println(vo.get(i).getImgpath());
//		}
		
		return "index";
	}
}
