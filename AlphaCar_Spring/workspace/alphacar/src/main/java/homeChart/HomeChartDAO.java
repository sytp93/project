package homeChart;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class HomeChartDAO implements HomeChartService {

	@Autowired private SqlSession sql;

	@Override
	public List<ChartVO> month_list(int store_number) {
		// TODO Auto-generated method stub
		return sql.selectList("homeChart.mapper.month_list", store_number);
	}

	


}
