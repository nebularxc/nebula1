package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.Address;

/**
 * 收货地址的持久层接口
 */
public interface AddressMapper {
	
	/**
	 * 增加新的收货地址数据
	 * @param address 收货地址数据
	 * @return 受影响的行数
	 */
	Integer addnew(Address address);
	
	/**
	 * 将某用户的收货地址全部设置为非默认
	 * @param uid 用户id
	 * @return 受影响的行数
	 */
	Integer updateNonDefault(Integer uid);

	/**
	 * 将指定id的收货地址设置为默认
	 * @param id 收货地址数据id
	 * @return 受影响的行数
	 */
	Integer updateDefault(Integer id);

	/**
	 * 根据用户id获取该用户的收货地址数据的数量
	 * @param uid 用户id
	 * @return 该用户的收货地址数据的数量，如果没有数据，则返回0
	 */
	Integer getCountByUid(Integer uid);
	
	/**
	 * 获取某用户的收货地址列表
	 * @param uid 用户id
	 * @return 收货地址列表
	 */
	List<Address> findByUid(Integer uid);

	/**
	 * 根据id查询收货地址数据
	 * @param id 收货地址id
	 * @return 匹配的收货地址数据，如果没有匹配的数据，则返回null
	 */
	Address findById(Integer id);
	
	/**
	 * 查询某用户最后修改的收货地址信息
	 * @param uid 用户的id
	 * @return 匹配的数据，如果没有匹配的数据，则返回null
	 */
	Address findLastModified(Integer uid);
	
	/**
	 * 根据id删除收货地址数据
	 * @param id 收货地址数据的id
	 * @return 受影响的行数
	 */
	Integer deleteById(Integer id);

}





