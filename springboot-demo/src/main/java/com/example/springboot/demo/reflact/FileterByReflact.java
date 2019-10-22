package com.example.springboot.demo.reflact;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 根据
 * @Author: qijx
 * @Date: 2019-10-14 14:19
 */
@Configuration
@ConfigurationProperties(prefix = "store.blacklist")
@Data
@Slf4j
public class FileterByReflact<T>{


    /**
     * 根据那些字段来判断是否获取值
     */
    private final List ALLOW_FIELD=Arrays.asList("storeCode","channelCode");

    /**
     * 移除掉的集合元素
     */
    private List<T> blackList=new ArrayList();

    /**
     *  门店编码
     */
    private List<String> storeList;


    /**
     * @return 移除黑名单中的门店，并返被移除的门店数据
     */
    public List<T> filterChannelList(List<T> channelDTOS) {
        log.info("黑名单中门店数据为:{}",storeList);
        if (CollectionUtils.isEmpty(storeList)){
            return blackList;
        }
        Iterator<T> iterator = channelDTOS.iterator();
        while (iterator.hasNext()){
            //得到当前迭代的元素
            T next = iterator.next();
            //得到当前迭代元素的class类
            Class<?> tClass = next.getClass();
            Field[] fields = tClass.getDeclaredFields();
            for (int i=0; i<fields.length;i++){
                //判断字段是否string类型
                String type = fields[i].getGenericType().toString();
                if(type.equals("class java.lang.String")){
                    String name = fields[i].getName();
                    if (ALLOW_FIELD.contains(name)){
                        //拼接get方法
                        name = "get"+name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
                        Method method = null;
                        //属性为private方法时需要设为true才能获得值
                        fields[i].setAccessible(true);
                        try {
                            //根据方法名获得方法
                            method = tClass.getMethod(name);
                            //执行方法
                            String invoke = (String) method.invoke(next);
                            if (storeList.contains(invoke)){
                                blackList.add(next);
                                iterator.remove();
                                break;
                            }
                        } catch (Exception e) {
                            log.error("过滤黑门店数据出错");
                            e.printStackTrace();
                        }
                    }
                }

            }

        }
        return blackList;
    }


//    public static void main(String[] args) {
//        List<StoreRenovateDTO> list = new ArrayList<>();
//        StoreRenovateDTO channelDTO = new StoreRenovateDTO();
//        channelDTO.setStoreCode("1Z33");
//        StoreRenovateDTO channelDTO2 = new StoreRenovateDTO();
//        channelDTO2.setStoreCode("1ssdf");
//        list.add(channelDTO);
//        list.add(channelDTO2);
//        StoreBlackConfiguration<StoreRenovateDTO> configuration = new StoreBlackConfiguration<>();
//        List<StoreRenovateDTO> list1 = configuration.filterChannelList(list);
//        System.out.println(list);
//
//    }




}
