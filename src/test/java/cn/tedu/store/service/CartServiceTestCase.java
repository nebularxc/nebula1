package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.exception.ServiceException;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTestCase {

	@Autowired
	private ICartService service;

	@Test
	public void addToCart() {
		try {
			String username = "liucs";
			Cart cart = new Cart();
			cart.setUid(9);
			cart.setGid(9527L);
			cart.setCount(2);
			cart.setPrice(800L);
			service.addToCart(username, cart);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println("错误类型：" + e.getClass().getName());
			System.err.println("错误描述：" + e.getMessage());
		}
	}
	
	@Test
	public void addCount() {
		try {
			Integer id = 90;
			Integer uid = 10;
			service.addCount(id, uid);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println("错误类型：" + e.getClass().getName());
			System.err.println("错误描述：" + e.getMessage());
		}
	}
	
	@Test
	public void getByUid() {
		Integer uid = 1;
		List<CartVO> list = service.getByUid(uid);
		System.err.println("BEGIN:");
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
		System.err.println("END.");
	}

}





