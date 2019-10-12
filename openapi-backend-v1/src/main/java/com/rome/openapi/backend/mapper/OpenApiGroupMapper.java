package com.rome.openapi.backend.mapper;

import com.rome.openapi.backend.vo.OpenApiGroup;

import java.util.List;
import java.util.Map;

public interface OpenApiGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OpenApiGroup record);

    int insertSelective(OpenApiGroup record);

    OpenApiGroup selectByPrimaryKey(Integer id);

    OpenApiGroup selectByService(String service);

    int updateByPrimaryKeySelective(OpenApiGroup record);

    int updateByPrimaryKey(OpenApiGroup record);

    int remove(Integer id);

    int batchRemove(Integer[] ids);

    List<OpenApiGroup> list(Map<String,Object> map);

    List<OpenApiGroup> listAll();
}