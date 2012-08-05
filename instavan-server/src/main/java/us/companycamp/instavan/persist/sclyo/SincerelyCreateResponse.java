/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persist.sclyo;

import com.google.gson.annotations.Expose;
import java.util.List;

/**
 *
 * @author waxzce
 */
public class SincerelyCreateResponse {
    @Expose
    private Boolean success;
    @Expose
    private Integer id;
    @Expose
    private Boolean testMode;
    @Expose
    private String sender_id;
    @Expose
    private List<SincerelyCreateResponseSendTo> sent_to;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

   
    public List<SincerelyCreateResponseSendTo> getSent_to() {
        return sent_to;
    }

    public void setSent_to(List<SincerelyCreateResponseSendTo> sent_to) {
        this.sent_to = sent_to;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getTestMode() {
        return testMode;
    }

    public void setTestMode(Boolean testMode) {
        this.testMode = testMode;
    }
    
    
}
