package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.service.exception.UserNotFoundException;

/**
 * 处理用户数据的业务层实现类
 */
@Service
public class UserServiceImpl 
	implements IUserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User reg(User user) throws DuplicateKeyException, InsertException {
		// 根据尝试注册的用户名查询用户数据
		User data = findByUsername(
				user.getUsername());
		// 判断查询到的数据是否为null
		if (data == null) {
			// 是：用户名不存在，允许注册，则
			// 【补充非用户提交的数据】
			// 是否已经删除：否
			user.setIsDelete(0); 
			// 4项日志
			Date now = new Date();
			user.setCreatedUser(user.getUsername());
			user.setCreatedTime(now);
			user.setModifiedUser(user.getUsername());
			user.setModifiedTime(now);
			// -----------------------
			// 【处理密码加密】
			// 加密-1：获取随机的UUID作为盐值
			String salt = UUID.randomUUID().toString().toUpperCase();
			// 加密-2：获取用户提交的原始密码
			String srcPassword = user.getPassword();
			// 加密-3：基于原始密码和盐值执行加密，获取通过MD5加密的密码
			String md5Password = getMd5Password(srcPassword, salt);
			// 加密-4：将加密后的密码封装在user对象中
			user.setPassword(md5Password);
			// 加密-5：将盐值封装在user对象中
			user.setSalt(salt);
			// 执行注册
			addnew(user);
			// 返回注册的用户对象
			return user;
		} else {
			// 否：用户名已被占用，抛出DuplicateKeyException异常
			throw new DuplicateKeyException(
				"注册失败！尝试注册的用户名(" + user.getUsername() + ")已经被占用！");
		}
	}
	
	@Override
	public User login(String username, String password) throws UserNotFoundException, PasswordNotMatchException {
		// 根据参数username查询用户数据
		User data = findByUsername(username);
		// 判断用户数据是否为null
		if (data == null) {
			// 是：为null，用户名不存在，则抛出UserNotFoundException
			throw new UserNotFoundException(
				"登录失败！您尝试登录的用户名不存在！");
		}
		
		// 否：非null，根据用户名找到了数据，取出盐值
		String salt = data.getSalt();
		// 	对参数password执行加密
		String md5Password 
			= getMd5Password(password, salt);
		
		// 	判断密码是否匹配
		if (data.getPassword().equals(md5Password)) {
			//是：匹配，密码正确，则判断是否被删除
			if (data.getIsDelete() == 1) {
				// 是：已被删除，则抛出UserNotFoundException或自定义“用户被删除异常”
				throw new UserNotFoundException(
					"登录失败！您尝试登录的用户数据已经被删除！");
			}
			
			// 否：没被删除，则登录成功，将第1步查询的用户数据中的盐值和密码设置为null
			data.setPassword(null);
			data.setSalt(null);
			// 返回用户数据
			return data;
		}else {
			// 否：不匹配，密码错误，则抛出PasswordNotMatchException
			throw new PasswordNotMatchException(
				"登录失败！密码错误！");
		}
	}
	
	@Override
	public void changePassword(Integer uid, String oldPassword, String newPassword)
			throws UserNotFoundException, PasswordNotMatchException, UpdateException {
		// 根据uid查询用户数据
		User data = findById(uid);
		// 判断查询结果是否为null
		if (data == null) {
			// 是：抛出异常：UserNotFoundException
			throw new UserNotFoundException(
				"修改密码失败！您尝试访问的用户数据不存在！");
		}

		// 判断查询结果中的isDelete是否为1
		if (data.getIsDelete() == 1) {
			// 是：抛出异常：UserNotFoundException
			throw new UserNotFoundException(
				"修改密码失败！您尝试访问的用户数据已经被删除！");
		}

		// 取出查询结果中的盐值
		String salt = data.getSalt();
		// 对参数oldPassword执行MD5加密
		String oldMd5Password 
			= getMd5Password(oldPassword, salt);
		// 将加密结果与查询结果中的password对比是否匹配
		if (data.getPassword().equals(oldMd5Password)) {
			// 是：原密码正确，对参数newPassword执行MD5加密
			String newMd5Password
				= getMd5Password(newPassword, salt);
			// 获取当前时间
			Date now = new Date();
			// 更新密码
			updatePassword(
				uid, newMd5Password, 
				data.getUsername(), now);
		} else {
			// 否：原密码错误，抛出异常：PasswordNotMatchException
			throw new PasswordNotMatchException(
				"修改密码失败！原密码错误！");
		}
	}
	
	@Override
	public void changeAvatar(Integer uid, String avatar) throws UserNotFoundException, UpdateException {
		// 根据user.getId()查询用户数据
		User data = findById(uid);
		// 判断数据是否为null
		if (data == null) {
			// 是：抛出：UserNotFoundException
			throw new UserNotFoundException(
				"修改头像失败！您尝试访问的用户数据不存在！");
		}

		// 判断is_delete是否为1
		if (data.getIsDelete() == 1) {
			// 是：抛出：UserNotFoundException
			throw new UserNotFoundException(
				"修改头像失败！您尝试访问的用户数据已经被删除！");
		}

		// 执行更新头像
		String modifiedUser = data.getUsername();
		Date modifiedTime = new Date();
		updateAvatar(uid, avatar, modifiedUser, modifiedTime);
	}
	
	@Override
	public void changeInfo(User user) 
			throws UserNotFoundException, UpdateException {
		// 根据user.getId()查询用户数据
		User data = findById(user.getId());
		// 判断数据是否为null
		if (data == null) {
			// 是：抛出：UserNotFoundException
			throw new UserNotFoundException(
				"修改个人资料失败！您尝试访问的用户数据不存在！");
		}

		// 判断is_delete是否为1
		if (data.getIsDelete() == 1) {
			// 是：抛出：UserNotFoundException
			throw new UserNotFoundException(
				"修改个人资料失败！您尝试访问的用户数据已经被删除！");
		}

		// 向参数对象中封装：
		// - modified_user > data.getUsername()
		// - modified_time > new Date()
		user.setModifiedUser(data.getUsername());
		user.setModifiedTime(new Date());
		
		// 执行修改：gender,phone,email,modified_user,modified_time
		updateInfo(user);
	}
	
	@Override
	public User getById(Integer id) {
		User data = findById(id);
		data.setPassword(null);
		data.setSalt(null);
		data.setIsDelete(null);
		return data;
	}
	
	/**
	 * 获取根据MD5加密的密码
	 * @param srcPassword 原密码
	 * @param salt 盐值
	 * @return 加密后的密码
	 */
	private String getMd5Password(
			String srcPassword, String salt) {
		// 【注意】以下加密规则是自由设计的
		// ----------------------------
		// 盐值 拼接 原密码 拼接 盐值
		String str = salt + srcPassword + salt;
		// 循环执行10次摘要运算
		for (int i = 0; i < 10; i++) {
			str = DigestUtils
				.md5DigestAsHex(str.getBytes())
					.toUpperCase();
		}
		// 返回摘要结果
		return str;
	}
	
	/**
	 * 插入用户数据
	 * @param user 用户数据
	 * @throws InsertException
	 */
	private void addnew(User user) {
		Integer rows = userMapper.addnew(user);
		if (rows != 1) {
			throw new InsertException(
				"增加用户数据时出现未知错误！");
		}
	}
	
	/**
	 * 更新密码
	 * @param uid 用户的id
	 * @param password 新密码
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updatePassword(
			Integer uid, String password,
			String modifiedUser,
			Date modifiedTime) {
		Integer rows = userMapper.updatePassword(
				uid, password, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException(
				"更新密码时出现未知错误！");
		}
	}
	
	/**
	 * 更新头像
	 * @param uid 用户的id
	 * @param avatar 新头像路径
	 * @param modifiedUser 修改执行人
	 * @param modifiedTime 修改时间
	 */
	private void updateAvatar(
			Integer uid, String avatar,
			String modifiedUser,
			Date modifiedTime) {
		Integer rows = userMapper.updateAvatar(
				uid, avatar, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException(
					"更新头像时出现未知错误！");
		}
	}
	
	/**
	 * 更新用户的个人资料
	 * @param user 用户数据
	 */
	private void updateInfo(User user) {
		// 执行更新，获取返回值
		Integer rows = userMapper.updateInfo(user);
		// 判断返回值，出错时抛出“更新时的未知错误”
		if (rows != 1) {
			throw new UpdateException(
				"更新用户数据时出现未知错误！");
		}
	}
	
	/**
	 * 根据用户名查询用户数据
	 * @param username 用户名
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	private User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}
	
	/**
	 * 根据用户id查询用户数据
	 * @param id 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	private User findById(Integer id) {
		return userMapper.findById(id);
	}




	
	
}





