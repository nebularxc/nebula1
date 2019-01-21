package cn.tedu.store.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Goods;

/**
 * 商口数据的持久层接口
 */
public interface GoodsMapper {
	
	/**
	 * 根据商品分类，查询商品列表
	 * @param categoryId 商品分类的id
	 * @param offset 偏移量
	 * @param count 获取的数据的最大数量
	 * @return 商品列表
	 */
	List<Goods> findByCategory(
		@Param("categoryId") Long categoryId,
		@Param("offset") Integer offset,
		@Param("count") Integer count);

	/**
	 * 根据id查询商品详情
	 * @param id 商品的id
	 * @return 商品详情，如果没有匹配的数据，则返回null
	 */
	Goods findById(Long id);
	
	/**
	 * 根据优先级获取商品数据的列表
	 * @param count 获取的商品的数量
	 * @return 优先级最高的几个商品数据的列表
	 */
	List<Goods> findByPriority(Integer count);
	
}





