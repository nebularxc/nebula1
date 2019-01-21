package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.GoodsCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsCategoryMapperTestCase {

	@Autowired
	private GoodsCategoryMapper mapper;
	
	@Test
	public void findByParent() {
		Long parentId = 161L;
		List<GoodsCategory> list
			= mapper.findByParent(parentId);
		System.err.println("BEGIN:");
		for (GoodsCategory data : list) {
			System.err.println(data);
		}
		System.err.println("END.");
	}
	
}








