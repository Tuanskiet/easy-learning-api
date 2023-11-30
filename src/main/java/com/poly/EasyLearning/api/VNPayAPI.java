package com.poly.EasyLearning.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import com.poly.EasyLearning.VNPay.Config;
import com.poly.EasyLearning.entity.Payment;
import com.poly.EasyLearning.entity.UserInfo;
import com.poly.EasyLearning.service.UserInfoService;
import com.poly.EasyLearning.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1")
public class VNPayAPI {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("payment-callback")
    public String paymentCallback(@RequestParam("account_id") Integer account_id,
            @RequestParam("vnp_PayDate") String vnp_PayDate, @RequestParam("vnp_TransactionNo") String vnp_TransactionNo,
            @RequestParam("vnp_Amount") String vnp_Amount,
            @RequestParam Map<String, String> queryParams,
            HttpServletResponse response) throws IOException {
        String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
        if (account_id != null && !account_id.equals("")) {
            System.out.println("Account id: " + account_id);
            if ("00".equals(vnp_ResponseCode)) {
                UserInfo userInfo = userInfoService.findById(account_id);
                userInfo.setSubscription("PREMIUM");
                userInfoService.insert(userInfo);
                // add vào payment
                Payment payment = new Payment();
                payment.setTransactionNo(vnp_TransactionNo);
                payment.setPaymentDate(new Date(System.currentTimeMillis()));
                payment.setTotalPrice(Float.parseFloat(vnp_Amount) / 100);
                payment.setAccountid(account_id);
                paymentService.insert(payment);
                System.out.println("Payment: " + payment);
                response.sendRedirect("http://localhost:4200/payment-success");
            } else {
                response.sendRedirect("http://localhost:4200/payment-fail");
            }
        } else {
            response.sendRedirect("http://localhost:4200/payment-error");
        }
        return "redirect:/";
    }

    @PostMapping("pay")
    public String getPay(@RequestParam("price") long price, @RequestParam("id") Integer account_id)
            throws UnsupportedEncodingException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = price * 100;
        String bankCode = "NCB";

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl + "?account_id=" + account_id);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }

}