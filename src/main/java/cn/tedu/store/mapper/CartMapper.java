package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

/**
 * 购物车数据的持久层接口
 */
public interface CartMapper {

	/**
	 * 新增购物车数据
	 * @param cart 购物车数据
	 * @return 受影响的行数
	 */
	Integer addnew(Cart cart);

	/**
	 * 更新购物车中商品的数量
	 * @param id 购物车数据的id
	 * @param count 新的数量
	 * @return 受影响的行数
	 */
	Integer updateCount(
		@Param("id") Integer id, 
		@Param("count") Integer count);

	/**
	 * 根据用户id和商品id查询购物车数据
	 * @param uid 用户id
	 * @param goodsId 商品id
	 * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
	 */
	Cart findByUidAndGid(
		@Param("uid") Integer uid, 
		@Param("goodsId") Long goodsId);
	
	/**
	 * 根据id获取购物车数据
	 * @param id 购物车数据的id
	 * @return 匹配的购物车数据，如果没有匹配的数据，则返回null
	 */
	Cart findById(Integer id);
	
	/**
	 * 根据用户id查询该用户的购物车数据列表
	 * @param uid 用户尖
	 * @return 该用户的购物车数据列表
	 */
	List<CartVO> findByUid(Integer uid);
	
}
