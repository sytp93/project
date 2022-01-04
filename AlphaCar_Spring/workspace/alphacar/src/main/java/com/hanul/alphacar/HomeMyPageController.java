package com.hanul.alphacar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import common.CommonService;
import homeChart.ChartVO;
import homeMypage.CustomerPage;
import homeMypage.HomeMyPageServiceImpl;
import homeMypage.HomeStoreFileVO;
import homeMypage.HomeStoreVO;
import homeQna.QnaPage;
import homeQna.QnaServiceImpl;
import homeQna.QnaVO;
import member.WebMemberServiceImpl;
import member.WebMemberVO;

@Controller
public class HomeMyPageController {
	
	@Autowired private CommonService common;
	@Autowired private HomeMyPageServiceImpl homeService;
	@Autowired private CustomerPage c_page;
	@Autowired private WebMemberServiceImpl member;
	@Autowired private QnaServiceImpl service;
	@Autowired private QnaPage page;
	
	@RequestMapping("/mypage.mp")
	public String login(HttpSession session) {
		
//	  HashMap<String, String> map = new HashMap<String, String>();
//	  map.put("customer_email", "e"); map.put("customer_pw", "e");
//	  session.setAttribute("loginInfo", member.member_login(map));
		 
		
		return "mypage/mypage";
	}
	//회원정보 수정 페이지로 이동
	@RequestMapping("/memberUpdate.mp")
	public String memberUpdate() {
		return "mypage/member_update";
	}
	//회원정보 수정 처리
	@RequestMapping("/memberSubmit.mp")
	public String memberUpdateWork(WebMemberVO vo, MultipartFile image_file, HttpSession session, String attach) {
		WebMemberVO memberVO = (WebMemberVO) session.getAttribute("loginInfo");
		String uuid = session.getServletContext().getRealPath("resources")
				+ "/" + memberVO.getCustomer_picture();
			
		//전송한 이미지 파일이 있다면
		if (! image_file.isEmpty()) {
			vo.setCustomer_picture(common.fileUpload("profiles", image_file, session));
			//기존에 가지고 있었던 파일 패스값이 있다면
			if ( memberVO.getCustomer_picture() != null ) {
				File f = new File ( uuid );
				// 기존 첨부 파일 삭제
				if (f.exists()) f.delete();
			} 
			
		}else {
			//전송한 이미지가 없을 경우 기존 주소 유지
			vo.setCustomer_picture(memberVO.getCustomer_picture());
		}
		
		//화면에서 변경 입력한 정보를 db에 변경 저장한 후 상세화면으로 연결
		homeService.home_member_update(vo);	
		session.setAttribute("loginInfo", vo);

	    //session.setAttribute("loginInfo", member.member_login(map));
		
		
		return "redirect:mypage.mp";

	}	
    //회원 탈퇴
    @RequestMapping("/memberDelete.mp")
    public String memberDelete(HttpSession session, String customer_email) {
        homeService.home_member_delete(customer_email);
        session.removeAttribute("loginInfo");
        return "redirect:/";
    }
    
	//내 가게 정보
	@RequestMapping("/memberCompany.mp")
	public String memberCompany(HttpSession session, Model model) {
		WebMemberVO member = (WebMemberVO) session.getAttribute("loginInfo");
		model.addAttribute("company", homeService.company_list(member.getCustomer_email()));
		return "mypage/member_company";
	}
	
	
	
	//1:1 문의 내역
	@RequestMapping("/memberContact.mp")
	public String memberContact(HttpSession session, Model model, 
			@RequestParam (defaultValue = "1") int curPage,
			String search, String keyword, QnaVO vo) {
		
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		//((WebMemberVO) session.getAttribute("loginInfo")).getCustomer_email() ;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("customer_email", ((WebMemberVO) session.getAttribute("loginInfo")).getCustomer_email());
		map.put("page", page);
		//DB에서 공지글 목록을 조회한 후 목록화면에 출력
		model.addAttribute("page", service.member_qna_list(map));
		
		return "mypage/member_contact";
	}

