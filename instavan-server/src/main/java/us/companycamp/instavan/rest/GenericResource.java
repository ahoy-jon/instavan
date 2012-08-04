/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.rest;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.*;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;
import sun.security.util.Length;
import us.companycamp.instavan.persist.NewEntity;
import us.companycamp.instavan.persistManager.NewEntityFacade;

/**
 * REST Web Service
 *
 * @author waxzce
 */
@Path("photos")
@Stateless
public class GenericResource {
    
    @Context
    private UriInfo context;
    @Inject
    private NewEntityFacade nef;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of
     * us.companycamp.instavan.rest.GenericResource
     *
     * @return an instance of javax.ws.rs.core.Response
     */
    @GET
    @Produces("image/png")
    @Path("{id}")
    public Response getimg(@PathParam("id") String id) {
        
        
     //   Logger.getAnonymousLogger().info("get id " + id + " long: " + Long.parseLong(id)getLong(id));
        
        NewEntity ne = nef.find(Long.parseLong(id));
        
        return Response.ok(ne.getPicture()).build();
    }
    
    @POST
    @Produces("text/plain")
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getNewPicture(@FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        
        
        String uploadedFileLocation = "/tmp/" + fileDetail.getFileName();

        // save it
        //  writeToFile(uploadedInputStream, uploadedFileLocation);

        NewEntity ne = new NewEntity();
        
        
        try {
            InputStream is = uploadedInputStream;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                baos.write(bytes, 0, read);
            }
            
            ne.setPicture(baos.toByteArray());
            nef.create(ne);
            
            
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        
        
        
        String output = "File uploaded with id " + ne.getId();
        
        return Response.status(200).entity(output).build();
        
        
    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) {
        
        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            
            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        
    }
}
