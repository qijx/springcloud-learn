package com.rome.openapi.backend.mapper;

import com.rome.openapi.backend.vo.ApiEntry;
import com.rome.openapi.backend.vo.ApiGroup;
import com.rome.openapi.backend.vo.OpenApi;
import com.rome.openapi.backend.vo.OpenApiGroupEntry;

import java.util.List;
import java.util.Map;

public interface OpenApiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OpenApi record);

    int insertSelective(OpenApi record);

    OpenApi selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OpenApi record);

    int updateByPrimaryKey(OpenApi record);

    List<ApiEntry> selectWithApp();

    int insertWithGroup(OpenApi record);

	List<OpenApiGroupEntry> list(Map<String, Object> map);

    int batchRemove(Integer[] ids);

    List<ApiGroup> groupList();

    List<OpenApi> selectByGroupId(Integer groupId);

    void removeByGroupId(Integer groupId);
}