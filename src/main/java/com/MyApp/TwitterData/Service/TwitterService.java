package com.MyApp.TwitterData.Service;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.scribe.http.Request;
import org.scribe.http.Response;
import org.scribe.oauth.Scribe;
import org.scribe.oauth.Token;

import com.MyApp.TwitterData.Util.HttpClientManager;
/**
 * 实现twitter api的调用 twitter oauth 的认证
 * @author Love
 *
 */
public class TwitterService {
    private static final String PASSWORD = "password";
//    private static final String PROXY = "proxy";
    private static final String USER = "user";
    private Logger logger;
    /**oauth认证所需的密钥*/
    private String authenticity_token;
    private String oauth_token;
    /**认证所需的用户名和密码*/
    private String user;
    private String password;
    /**代理地址和代理proxy*/
    private String portadd;
//    private Proxy proxy;
    /**scribe lib*/
    private Scribe sb;
    private Token tk;
    private Properties tp;
    

    public TwitterService() {
        this.init();
        try {
            this.doOauth();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("oauth认证失败" + e);
            System.exit(0);
        }
    }

    /**
     * 进行Oauth 认证      
     * @throws URISyntaxException    url不正确
     * @throws IOException
     * @throws ParseException
     */
    private void doOauth() throws URISyntaxException, ParseException, IOException {
        /**httpclient初始化*/
        HttpClientManager hcm = HttpClientManager.getInstance();
        CloseableHttpClient httpclient=null;
        if(null!=portadd){
            String[] arrystr = portadd.split(":");
            HttpHost httphost = new HttpHost(arrystr[0], Integer.valueOf(arrystr[1]));
//            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(httphost);
            httpclient = HttpClients.custom()
                    .setConnectionManager(hcm.getManager())
                    .setSSLSocketFactory(hcm.getssl())
//                    .setRoutePlanner(routePlanner)
                    .build();
        }else{
            httpclient = HttpClients.custom()
                    .setConnectionManager(hcm.getManager())
                    .setSSLSocketFactory(hcm.getssl())
                    .build();
        }
        /**发出第一个认证请求，获得authenticity_token*/
        String url = "https://api.twitter.com/oauth/authorize?oauth_token=" + oauth_token;
        HttpUriRequest search = RequestBuilder.get().setUri(new URI(url)).build();
        CloseableHttpResponse response = httpclient.execute(search);
        HttpEntity entity;
        try {
            entity = response.getEntity();
            if (entity != null) {
                entity = new BufferedHttpEntity(entity);
            } else {
                throw new RuntimeException(url + "没有取回 entity");
            }
        } finally {
            response.close();
        }
        String body = EntityUtils.toString(entity);
        Pattern pattern = Pattern.compile("authenticity_token\" type=\"hidden\" value=\"(.*)\">");
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            authenticity_token = matcher.group(1);
        }
        /**发出第二个请求，使用资源文件中的用户信息完成oauth认证，获得Token*/
        url = "https://api.twitter.com/oauth/authorize";
        HttpEntity httpentity = new StringEntity("authenticity_token=" + authenticity_token + "&oauth_token="
                + oauth_token + "&redirect_after_login=https://api.twitter.com/oauth/authorize?oauth_token="
                + oauth_token + "&session[password]=" + password + "&session[username_or_email]=" + user);
        search = RequestBuilder.post()
                .setHeader("Content-Type", " application/x-www-form-urlencoded")
                .setHeader("Referer", "https://api.twitter.com/oauth/authorize?oauth_token=" + oauth_token)
                .setUri(new URI(url))
                .setEntity(httpentity)
                .build();
        response = httpclient.execute(search);
        try {
            entity = response.getEntity();
            if (entity != null) {
                entity = new BufferedHttpEntity(entity);
            } else {
                throw new RuntimeException(url + "没有取回 entity 2");
            }
        } finally {
            response.close();
        }
        body = EntityUtils.toString(entity);
        tk = new Token(this.getToken(body), tk.getSecret());
        tk = sb.getAccessToken(tk, this.getVerifier(body), null);
        System.out.println("oauth 认证成功");
    }
    
    
    /**
     * 
     * @param url
     * @return
     */
    public String getJsonByGet(String url){
        Request req = null;
        Response resp = null;
        String jsonstr = null;
        req = new Request(Request.Verb.GET, url);
        sb.signRequest(req, tk);        
        /* 判断有没有getbody 否则重试 */
        Boolean flag = true;
        while (flag) {
            try {
                resp = req.send(null);
                jsonstr = resp.getBody();
                flag = false;
            } catch (Exception e) {
                logger.error("没有get body" + e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    logger.error("sleep失败" + e);
                }
            }
        }        
        return jsonstr;
    }
    
    
    
    public String getSearchList(String company_name){
        Request req = null;
        Response resp = null;
        String jsonstr = null;
        req = new Request(Request.Verb.GET, "https://api.twitter.com/1.1/users/search.json?q="+company_name);

        sb.signRequest(req, tk);        
        /* 判断有没有getbody 否则重试 */
        Boolean flag = true;
       while (flag) {
            try {
                resp = req.send(null);
                jsonstr = resp.getBody();
                flag = false;
            } catch (Exception e) {
                logger.error("没有get body " + e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    logger.error("sleep失败" + e);
                }
            }
        }        
        return jsonstr;
    }
    
    
    
    
    /**
     * 根据 twitter_name & cursor 抓取 followers
     * @param Twitter_name
     * @param cursor
     * @return json
     */
    public String getFollowersList(String Twitter_name,Long cursor ){
        Request req = null;
        Response resp = null;
        String jsonstr = null;
        req = new Request(Request.Verb.GET, "https://api.twitter.com/1.1/followers/list.json?"
                + "screen_name=" + Twitter_name + "&count=200" + "&cursor=" + cursor);
        sb.signRequest(req, tk);        
        /* 判断有没有getbody 否则重试 */
        Boolean flag = true;
        while (flag) {
            try {
                resp = req.send(null);
                jsonstr = resp.getBody();
                flag = false;
            } catch (Exception e) {
                logger.error("没有get body" + e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    logger.error("sleep失败" + e);
                }
            }
        }        
        return jsonstr;
    }

    /**
     * 讀取 ratelimit
     * @return ratelimit_json
     */
    public String getRateLimitStatus(){
        Request req = null;
        Response resp = null;
        String jsonstr = null;
        req = new Request(Request.Verb.GET,
                "https://api.twitter.com/1.1/application/rate_limit_status.json");
        sb.signRequest(req, tk);        
        Boolean flag = true;
        while (flag) {
            try {
                resp = req.send(null);
                jsonstr = resp.getBody();
                flag = false;
            } catch (Exception e) {
                logger.error("没有get body" + e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    logger.error("sleep失败" + e);
                }
            }
        }        
        return jsonstr;
    }

    /**
     * 获取认证oauth_token
     * 
     * @param body
     * @return oauth_token
     */
    private String getToken(String body) {
        String Regex = "oauth_token=(.*)&";
        Pattern pattern = Pattern.compile(Regex);
        String token = null;

        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            token = matcher.group(1);
        }
        return token;
    }
    
    /**
     * 获取认证oauth_verifier
     * 
     * @param body
     * @return oauth_verifier
     */
    private String getVerifier(String body) {
        String Regex = "oauth_verifier=(.*)\">";
        Pattern pattern = Pattern.compile(Regex);
        String verifier = null;
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            verifier = matcher.group(1);
        }
        return verifier;
    }
    
    /**
     * 初始化方法 赋值
     */
    private void init() {
        logger = Logger.getLogger(TwitterService.class.getName());
        tp = new Properties();
        try {
            tp.load(TwitterService.class.getResourceAsStream("/twitter.properties"));
            user = tp.getProperty(USER);
            password = tp.getProperty(PASSWORD);
        } catch (IOException e) {
            logger.error("配置文件读取错误" + e);
            System.exit(0);
        }
       
        try {
//            portadd = tp.getProperty(PROXY);
//            String[] arrystr = portadd.split(":");
//            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(Inet4Address.getByName(arrystr[0]),
//                    Integer.valueOf(arrystr[1])));
        } catch (Exception e) {
            logger.error("Proxy配置读取失败，不使用proxy" + e);
            portadd=null;
           // proxy=null;
        }
        /**读取资源文件，并request获得第一个oauth_token*/
        sb = new Scribe(tp);
        tk = sb.getRequestToken(null);
        oauth_token = tk.getToken();
        
    }
    /**
     * 最近200条tweets信息
     * @param screenName
     * @return
     */
    public String getTimeLine(String screenName){
        Request req = null;
        Response resp = null;
        String jsonstr = null;
        req = new Request(Request.Verb.GET,
                "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name="+screenName+"&count=200");
        sb.signRequest(req, tk);        
        Boolean flag = true;
        while (flag) {
            try {
                resp = req.send(null);
                jsonstr = resp.getBody();
                flag = false;
            } catch (Exception e) {
                logger.error("没有get body" + e);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    logger.error("sleep失败" + e);
                }
            }
        }        
        return jsonstr;
    }
    
    
}
