package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictMapperTestCase {

	@Autowired
	private DistrictMapper districtMapper;
	
	@Test
	public void findByParent() {
		String parent = "111111";
		List<District> result
			= districtMapper.findByParent(parent);
		System.err.println("BEGIN.");
		for (District district : result) {
			System.err.println(district);
		}
		System.err.println("END.");
	}
	
	@Test
	public void findByCode() {
		String code = "371999";
		District district
			= districtMapper.findByCode(code);
		System.err.println("district=" + district);
	}
	
}








