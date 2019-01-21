package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.exception.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTestCase {

	@Autowired
	private IAddressService addressService;

	@Test
	public void create() {
		String username = "Admin";
		Address address = new Address();
		address.setUid(3);
		address.setName("小马同学");
		address.setProvince("440000");
		address.setCity("440300");
		address.setArea("440305");
		Address result
			= addressService.create(
				username, address);
		System.err.println("result=" + result);
	}
	
	@Test
	public void setDefault() {
		try {
			Integer uid = 1;
			Integer id = 20;
			addressService.setDefault(uid, id);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println("错误类型：" + e.getClass().getName());
			System.err.println("错误描述：" + e.getMessage());
		}
	}
	
	@Test
	public void delete() {
		try {
			Integer uid = 1;
			Integer id = 18;
			addressService.delete(uid, id);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println("错误类型：" + e.getClass().getName());
			System.err.println("错误描述：" + e.getMessage());
		}
	}
	
	@Test
	public void getListByUid() {
		Integer uid = 1;
		List<Address> list
			= addressService.getListByUid(uid);
		System.err.println("BEGIN:");
		for (Address address : list) {
			System.err.println(address);
		}
		System.err.println("END.");
	}

}
