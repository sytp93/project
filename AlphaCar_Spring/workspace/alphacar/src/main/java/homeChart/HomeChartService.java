package homeChart;


import java.util.List;


public interface HomeChartService {
	//월별 이용자 수 조회
	List<ChartVO> month_list(int store_number);
	
}
