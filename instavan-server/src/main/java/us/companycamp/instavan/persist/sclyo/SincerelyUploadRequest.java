/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persist.sclyo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import us.companycamp.instavan.utils.GsonInjector;

/**
 *
 * @author waxzce
 */
public class SincerelyUploadRequest {

    @Expose
    private String appkey;
    @Expose
    private String photo;

    public SincerelyUploadRequestResponse upload() throws UnsupportedEncodingException, IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost r = new HttpPost("https://snapi.sincerely.com/shiplib/upload");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("appkey", this.getAppkey()));
        nvps.add(new BasicNameValuePair("photo", this.getPhoto()));
        r.setEntity(new UrlEncodedFormEntity(nvps));

        HttpResponse res = httpclient.execute(r);
        return gson.fromJson(new InputStreamReader(res.getEntity().getContent()), SincerelyUploadRequestResponse.class);
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public SincerelyUploadRequest(String appkey, String photo) {
        this.appkey = appkey;
        this.photo = photo;
    }
}
