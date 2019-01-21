package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartMapperTestCase {

	@Autowired
	private CartMapper mapper;
	
	@Test
	public void addnew() {
		Cart cart = new Cart();
		cart.setUid(1);
		cart.setGid(100L);
		cart.setPrice(5000L);
		cart.setCount(8);
		Integer rows = mapper.addnew(cart);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateCount() {
		Integer id = 1;
		Integer count = 6;
		Integer rows = mapper.updateCount(id, count);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void findByUidAndGid() {
		Integer uid = 1;
		Long goodsId = 100L;
		Cart cart = mapper.findByUidAndGid(uid, goodsId);
		System.err.println(cart);
	}
	
	@Test
	public void findById() {
		Integer id = 9;
		Cart cart = mapper.findById(id);
		System.err.println(cart);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 1;
		List<CartVO> list = mapper.findByUid(uid);
		System.err.println("BEGIN:");
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
		System.err.println("END.");
	}
	
}








