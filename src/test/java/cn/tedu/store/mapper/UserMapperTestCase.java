package cn.tedu.store.mapper;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTestCase {

	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void addnew() {
		Date now = new Date();
		User user = new User();
		user.setUsername("root");
		user.setPassword("1234");
		user.setGender(1);
		user.setPhone("13800138001");
		user.setEmail("root@tedu.cn");
		user.setSalt("Hello,MD5!");
		user.setIsDelete(0);
		user.setCreatedUser("Admin");
		user.setModifiedUser("Admin");
		user.setCreatedTime(now);
		user.setModifiedTime(now);
		Integer rows = userMapper.addnew(user);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updatePassword() {
		Integer uid = 3;
		String password = "1234";
		String modifiedUser = "ChengHeng";
		Date modifiedTime = new Date();
		Integer rows = userMapper.updatePassword(uid, password, modifiedUser, modifiedTime);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateAvatar() {
		Integer uid = 3;
		String avatar = "upload/201901071102.jpg";
		String modifiedUser = "ChengHeng";
		Date modifiedTime = new Date();
		Integer rows = userMapper.updateAvatar(uid, avatar, modifiedUser, modifiedTime);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateInfo() {
		User user = new User();
		user.setId(2);
		user.setGender(1);
		user.setPhone("13100131001");
		user.setEmail("liucs@tedu.cn");
		user.setModifiedUser("ChengHeng");
		user.setModifiedTime(new Date());
		Integer rows = userMapper.updateInfo(user);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void findByUsername() {
		String username = "root";
		User user = userMapper.findByUsername(username);
		System.err.println(user);
	}
	
	@Test
	public void findById() {
		Integer id = 3;
		User user = userMapper.findById(id);
		System.err.println(user);
	}
	
}








