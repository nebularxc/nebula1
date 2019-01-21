package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.AddressMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.IDistrictService;
import cn.tedu.store.service.exception.AccessDeniedException;
import cn.tedu.store.service.exception.AddressNotFoundException;
import cn.tedu.store.service.exception.DeleteException;
import cn.tedu.store.service.exception.InsertException;
import cn.tedu.store.service.exception.UpdateException;

@Service
public class AddressServiceImpl 
	implements IAddressService {
	
	@Autowired
	private AddressMapper addressMapper;
	@Autowired
	private IDistrictService districtService;

	@Override
	public Address create(
		String username, Address address) throws InsertException {
		// 通过address.getUid()得到用户id，并以此查询该用户的收货地址数量
		Integer count = getCountByUid(
				address.getUid());
		// 判断数量是否为0
		// if (count == 0) {
		//	// 是：当前用户首次创建地址，则该地址默认：address.setIsDefault(1);
		//	address.setIsDefault(1);
		// } else {
		//	// 否：当前用户非首次创建地址，则该地址非默认：address.setIsDefault(0);
		//	address.setIsDefault(0);
		// }
		address.setIsDefault(count == 0 ? 1 : 0);

		// 处理district：根据省、市、区的代号获取District值
		String district = getDistrict(
				address.getProvince(), 
				address.getCity(), 
				address.getArea());
		address.setDistrict(district);

		// 封装日志
		Date now = new Date();
		address.setCreatedUser(username);
		address.setCreatedTime(now);
		address.setModifiedUser(username);
		address.setModifiedTime(now);

		// 执行创建新地址
		addnew(address);
		return address;
	}

	@Override
	@Transactional
	public void setDefault(Integer uid, Integer id) {
		// 根据id查询收货地址数据
		Address data = findById(id);
		
		// 判断数据是否为null
		if (data == null) {
			throw new AddressNotFoundException(
				"设置默认收货地址失败！尝试访问的收货地址数据不存在！");
		}
		
		// 判断查询到的数据中的uid与参数uid是否一致
		if (data.getUid() != uid) {
			throw new AccessDeniedException(
				"设置默认收货地址失败！访问数据权限验证不通过！");
		}
		
		// 将该用户的所有收货地址设置为非默认
		updateNonDefault(uid);
		// 将指定id的收货地址设置为默认
		updateDefault(id);
	}
	
	@Override
	public List<Address> getListByUid(Integer uid) {
		return findByUid(uid);
	}
	
	@Override
	@Transactional
	public void delete(Integer uid, Integer id) throws DeleteException {
		// 根据id查询收货地址数据：findById(id)
		Address data = findById(id);
		// 检查数据是否为null
		if (data == null) {
			// 是：抛出AddressNotFoundException
			throw new AddressNotFoundException("删除收货地址失败！尝试删除的数据不存在！");
		}
		
		// 检查数据归属是否有误
		if (data.getUid() != uid) {
			// 是：抛出AccessDeniedException
			throw new AccessDeniedException("删除收货地址失败！访问数据权限验证不通过！");
		}
		
		// 执行删除
		deleteById(id);
		
		// 检查还有没有收货地址数据：getCountByUid(uid)
		if (getCountByUid(uid) > 0) {
			// 是：判断刚才判断的是否是默认收货地址
			if (data.getIsDefault() == 1) {
				// -- 是：获取最后修改的收货地址：findLastModified(uid)
				Integer lastModifiedId
					= findLastModified(uid).getId();
				// -- 将最后修改的收货地址设置为默认收货地址
				setDefault(uid, lastModifiedId);
			}
		}
	}
	
	/**
	 * 增加新的收货地址数据
	 * @param address 收货地址数据
	 */
	private void addnew(Address address) {
		Integer rows
			= addressMapper.addnew(address);
		if (rows != 1) {
			throw new InsertException(
				"增加收货地址数据时出现未知错误！");
		}
	}
	
	/**
	 * 将某用户的收货地址全部设置为非默认
	 * @param uid 用户id
	 * @return 受影响的行数
	 */
	private void updateNonDefault(Integer uid) {
		Integer rows = addressMapper.updateNonDefault(uid);
		if (rows < 1) {
			throw new UpdateException(
				"修改默认收货地址时出现未知错误！");
		}
	}

	/**
	 * 将指定id的收货地址设置为默认
	 * @param id 收货地址数据id
	 * @return 受影响的行数
	 */
	private void updateDefault(Integer id) {
		Integer rows = addressMapper.updateDefault(id);
		if (rows != 1) {
			throw new UpdateException(
				"修改默认收货地址时出现未知错误！");
		}
	}


	/**
	 * 根据用户id获取该用户的收货地址数据的数量
	 * @param uid 用户id
	 * @return 该用户的收货地址数据的数量，如果没有数据，则返回0
	 */
	private Integer getCountByUid(Integer uid) {
		return addressMapper.getCountByUid(uid);
	}
	
	/**
	 * 获取某用户的收货地址列表
	 * @param uid 用户id
	 * @return 收货地址列表
	 */
	private List<Address> findByUid(Integer uid) {
		return addressMapper.findByUid(uid);
	}
	
	/**
	 * 根据id查询收货地址数据
	 * @param id 收货地址id
	 * @return 匹配的收货地址数据，如果没有匹配的数据，则返回null
	 */
	private Address findById(Integer id) {
		return addressMapper.findById(id);
	}
	
	/**
	 * 查询某用户最后修改的收货地址信息
	 * @param uid 用户的id
	 * @return 匹配的数据，如果没有匹配的数据，则返回null
	 */
	private Address findLastModified(Integer uid) {
		return addressMapper.findLastModified(uid);
	}
	
	/**
	 * 根据id删除收货地址数据
	 * @param id 收货地址数据的id
	 */
	private void deleteById(Integer id) {
		Integer rows = addressMapper.deleteById(id);
		if (rows != 1) {
			throw new DeleteException("删除收货地址时出现未知错误！");
		}
	}

	/**
	 * 根据省、市、区的代号获取名称
	 * @param province 省的代号
	 * @param city 市的代号
	 * @param area 区的代号
	 * @return 省市区的名称，例如：浙江省杭州市上城区
	 */
	private String getDistrict(
			String province, String city, String area) {
		String provinceName = null;
		String cityName = null;
		String areaName = null;
		
		District p = districtService.getByCode(province);
		District c = districtService.getByCode(city);
		District a = districtService.getByCode(area);
		
		if (p != null) {
			provinceName = p.getName();
		}
		
		if (c != null) {
			cityName = c.getName();
		}
		
		if (a != null) {
			areaName = a.getName();
		}
		
		return provinceName + ", " + cityName + ", " + areaName;
	}



}




