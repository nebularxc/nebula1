package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.District;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistrictServiceTestCase {

	@Autowired
	private IDistrictService service;

	@Test
	public void getListByParent() {
		String parent = "86";
		List<District> result 
			= service.getListByParent(parent);
		System.err.println("BEGIN:");
		for (District district : result) {
			System.err.println(district);
		}
		System.err.println("END.");
	}
	
	@Test
	public void getByCode() {
		String code = "320000";
		District district
			= service.getByCode(code);
		System.err.println("district=" + district);
	}

}