	//가게 삭제
	@RequestMapping("/memberCompanyDelete.mp")
	public String memberCompanyDelete(int store_number) {
		homeService.company_delete(store_number);
		return "redirect:memberCompany.mp";
	}
	
	//가게 그래프 보러가기
	@RequestMapping("/memberCompanyGraph.mp")
	public String memberCompanyGraph(HttpSession session, Model model, int store_number ) {
		
		model.addAttribute("store_number", store_number);
		model.addAttribute("vo", homeService.companyId_list(store_number));
		return "mypage/member_company_graph";
	}
	
	//가게 수정 페이지 요청
	@RequestMapping("/memberCompanyUpdate.mp")
	public String memberCompanyUpdate(HttpSession session, Model model, int store_number) {
		
			model.addAttribute("vo", homeService.companyId_list(store_number));
			model.addAttribute("img", homeService.company_img(store_number));
			
			return "mypage/member_company_update";
		
	}

	// 가게 수정 저장 처리 요청
	
	@RequestMapping ("/update_work.mp")
	public String update_work(HomeStoreVO vo, HttpSession session, int inventory, int store_number, 
			@RequestParam("article_file") List<MultipartFile> mf) {
		ArrayList<String> storeInventory = new ArrayList<>();
		HomeStoreVO hvo = homeService.companyId_list(store_number);
		
		for (int i =0; i< 9; i++){
			storeInventory.add("X");
		}
		for(int i =0; i<inventory; i++){
			storeInventory.set(i, "Y");
		}
		for (int i = 0; i < storeInventory.size(); i++) {
			vo.setNow_state(storeInventory.get(i));
			
		} 
		
    	vo.setStore_number(store_number);
    	
    	
    	homeService.company_update(vo);
    	
    	HomeStoreFileVO fvo = new HomeStoreFileVO(); 
    	
    	if(mf.size() > 0 && !mf.get(0).getOriginalFilename().equals("")) {
    		int rank = 0;
    		for(MultipartFile file:mf) {
    			fvo.setStore_number(store_number);
    			fvo.setImgname(file.getOriginalFilename());
    			fvo.setImgpath(common.fileUpload("company", file, session));
    			fvo.setRank(++rank);
    			homeService.companyImg_update(fvo);
    		}
    	
        }
    	
		
		return "redirect:memberCompany.mp";
	}
	
	//신규 가게등록 페이지 요청
	@RequestMapping("/memberCompanyInsert.mp")
	public String memberCompanyInsert(HttpSession session, Model model) {
		return "mypage/member_company_insert";
	}
	
	// 사업자 등록번호 중복검사
	@ResponseBody
	@RequestMapping("/regiDupl.mp")
	public boolean memberCompanyDuplicate(String id) {
		System.out.println(id);
		return homeService.memberCompanyDuplicate(id);
	}

	//신규 가게 저장 요청
	@RequestMapping(value = "/homeStoreRegister.mp", produces = "text/html; charset=utf-8")
	public String homeStoreRegister(HomeStoreVO vo, HttpSession session, int inventory,  
			@RequestParam("article_file") List<MultipartFile> mf) {
		vo.setCustomer_email( ( (WebMemberVO) session.getAttribute("loginInfo")).getCustomer_email() );
		
		ArrayList<String> storeInventory = new ArrayList<>();
		for (int i =0; i< 9; i++){
			storeInventory.add("X");
        }
        for(int i =0; i<inventory; i++){
        	storeInventory.set(i, "Y");
        }
        
        for (int i = 0; i < storeInventory.size(); i++) {
			vo.setNow_state(storeInventory.get(i));
			
		}
        
        homeService.company_insert(vo);
        
        HomeStoreFileVO fvo = new HomeStoreFileVO(); 
        
    	if(mf.size() > 0 && !mf.get(0).getOriginalFilename().equals("")) {
    		int rank = 0;
    		for(MultipartFile file:mf) {
    			fvo.setImgname(file.getOriginalFilename());
    			fvo.setImgpath(common.fileUpload("company", file, session));
    			fvo.setRank(++rank);
    			homeService.companyImg_insert(fvo);
  			} 
		
		
		
    	
        }

    	return "redirect:memberCompany.mp";
       
	}
	
	
	//회원 정보 리스트로 반환
	@RequestMapping("/masterMemberList.mp")
	public String masterMemberList(HttpSession session, Model model, 
			@RequestParam (defaultValue = "1") int curPage,
			String search, String keyword) {
		
		c_page.setCurPage(curPage);
		c_page.setSearch(search);
		c_page.setKeyword(keyword);
		
		model.addAttribute("page", homeService.customer_list(c_page));
		return "mypage/master_member_list";
	}
	
