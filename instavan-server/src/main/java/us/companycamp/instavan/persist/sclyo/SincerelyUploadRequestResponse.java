/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.persist.sclyo;

import com.google.gson.annotations.Expose;

/**
 *
 * @author waxzce
 */
public class SincerelyUploadRequestResponse {

    @Expose
    private Integer id;
    @Expose
    private Boolean success;
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
