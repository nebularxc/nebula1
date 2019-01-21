package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Address;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.util.ResponseResult;

/**
 * 处理收货地址相关请求的控制器类
 */
@RestController
@RequestMapping("/address")
public class AddressController 
	extends BaseController {

	@Autowired
	private IAddressService addressService;
	
	@PostMapping("/create")
	public ResponseResult<Void> handleCreate(
		Address address, HttpSession session) {
		// 根据session获取username
		String username
			= session.getAttribute("username").toString();

		// 根据session获取uid
		Integer uid = getUidFromSession(session);
		// 将uid封装到address中
		address.setUid(uid);

		// 调用业务层对象执行创建收货地址
		addressService.create(username, address);
		
		// 返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@RequestMapping("/list")
	public ResponseResult<List<Address>> 
		getListByUid(HttpSession session) {
		// 获取uid
		Integer uid = getUidFromSession(session);
		// 查询数据
		List<Address> list
			= addressService.getListByUid(uid);
		// 返回
		return new ResponseResult<List<Address>>(
				SUCCESS, list);
	}
	
	@GetMapping("/default/{id}")
	public ResponseResult<Void> setDefault(
			HttpSession session,
			@PathVariable("id") Integer id) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 调用业务层方法执行设置
		addressService.setDefault(uid, id);
		// 返回
		return new ResponseResult<>(SUCCESS);
	}
	
	@GetMapping("/delete/{id}")
	public ResponseResult<Void> deleteById(
			HttpSession session,
			@PathVariable("id") Integer id) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 调用业务层方法执行设置
		addressService.delete(uid, id);
		// 返回
		return new ResponseResult<>(SUCCESS);
	}

}







