package control;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

@Slf4j
public class ControllerTest {
    
    private final static String URLStr = "http://127.0.0.1:8080/ubs_mag/test/";
    private final static String URLStr1 = "http://127.0.0.1:8080/ubs_mag/test/testContoller";
    
    public static void main(String[] args) throws ClientProtocolException, IOException {
    
        ControllerTest controllerTest = new ControllerTest();
        try {
    
            controllerTest.testController();
            
        } catch (Exception e) {
        }
    }
    
    @Test
    public void testController() throws Exception {
        String[] keys = {"version", "merId", "tradeCat",  "nonceStr"};
        String[] params = {"1.0", "201708230003956", "QRPAY", "2da68d8c36697d73ffd6764ca90dad07"};
        build(keys, params, "testContoller");
    }
    
    private void build(String[] keys, String[] params,
                       String service) throws UnsupportedEncodingException {
        
        InputStream is = null;
        HttpURLConnection httpUrlConnection = null;
        try {
            
            URL url = new URL(URLStr + service);
            
            URLConnection urlConnection = url.openConnection();
            httpUrlConnection = (HttpURLConnection) urlConnection;
            
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestMethod("GET");
            
            httpUrlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpUrlConnection.setRequestProperty("Charset", "UTF-8");
            
            httpUrlConnection.connect();
            
            DataOutputStream dos = new DataOutputStream(httpUrlConnection.getOutputStream());
            dos.writeBytes("");
            dos.flush();
            dos.close();
            
            int resultCode = httpUrlConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                StringBuffer sb1 = new StringBuffer();
                String readLine = new String();
                BufferedReader responseReader = new BufferedReader(
                        new InputStreamReader(httpUrlConnection.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    sb1.append(readLine).append("\n");
                }
                Gson gson = new Gson();
                Map<String, String> result = gson.fromJson(sb1.toString(), Map.class);
                log.info("result=" + result);
                
                responseReader.close();
                System.out.println(sb1.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }
        }
    }
    
}