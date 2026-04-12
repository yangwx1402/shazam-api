package com.shazam.wechat.sdk.http;

import com.shazam.wechat.sdk.exception.WechatException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * HTTP 客户端封装（基于 HttpURLConnection）
 */
public class HttpClient {

    private final int connectTimeout;
    private final int readTimeout;

    public HttpClient(int connectTimeout, int readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    /**
     * 发送 GET 请求
     *
     * @param urlString    请求 URL
     * @param responseType 响应类型
     * @param <T>          响应类型
     * @return 响应对象
     * @throws WechatException 请求异常
     */
    public <T> T get(String urlString, Class<T> responseType) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            String responseBody = readResponse(connection);

            if (responseCode == 200) {
                return JsonUtil.fromJson(responseBody, responseType);
            } else {
                throw new WechatException("HTTP GET failed: " + responseCode);
            }
        } catch (IOException e) {
            throw new WechatException("HTTP GET request failed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * 发送 POST 请求
     *
     * @param urlString    请求 URL
     * @param body         请求体对象（将被序列化为 JSON）
     * @param responseType 响应类型
     * @param <T>          响应类型
     * @return 响应对象
     * @throws WechatException 请求异常
     */
    public <T> T post(String urlString, Object body, Class<T> responseType) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonBody = JsonUtil.toJson(body);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            String responseBody = readResponse(connection);

            if (responseCode == 200) {
                return JsonUtil.fromJson(responseBody, responseType);
            } else {
                throw new WechatException("HTTP POST failed: " + responseCode);
            }
        } catch (IOException e) {
            throw new WechatException("HTTP POST request failed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        byte[] buffer = new byte[4096];
        java.io.InputStream inputStream;
        
        if (connection.getResponseCode() >= 400) {
            inputStream = connection.getErrorStream();
        } else {
            inputStream = connection.getInputStream();
        }
        
        if (inputStream == null) {
            return "";
        }
        
        StringBuilder response = new StringBuilder();
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            response.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
        }
        return response.toString();
    }
}
