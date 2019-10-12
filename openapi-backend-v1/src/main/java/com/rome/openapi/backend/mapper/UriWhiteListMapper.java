package com.rome.openapi.backend.mapper;

import com.rome.openapi.backend.vo.UriWhiteList;

import java.util.List;

public interface UriWhiteListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UriWhiteList record);

    int insertSelective(UriWhiteList record);

    UriWhiteList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UriWhiteList record);

    int updateByPrimaryKey(UriWhiteList record);

    List<UriWhiteList> selectAll();

    List<String> selectAllListOnlyUri();

	List<UriWhiteList> list();

    int batchRemove(Integer[] ids);
}