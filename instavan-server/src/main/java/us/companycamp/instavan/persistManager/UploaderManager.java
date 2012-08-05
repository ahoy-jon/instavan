/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persistManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import sun.misc.BASE64Encoder;
import us.companycamp.instavan.persist.CardEntry;
import us.companycamp.instavan.persist.SincerelyEntry;
import us.companycamp.instavan.persist.UploadedSincerelyEntry;
import us.companycamp.instavan.persist.sclyo.SincerelyCreateResponse;
import us.companycamp.instavan.persist.sclyo.SincerelyCreateResponseSendTo;
import us.companycamp.instavan.persist.sclyo.SincerelyUploadRequest;
import us.companycamp.instavan.persist.sclyo.SincerelyUploadRequestResponse;
import us.companycamp.instavan.utils.APIkeyInjector;
import us.companycamp.instavan.utils.GsonInjector;

/**
 *
 * @author waxzce
 */
@MessageDriven(mappedName = "jms/sclyq", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode",
    propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType",
    propertyValue = "javax.jms.Queue")})
public class UploaderManager implements MessageListener {

    @Inject
    private SincerelyEntryFacade sef;
    @Inject
    @APIkeyInjector.SincerelyAPI
    private String apikey;
    @GsonInjector.GsonQualifier
    @Inject
    private Gson gson;
    @Inject
    UploadedSincerelyEntryFacade usef;

    public void onMessage(Message message) {
        try {
            TextMessage tm = (TextMessage) message;
            SincerelyEntry se = sef.find(Long.parseLong(tm.getText()));

            Logger.getAnonymousLogger().info("start upload " + se.getUuid());

            BASE64Encoder b64e = new BASE64Encoder();


            SincerelyUploadRequest surfront = new SincerelyUploadRequest(apikey, b64e.encode(se.getFrontPhoto()));
            SincerelyUploadRequestResponse resjson = surfront.upload();
            if (resjson.getSuccess()) {
                se.setFrontPhotoId(resjson.getId());
            } else {
                // TODO : manage error
            }
            SincerelyUploadRequest surpro = new SincerelyUploadRequest(apikey, b64e.encode(se.getProfilePhoto()));
            SincerelyUploadRequestResponse resjsonpro = surpro.upload();
            if (resjsonpro.getSuccess()) {
                se.setProfilePhotoId(resjsonpro.getId());
            } else {
                // TODO : manage error
            }
            sef.edit(se);
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost r = new HttpPost("https://snapi.sincerely.com/shiplib/create");

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("appkey", apikey));
            nvps.add(new BasicNameValuePair("message", se.getMessage()));
            nvps.add(new BasicNameValuePair("frontPhotoId", se.getFrontPhotoId().toString()));
            nvps.add(new BasicNameValuePair("profilePhotoId", se.getProfilePhotoId().toString()));
            nvps.add(new BasicNameValuePair("testMode", se.getTestMode().toString()));
            nvps.add(new BasicNameValuePair("recipients", gson.toJson(se.getRecipients())));
            nvps.add(new BasicNameValuePair("sender", gson.toJson(se.getSender())));
            r.setEntity(new UrlEncodedFormEntity(nvps));


            HttpResponse res = httpclient.execute(r);

            SincerelyCreateResponse scr = gson.fromJson(new InputStreamReader(res.getEntity().getContent()), SincerelyCreateResponse.class);
            Logger.getAnonymousLogger().info(gson.toJson(scr));
            UploadedSincerelyEntry use = new UploadedSincerelyEntry();
            use.setSe(se);
            usef.create(use);
            for (Iterator<SincerelyCreateResponseSendTo> it = scr.getSent_to().iterator(); it.hasNext();) {
                SincerelyCreateResponseSendTo scrst = it.next();
                HttpPost rr = new HttpPost("https://snapi.sincerely.com/shiplib/debug");
                List<NameValuePair> nvps2 = new ArrayList<NameValuePair>();
                nvps2.add(new BasicNameValuePair("appkey", apikey));
                nvps2.add(new BasicNameValuePair("printId", scrst.getPrintId()));
                rr.setEntity(new UrlEncodedFormEntity(nvps2));
                HttpResponse respdf = httpclient.execute(rr);

                CardEntry ce = new CardEntry();
                ce.setPreviewUrl(scrst.getPreviewUrl());
                ce.setPrintId(scrst.getPrintId());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                respdf.getEntity().writeTo(baos);
                ce.setPdf_file(baos.toByteArray());
                use.addCard(ce);
                usef.edit(use);
                /*
                 * File f = File.createTempFile("test-" + scrst.getPrintId(),
                 * "pdf"); respdf.getEntity().writeTo(new FileOutputStream(f));
                 * Runtime.getRuntime().exec("open " + f.getAbsolutePath()); *
                 */
            }
            usef.edit(use);

        } catch (ClientProtocolException ex) {
            Logger.getLogger(UploaderManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UploaderManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UploaderManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception occurred: {0}", e.toString());

        }
    }
}
