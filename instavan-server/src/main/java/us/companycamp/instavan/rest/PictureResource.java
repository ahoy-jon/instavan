/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package us.companycamp.instavan.rest;

import com.google.gson.Gson;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import java.io.*;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import us.companycamp.instavan.persist.SincerelyEntry;
import us.companycamp.instavan.persistManager.SincerelyEntryFacade;
import us.companycamp.instavan.utils.GsonInjector;

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
    @GsonInjector.GsonQualifier
    @Inject
    private Gson gson;
    @Inject
    private SincerelyEntryFacade sef;

    /**
     * Retrieves representation of an instance of
     * us.companycamp.instavan.rest.PictureResource
     *
     * @return an instance of javax.ws.rs.core.Response
     *
     * @GET @Produces("image/png") @Path("{id}") public Response
     * getimg(@PathParam("id") String id) {
     *
     * PictureEntry ne = nef.find(Long.parseLong(id));
     *
     * return Response.ok(ne.getPicture()).build(); }
     *
     *
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getimgbyuuid(@PathParam("id") String id) {

        // PictureEntry ne = nef.getByUUID(id);
        //   sef.launchPrintProcedure(ne);
        SincerelyEntry se = sef.getByUUID(id);

        return Response.ok(gson.toJson(se)).build();
    }
    
    @GET
    @Produces("image/png")
    @Path("{id}/front")
    public Response getfrontimgbyuuid(@PathParam("id") String id) {

        // PictureEntry ne = nef.getByUUID(id);
        //   sef.launchPrintProcedure(ne);
        SincerelyEntry se = sef.getByUUID(id);

        return Response.ok(se.getFrontPhoto()).build();
    }
    
     @GET
    @Produces("image/png")
    @Path("{id}/profile")
    public Response getprofileimgbyuuid(@PathParam("id") String id) {

        // PictureEntry ne = nef.getByUUID(id);
        //   sef.launchPrintProcedure(ne);
        SincerelyEntry se = sef.getByUUID(id);

        return Response.ok(se.getProfilePhoto()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response getNewPicture(
            com.sun.jersey.multipart.FormDataMultiPart data /*
             * ,
             * @FormDataParam("frontfile") InputStream uploadedInputStream,
             * @FormDataParam("frontfile") FormDataContentDisposition
             * fileDetail, @FormDataParam("profilefile") InputStream
             * uploadedInputStream2, @FormDataParam("profilefile")
             * FormDataContentDisposition fileDetail2
             *
             */) {

        SincerelyEntry se = new SincerelyEntry();
        try {
            for (Iterator<Entry<String, List<FormDataBodyPart>>> it = data.getFields().entrySet().iterator(); it.hasNext();) {
                Entry<String, List<FormDataBodyPart>> ebp = it.next();
                String name = ebp.getKey();
                FormDataBodyPart bp = ebp.getValue().get(0);
                Logger.getAnonymousLogger().info(ebp.getKey());
                Logger.getAnonymousLogger().info(ebp.getValue().get(0).toString());

                if (name.endsWith("file")) {
                    if ("frontfile".equals(name)) {
                        se.setFrontPhoto(getPiFromMultipart(bp));

                    }
                    if ("profilefile".equals(name)) {
                        se.setProfilePhoto(getPiFromMultipart(bp));
                    }
                } else if (name.startsWith("sender_")) {
                } else if (name.equals("message")) {
                    se.setMessage(bp.getEntityAs(String.class));
                    Logger.getAnonymousLogger().info(bp.getEntityAs(String.class));
                }


                /*
                 * if (bp.getMediaType().getType().equalsIgnoreCase("image")) {
                 * if (isHeader(bp, "frontfile")) {
                 * se.setFrontPhoto(getPiFromMultipart(bp)); } if (isHeader(bp,
                 * "profilefile")) { se.setProfilePhoto(getPiFromMultipart(bp));
                 * } } if (isHeader(bp, "message")) {
                 * se.setMessage(bp.getEntityAs(String.class));
                 * Logger.getAnonymousLogger().info(bp.getEntityAs(String.class));
                 *
                 * }
                 * Logger.getAnonymousLogger().info(bp.getMediaType().toString());
                 * Logger.getAnonymousLogger().info(bp.getHeaders().toString());
                 *
                 */
            }

            sef.create(se);


            URI uri = context.getBaseUriBuilder().path("/photos/" + se.getUuid().toString()).build();// + ne.getId().toString()).build();

            return Response.created(uri).entity(gson.toJson(se)).build();

            //     } catch (IOException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
         * catch (URISyntaxException e) { e.printStackTrace(); }
         */

        return Response.serverError().build();


    }

    private Boolean isHeader(BodyPart bp, String name) {
        return name.equals(bp.getContentDisposition().getParameters().get("name"));
    }

    private byte[] getPiFromMultipart(FormDataBodyPart bp) throws IOException {
        byte[] bytes = new byte[1024];
        int read = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = ((BodyPartEntity) bp.getEntity()).getInputStream();
        while ((read = is.read(bytes)) != -1) {
            baos.write(bytes, 0, read);
        }
        return baos.toByteArray();
    }
}
