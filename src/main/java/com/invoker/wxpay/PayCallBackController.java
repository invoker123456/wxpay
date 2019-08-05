package com.invoker.wxpay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;

/**
 * Created by invoker on 2019-07-31
 * Description: 回调接口
 */
@Api(tags = "第三方支付回调接口", description = "微信调用")
@RestController
@RequestMapping("/api/payment")
public class PayCallBackController {
    @Autowired
    private WeChatPayService wxPayService;

    @RequestMapping(value = "/wxpay/notify", method = {RequestMethod.POST, RequestMethod.GET})
    public String wxNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            return wxPayService.callBack(request, response);
        } catch (Exception e) {
            response.setHeader("Content-type", "application/xml");
            return "<xml>\n" +
                    "  <return_code><![CDATA[FAIL]]></return_code>\n" +
                    "  <return_msg><![CDATA[]]></return_msg>\n" +
                    "</xml>";
        }
    }
}
