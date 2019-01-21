package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.GoodsCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsCategoryServiceTestCase {

	@Autowired
	private IGoodsCategoryService service;

	@Test
	public void getListByParent() {
		Long parentId = 161L;
		List<GoodsCategory> result 
			= service.getByParent(parentId);
		System.err.println("BEGIN:");
		for (GoodsCategory data : result) {
			System.err.println(data);
		}
		System.err.println("END.");
	}
	
}
