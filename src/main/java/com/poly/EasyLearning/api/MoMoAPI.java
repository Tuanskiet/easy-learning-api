package com.poly.EasyLearning.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.http.HttpRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.EasyLearning.config.MOMO.MoMoConfig;
import com.poly.EasyLearning.utils.MomoEncoderUtils;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/momo")

public class MoMoAPI {
    @GetMapping(value = "/test")
    public String test() {
        return "Test";
    }

    // tạo thanh toán, response trả về pay url
    @PostMapping(value = "/create-order")
    public Map<String, Object> createPayment(HttpServletRequest request, @RequestParam Long amount)
            throws InvalidKeyException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        Long order_id = (long) (Math.random() * 1000000000);
        JSONObject json = new JSONObject();
        String partnerCode = MoMoConfig.PARTNER_CODE;
        String accessKey = MoMoConfig.ACCESS_KEY;
        String secretKey = MoMoConfig.SECRET_KEY;
        String returnUrl = MoMoConfig.REDIRECT_URL;
        String notifyUrl = MoMoConfig.NOTIFY_URL;
        json.put("partnerCode", partnerCode);
        json.put("accessKey", accessKey);
        json.put("requestId", String.valueOf(System.currentTimeMillis()));
        json.put("amount", amount.toString());
        json.put("orderId", order_id.toString());
        json.put("orderInfo", "Thanh toan don hang " + order_id.toString());
        json.put("returnUrl", returnUrl);
        json.put("notifyUrl", notifyUrl);
        json.put("requestType", "captureMoMoWallet");

        String data = "partnerCode=" + partnerCode
                + "&accessKey=" + accessKey
                + "&requestId=" + json.get("requestId")
                + "&amount=" + amount.toString()
                + "&orderId=" + json.get("orderId")
                + "&orderInfo=" + json.get("orderInfo")
                + "&returnUrl=" + returnUrl
                + "&notifyUrl=" + notifyUrl
                + "&extraData=";

        String hashData = MomoEncoderUtils.signHmacSHA256(data, secretKey);
        json.put("signature", hashData);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(MoMoConfig.CREATE_ORDER_URL);
        StringEntity stringEntity = new StringEntity(json.toString());
        post.setHeader("content-type", "application/json");
        post.setEntity(stringEntity);

        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }
        JSONObject result = new JSONObject(resultJsonStr.toString());
        Map<String, Object> kq = new HashMap<>();
        if (result.get("errorCode").toString().equalsIgnoreCase("0")) {
            kq.put("requestType", result.get("requestType"));
            kq.put("orderId", result.get("orderId"));
            kq.put("payUrl", result.get("payUrl"));
            kq.put("signature", result.get("signature"));
            kq.put("requestId", result.get("requestId"));
            kq.put("errorCode", result.get("errorCode"));
            kq.put("message", result.get("message"));
            kq.put("localMessage", result.get("localMessage"));
        } else {
            kq.put("requestType", result.get("requestType"));
            kq.put("orderId", result.get("orderId"));
            kq.put("signature", result.get("signature"));
            kq.put("requestId", result.get("requestId"));
            kq.put("errorCode", result.get("errorCode"));
            kq.put("message", result.get("message"));
            kq.put("localMessage", result.get("localMessage"));
                    System.out.println("Thanh toán thành công");

        }
        return kq;
    }

    // truy vấn lại trạng thái thanh toán
    @PostMapping(value = "/transactionStatus")
    public Map<String, Object> transactionStatus(HttpServletRequest request, @RequestParam String requestId,
            @RequestParam String orderId)
            throws InvalidKeyException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        JSONObject json = new JSONObject();
        String partnerCode = MoMoConfig.PARTNER_CODE;
        String accessKey = MoMoConfig.ACCESS_KEY;
        String secretKey = MoMoConfig.SECRET_KEY;
        json.put("partnerCode", partnerCode);
        json.put("accessKey", accessKey);
        json.put("requestId", requestId);
        json.put("orderId", orderId);
        json.put("requestType", "transactionStatus");

        String data = "partnerCode=" + partnerCode + "&accessKey=" + accessKey + "&requestId=" + json.get("requestId")
                + "&orderId=" + json.get("orderId") + "&requestType=" + json.get("requestType");
        String hashData = MomoEncoderUtils.signHmacSHA256(data, secretKey);
        json.put("signature", hashData);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(MoMoConfig.CREATE_ORDER_URL);
        StringEntity stringEntity = new StringEntity(json.toString());
        post.setHeader("content-type", "application/json");
        post.setEntity(stringEntity);

        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }
        JSONObject result = new JSONObject(resultJsonStr.toString());
        Map<String, Object> kq = new HashMap<>();
        kq.put("requestId", result.get("requestId"));
        kq.put("orderId", result.get("orderId"));
        kq.put("extraData", result.get("extraData"));
        kq.put("amount", Long.parseLong(result.get("amount").toString()));
        kq.put("transId", result.get("transId"));
        kq.put("payType", result.get("payType"));
        kq.put("errorCode", result.get("errorCode"));
        kq.put("message", result.get("message"));
        kq.put("localMessage", result.get("localMessage"));
        kq.put("requestType", result.get("requestType"));
        kq.put("signature", result.get("signature"));
        return kq;
    }
    
}