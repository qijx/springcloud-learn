package com.rome.openapi.gateway;

import com.rome.openapi.gateway.domain.entity.AppEntry;
import com.rome.openapi.gateway.dto.SignatureVO;
import com.rome.openapi.gateway.utils.signature.SignCalculaterV2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) {
        SignatureVO vo = new SignatureVO();
        vo.setRequestURI("/basedata-core/v1/payways");
        vo.setTimeStamp("1561946873950");
        vo.setClientId("5431EE90C0BE43CC8FA2C276CCD3F29B");
        vo.setRequestMethod("GET");
//		Map map = new HashMap();
//		map.put("ut", "6932f182917bb8973b39d54c74e9602441");
//		map.put("companyId", "0");
//		map.put("companyName", "浙江来伊份食品有限公司");
//		vo.setParameter(getParameterMap(map));
        AppEntry app = new AppEntry();
        app.setSecretKey("SECRETKEY-9EFC65938A9D4E16B1FF1D");
        String sign = SignCalculaterV2.calcuteSign(vo, app);
        System.out.println(sign);
        System.out.println(vo.getTimeStamp());
    }

}
