package com.hanul.alphacar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import homeChart.ChartVO;
import homeChart.HomeChartServiceImpl;
	

@Controller
public class ChartController {
	
	@Autowired private HomeChartServiceImpl service;
	
//	@RequestMapping("/")
//	public String chart(Model model) {
//		ArrayList<ChartVO> list = new ArrayList<ChartVO>();
//	
//		
//		model.addAttribute("list", list);
//		return  "home";
//	}
	
	
//	@ResponseBody
//	@RequestMapping("/month_list")
//	public String month_list(Model model, int store_number) {
//		
//		Gson gson = new Gson();
//		ChartVO vo = new ChartVO();
//		List<ChartVO> list  =service.month_list(store_number);
//		
//		for (int i = 0; i < list.size(); i++) {
//			if (i == 0 ) {
////				list.get(i).setM01(list.get(i).getTt());
////				list.get(i).setTitle("M01");
//				list.get(i).setColor("rgba(255, 99, 132, 1)");
//			} else if (i == 1 ){
////				list.get(i).setM02(list.get(i).getTt());
////				list.get(i).setTitle("M02");
//				list.get(i).setColor("rgba(0, 255, 132, 1)");
//			} else if (i == 2 ){
////				list.get(i).setM03(list.get(i).getTt());
////				list.get(i).setTitle("M03");
//				list.get(i).setColor("rgba(0, 0, 255, 1)");
//			} else if (i == 3 ){
////				list.get(i).setM04(list.get(i).getTt());
////				list.get(i).setTitle("M04");
//				list.get(i).setColor("rgba(111, 222, 111, 1)");
//			} else if (i == 4 ){
////				list.get(i).setM05(list.get(i).getTt());
////				list.get(i).setTitle("M05");
//				list.get(i).setColor("rgba(255, 99, 132, 1)");
//			} else if (i == 5 ){
////				list.get(i).setM06(list.get(i).getTt());
////				list.get(i).setTitle("M06");
//				list.get(i).setColor("rgba(255, 99, 132, 1)");
//			} else if (i == 6 ){
////				list.get(i).setM07(list.get(i).getTt());
////				list.get(i).setTitle("M07");
//				list.get(i).setColor("rgba(255, 99, 132, 1)");
//			} else if (i == 7 ){
////				list.get(i).setM08(list.get(i).getTt());
////				list.get(i).setTitle("M08");
//				list.get(i).setColor("rgba(255, 99, 132, 1)");
//			} else if (i == 8 ){
////				list.get(i).setM09(list.get(i).getTt());
////				list.get(i).setTitle("M09");
//				list.get(i).setColor("rgba(255, 99, 132, 1)");
//			} else if (i == 9 ){
////				list.get(i).setM10(list.get(i).getTt());
////				list.get(i).setTitle("M10");
//				list.get(i).setColor("rgba(255, 99, 132, 1)");
//			} else if (i == 10 ){
////				list.get(i).setM11(list.get(i).getTt());
////				list.get(i).setTitle("M11");
//				list.get(i).setColor("rgba(255, 99, 132, 1)");
//			} else if (i == 11 ){
////				list.get(i).setM12(list.get(i).getTt());
////				list.get(i).setTitle("M12");
//				list.get(i).setColor("rgba(255, 99, 132, 1)");
//			} 
//			
//		}
//	
//		
//		
//		return gson.toJson(list);
//	}
	@ResponseBody
	   @RequestMapping("/month_list")
	   public String month_list(Model model, int store_number)  throws Exception{
	      Gson gson = new Gson();

	      List<ChartVO> list = service.month_list(store_number);
	      ObjectMapper mapper = new ObjectMapper();
	      String datas = mapper.writeValueAsString(list);
	      
	      return gson.toJson(datas);
	   }
	
}
