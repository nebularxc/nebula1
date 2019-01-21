package cn.tedu.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.District;
import cn.tedu.store.mapper.DistrictMapper;
import cn.tedu.store.service.IDistrictService;

@Service
public class DistrictServiceImpl implements IDistrictService {

	@Autowired
	private DistrictMapper districtMapper;

	@Override
	public List<District> getListByParent(String parent) {
		return findByParent(parent);
	}

	@Override
	public District getByCode(String code) {
		return findByCode(code);
	}

	/**
	 * 根据父级代号获取子级的省/市/区的列表
	 * 
	 * @param parent
	 *            父级代号，如果需要获取省的列表，则父级代号为86
	 * @return 省/市/区的列表
	 */
	private List<District> findByParent(String parent) {
		return districtMapper.findByParent(parent);
	}

	/**
	 * 根据代号获取省/市/区的详情
	 * 
	 * @param code
	 *            省/市/区的代号
	 * @return 省/市/区的详情，如果没有匹配的数据，则返回null
	 */
	private District findByCode(String code) {
		return districtMapper.findByCode(code);
	}
}
