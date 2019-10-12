/*
 * Filename Query.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author kongweixiang
 * @since 1.0.0_2018/8/6
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	private int pageNum = 1;
	private int pageSize = 10;

	public Query(Map<String,Object> params) {
		this.putAll(params);
		if (params.get("pageNum") != null) {
			this.pageNum = Integer.valueOf(params.get("pageNum").toString());
		}
		if (params.get("pageNumber") != null) {
			this.pageSize = Integer.valueOf((String) params.get("pageNumber"));
		}
		if (params.get("pageSize") != null) {
			this.pageSize = Integer.valueOf(params.get("pageSize").toString());
		}
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
