/*
 * Filename PageUtils.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.util.page;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 适配Bootstrap Table
 * @author kongweixiang
 * @since 1.0.0_2018/8/6
 */
public class PageUtils  implements Serializable {
	private static final long serialVersionUID = 1L;
	private long total;
	private List<?> rows;

	public PageUtils() {

	}

	public PageUtils(PageInfo<?> pageInfo) {
		this.rows = pageInfo.getList();
		this.total = pageInfo.getTotal();
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}
