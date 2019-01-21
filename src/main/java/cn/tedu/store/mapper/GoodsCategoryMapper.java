package cn.tedu.store.mapper;

import java.util.List;

import cn.tedu.store.entity.GoodsCategory;

/**
 * 商品分类数据的持久层接口
 */
public interface GoodsCategoryMapper {

	/**
	 * 根据父级id获取子级的商品分类的数据的列表
	 * @param parentId 父级商品分类的id
	 * @return 子级的商品分类的数据的列表
	 */
	List<GoodsCategory> findByParent(
			Long parentId);
	
}





