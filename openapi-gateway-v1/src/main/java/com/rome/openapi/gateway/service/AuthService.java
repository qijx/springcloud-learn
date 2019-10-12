/*
 * Filename AuthService.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.gateway.service;

import com.rome.openapi.gateway.dto.SignatureVO;

/**
 * 验签服务
 *
 * @author kongweixiang
 * @since 1.0.0_2018/8/1
 */
public interface AuthService {

    /**
     * url白名单验证
     *
     * @param uri 访问uri
     * @return true：在白名单内，false：不再白名单内
     */
    boolean validURLWhiteList(String uri);


    /**
     * 验签服务
     *
     * @param signatureVO 验前数据
     * @return
     */
    void validSign(SignatureVO signatureVO);

    /**
     * 校验时间戳有效性
     *
     * @param timeStamp
     * @return
     */
    void authTimeStamp(String timeStamp);

}
