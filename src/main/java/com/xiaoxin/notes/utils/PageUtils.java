package com.xiaoxin.notes.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月4日 下午12:59:00
 */
@Data
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	//总记录数 list.size()
	private int totalCount;
	//每页记录数
	private int pageSize;
	//总页数 totalCount/pageSize
	private int totalPage;
	//当前页数
	private int pagenum;
	//列表数据 list
	private List<?> list;
	
	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param pagenum    当前页数
	 */
	public PageUtils(List<?> list, int totalCount, int pageSize, int pagenum) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.pagenum = pagenum;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
	}

	/**
	 * 分页
	 */
	public PageUtils(IPage<?> page) {
		this.list = page.getRecords();
		this.totalCount = (int)page.getTotal();
		this.pageSize = (int)page.getSize();
		this.pagenum = (int)page.getCurrent();
		this.totalPage = (int)page.getPages();
	}
	
}
