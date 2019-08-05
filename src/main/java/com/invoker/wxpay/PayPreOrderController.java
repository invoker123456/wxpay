package com.invoker.wxpay;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import io.swagger.annotations.Api;

/**
 * Created by invoker on 2019-07-31
 * Description: 下单
 */
@Api(tags = "第三方支付下单接口", description = "微信调用")
@RestController
@RequestMapping("/api/payment")
public class PayPreOrderController {
    @Autowired
    private WeChatPayService wxPayService;

    @RequestMapping(value = "/wxpay/preorder", method = {RequestMethod.POST, RequestMethod.GET})
    public String wxPreOrder(String orderId, String price, String body, String ipAddress) {
        try {
//            return wxPayService.createOrder(orderId, BigDecimal.valueOf(1), body, ipAddress).toString();

            String result_map = wxPayService.createOrder(orderId, BigDecimal.valueOf(Long.parseLong(price)), body, ipAddress);

            String result = null;
            if (result_map.length() != 0) {
                JsonArray jsonArray = new JsonArray();
                JsonObject jsonObj = new JsonObject();
                JsonArray jsonArrayMsg = new JsonArray();
                jsonObj.addProperty("code", "1");
                jsonObj.addProperty("msg", "success");
                jsonArrayMsg.add(result_map);
                jsonObj.add("data", jsonArrayMsg);
                jsonArray.add(jsonObj);
                result = jsonObj.toString().replace("\\\"", "\"")
                        .replace("[\"", "[")
                        .replace("\"]", "]");
            }
            return result;


        } catch (Exception e) {
            return "<xml>\n" +
                    "  <return_code><![CDATA[FAIL]]></return_code>\n" +
                    "  <return_msg><![CDATA[]]></return_msg>\n" +
                    "</xml>";
        }
    }
}
