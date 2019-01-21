package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressMapperTestCase {

	@Autowired
	private AddressMapper addressMapper;
	
	@Test
	public void addnew() {
		Address address = new Address();
		address.setUid(2);
		address.setName("小张老师");
		Integer rows = addressMapper.addnew(address);
		System.err.println("rows=" + rows);
		System.err.println(address);
	}
	
	@Test
	public void updateNonDefault() {
		Integer uid = 1;
		Integer rows = addressMapper.updateNonDefault(uid);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateDefault() {
		Integer id = 17;
		Integer rows = addressMapper.updateDefault(id);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void getCountByUid() {
		Integer uid = 3;
		Integer count 
			= addressMapper.getCountByUid(uid);
		System.err.println("count=" + count);
	}
	
	@Test
	public void findByUid() {
		Integer uid = 1;
		List<Address> list
			= addressMapper.findByUid(uid);
		System.err.println("BEGIN:");
		for (Address address : list) {
			System.err.println(address);
		}
		System.err.println("END.");
	}
	
	@Test
	public void findById() {
		Integer id = 170;
		Address address
			= addressMapper.findById(id);
		System.err.println("address=" + address);
	}
	
	@Test
	public void findLastModified() {
		Integer uid = 1;
		Address address
			= addressMapper.findLastModified(uid);
		System.err.println("address=" + address);
	}
	
	@Test
	public void deleteById() {
		Integer id = 19;
		Integer rows
			= addressMapper.deleteById(id );
		System.err.println("rows=" + rows);
	}
}








