package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.controller.exception.FileEmptyException;
import cn.tedu.store.controller.exception.FileSizeOutOfLimitException;
import cn.tedu.store.controller.exception.FileTypeNotSupportException;
import cn.tedu.store.controller.exception.FileUploadException;
import cn.tedu.store.controller.exception.RequestException;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.AddressNotFoundException;
import cn.tedu.store.service.exception.DeleteException;
import cn.tedu.store.service.exception.DuplicateKeyException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.PasswordNotMatchException;
import cn.tedu.store.service.exception.ServiceException;
import cn.tedu.store.service.exception.UpdateException;
import cn.tedu.store.service.exception.UserNotFoundException;
import cn.tedu.store.util.ResponseResult;

/**
 * 当前项目中所有控制器类的基类
 */
public abstract class BaseController {
	
	/**
	 * 正确响应时的代号
	 */
	public static final Integer SUCCESS = 200;

	@ExceptionHandler({ServiceException.class, 
		RequestException.class})
	@ResponseBody
	public ResponseResult<Void> handleException(
			Exception e) {
		Integer state = null;
		
		if (e instanceof DuplicateKeyException) {
			// 400-违反了Unique约束的异常
			state = 400;
		} else if (e instanceof UserNotFoundException) {
			// 401-用户数据不存在
			state = 401;
		} else if (e instanceof PasswordNotMatchException) {
			// 402-密码错误
			state = 402;
		} else if (e instanceof AddressNotFoundException) {
			// 403-收货地址数据不存在
			state = 403;
		} else if (e instanceof AccessDeniedException) {
			// 404-访问被拒绝的异常
			state = 404;
		} else if (e instanceof InsertException) {
			// 500-插入数据异常
			state = 500;
		} else if (e instanceof UpdateException) {
			// 501-更新数据异常
			state = 501;
		} else if (e instanceof DeleteException) {
			// 502-删除数据异常
			state = 502;
		} else if (e instanceof FileEmptyException) {
			// 600-上传的文件为空的异常
			state = 600;
		} else if (e instanceof FileSizeOutOfLimitException) {
			// 601-上传的文件超出了限制的异常
			state = 601;
		} else if (e instanceof FileTypeNotSupportException) {
			// 602-上传的文件类型不支持的异常
			state = 602;
		} else if (e instanceof FileUploadException) {
			// 610-文件上传异常
			state = 610;
		}
		
		return new ResponseResult<>(state, e);
	}
	
	/**
	 * 从Session中获取uid
	 * @param session HttpSession对象
	 * @return 当前登录的用户的id
	 */
	protected Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(
				session.getAttribute("uid").toString());
	}
	
}




