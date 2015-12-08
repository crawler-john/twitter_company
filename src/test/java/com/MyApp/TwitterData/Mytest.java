package com.MyApp.TwitterData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;

import com.MyApp.TwitterData.Util.HttpClientManager;


public class Mytest {
//
//  public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException {
//    //创建get请求
//    String url="https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=ider";
//    HttpUriRequest search = RequestBuilder.get().setUri(new URI(url))
//        .addHeader("Authorization", "OAuth oauth_consumer_key=\"Z2I5hbcmGlm7pI8yGQy2eYuVA\", oauth_nonce=\"69cf4d0bc8af61fb08a545811d032ef1\", oauth_signature=\"YIgzuVRe045bT64RK9mogqujXbs%3D\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1436246222\", oauth_token=\"3269193235-jiHbDMonrWlPkrU2c5RmmLOoeMGPsUIH0lPsRy6\", oauth_version=\"1.0\"")
//        .build();
//    
//    //创建代理地址
//    HttpHost proxy = new HttpHost("127.0.0.1", 8580);
//    DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
//
//
//    
//    // 创建client管理器
//    HttpClientManager hcm = HttpClientManager.getInstance();
//    CloseableHttpClient httpclient = HttpClients.custom()
//        .setConnectionManager(hcm.getManager())
//        .setRoutePlanner(routePlanner)
//        .build();
//    /*
//     * 提交http请求
//     */
//    CloseableHttpResponse response = httpclient.execute(search);
//    HttpEntity entity;
//    try {
//      entity = response.getEntity();
//      if (entity != null) {
//        entity = new BufferedHttpEntity(entity);
//      } else {
//        throw new RuntimeException(url + "没有取回 entity");
//      }
//    } finally {
//      response.close();
//    }
//    System.out.println(EntityUtils.toString(entity));
//
//  }

}
