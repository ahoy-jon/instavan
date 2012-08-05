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
public class SincerelyCreateResponseSendTo {

    @Expose
    private String previewUrl;
    @Expose
    private Integer id;
    @Expose
    private String printId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId;
    }
}