	//히든페이지
	@RequestMapping("/hidden.mp")
	public String hidden(HttpSession session, Model model, 
			@RequestParam (defaultValue = "1") int curPage,
			String search, String keyword, String customer_email) {
		
		c_page.setCurPage(curPage);
		c_page.setSearch(search);
		c_page.setKeyword(keyword);
		
		WebMemberVO vo = new WebMemberVO();
		vo.setCustomer_email(customer_email);
		
		model.addAttribute("uri", "mastermemberUpdate.mp");
		model.addAttribute("page", c_page);
		model.addAttribute("vo", vo);
		return "mypage/redirect";
	}
	
	//회원정보 수정 마스터 페이지로 이동
	@RequestMapping("/mastermemberUpdate.mp")
	public String masterMemberUpdate(Model model, String customer_email) {
		WebMemberVO vo = homeService.home_member_select(customer_email);
		model.addAttribute("vo", vo);

		
		return "mypage/master_member_update";
	}
	
	//마스터가 하는 회원 삭제
    @RequestMapping("/mastermemberDelete.mp")
    public String mastermemberDelete(HttpSession session, String customer_email) {
        homeService.home_member_delete(customer_email);
        session.removeAttribute("loginInfo");
        return "redirect:masterMemberList.mp";
        
    }
    //마스터가 하는 회원정보 수정 처리
  	@RequestMapping("/mastermemberSubmit.mp")
  	public String mastermemberUpdateWork(WebMemberVO vo, MultipartFile image_file, HttpSession session, String attach) {
  		String uuid = session.getServletContext().getRealPath("resources")
  				+ "/" + vo.getCustomer_picture();
  		//전송한 이미지 파일이 있다면
  		if (! image_file.isEmpty()) {
  			vo.setCustomer_picture(common.fileUpload("profiles", image_file, session));
  			//기존에 가지고 있었던 파일 패스값이 있다면
  			if ( vo.getCustomer_picture() != null ) {
  				File f = new File ( uuid );
  				// 기존 첨부 파일 삭제
  				if (f.exists()) f.delete();
  			} 
  			
  		}else {
  			//전송한 이미지가 없을 경우 기존 주소 유지
  			vo.setCustomer_picture(vo.getCustomer_picture());
  		}
  		homeService.home_member_update(vo);	
  		//화면에서 변경 입력한 정보를 db에 변경 저장한 후 상세화면으로 연결

  		return "redirect:masterMemberList.mp";

  	}
	
	//1:1문의 처리
	@RequestMapping("/masterContact.mp")
	public String masterContact(HttpSession session, Model model, 
			@RequestParam (defaultValue = "1") int curPage,
			String search, String keyword, WebMemberVO vo) {
		
			page.setCurPage(curPage);
			page.setSearch(search);
			page.setKeyword(keyword);
			
			//DB에서 공지글 목록을 조회한 후 목록화면에 출력
			model.addAttribute("page", service.qna_list(page));
			return "mypage/master_contact";
		
	}
	
}
