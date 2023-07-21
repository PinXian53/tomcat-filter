# tomcat filter
用途：解決網站弱掃 Content Security Policy (CSP)、Missing Anti-clickjacking Header 問題

### Content Security Policy (CSP)
修正方式：Response Header 要加上 Content-Security-Policy

### Missing Anti-clickjacking Header
修正方式：Response Header 要加上 X-Frame-Options

## 使用方式
客製化一個 tomcat filter ，讓 tomcat 的 response 都自動加入對應的 header

1. 將此專案打包成 jar 檔
   ```shell
   mvn clean package
   ```
2. 將 jar 丟到，tomcat 的 lib 資料夾裡面
3. 在 tomcat 的 conf/web.xml 加上 filter 設定
   - init-param 設定要加入的 response header，可以接受多筆
    ```xml
    <filter>
        <filter-name>PinoTomcatFilter</filter-name>
        <filter-class>com.pino.filter.ResponseHeaderFilter</filter-class>
        <init-param>
            <param-name>X-Frame-Options</param-name>
            <param-value>SAMEORIGIN</param-value>
        </init-param>
        <init-param>
            <param-name>Content-Security-Policy</param-name>
            <param-value>default-src 'self';script-src 'self';style-src 'self';img-src 'self';frame-ancestors 'self';form-action 'self'</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>PinoTomcatFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    ```
3. 重啟 tomcat 讓設定生效