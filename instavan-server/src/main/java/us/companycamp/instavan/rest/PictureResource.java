/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.rest;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;
import sun.security.util.Length;
import us.companycamp.instavan.persist.PictureEntry;
import us.companycamp.instavan.persistManager.PictureEntryFacade;

/**
 * REST Web Service
 *
 * @author waxzce
 */
@Path("photos")
@Stateless
public class PictureResource {

    @Context
    private UriInfo context;
    @Inject
    private PictureEntryFacade nef;

    /**
     * Retrieves representation of an instance of
     * us.companycamp.instavan.rest.PictureResource
     *
     * @return an instance of javax.ws.rs.core.Response
     */
    @GET
    @Produces("image/png")
    @Path("{id}")
    public Response getimg(@PathParam("id") String id) {

        PictureEntry ne = nef.find(Long.parseLong(id));

        return Response.ok(ne.getPicture()).build();
    }
    
    
    @GET
    @Produces("image/png")
    @Path("u/{id}")
    public Response getimgbyuuid(@PathParam("id") String id) {

        PictureEntry ne = nef.getByUUID(id);

        return Response.ok(ne.getPicture()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getNewPicture(@FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) throws URISyntaxException {

        PictureEntry ne = new PictureEntry();

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


        String output = "File " + fileDetail.getType() + " uploaded with id " + ne.getId();

        
        URI uri = context.getBaseUriBuilder().path("/photos/"+ne.getId().toString()).build() ;
        
       
        
        return Response.created(uri).entity(ne.getUuid()).build();


    }
}
