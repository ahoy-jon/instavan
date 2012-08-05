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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import us.companycamp.instavan.persist.CardEntry;
import us.companycamp.instavan.persist.PeopleAndAdress;
import us.companycamp.instavan.persist.SincerelyEntry;
import us.companycamp.instavan.persist.UploadedSincerelyEntry;
import us.companycamp.instavan.persistManager.SincerelyEntryFacade;
import us.companycamp.instavan.persistManager.UploadedSincerelyEntryFacade;
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
    @Inject
    private UploadedSincerelyEntryFacade usef;

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


        SincerelyEntry se = sef.getByUUID(id);

        return Response.ok(gson.toJson(se)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/uploaded")
    public Response getuploadedimgbyuuid(@PathParam("id") String id) {


        UploadedSincerelyEntry use = usef.getBySincerelyEntryUUID(id);
        if (use != null) {
            return Response.ok(gson.toJson(use)).build();
        } else {
            return Response.status(404).build();
        }
    }

    @GET
    @Produces("application/x-pdf")
    @Path("{id}/uploaded/{cardid}")
    public Response getuploadedimgbyuuidgetpdf(@PathParam("id") String id, @PathParam("cardid") String cardid) {

        // PictureEntry ne = nef.getByUUID(id);
        //   sef.launchPrintProcedure(ne);
        UploadedSincerelyEntry use = usef.getBySincerelyEntryUUID(id);


        for (Iterator<CardEntry> it = use.getCards().iterator(); it.hasNext();) {
            CardEntry c = it.next();
            if (c.getPrintId().equals(cardid)) {
                return Response.ok(c.getPdf_file()).build();
            }

        }


        return Response.status(404).build();

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
        PeopleAndAdress sender = new PeopleAndAdress();
        Map<String, PeopleAndAdress> mr = new TreeMap<String, PeopleAndAdress>();
        try {
            for (Iterator<Entry<String, List<FormDataBodyPart>>> it = data.getFields().entrySet().iterator(); it.hasNext();) {
                Entry<String, List<FormDataBodyPart>> ebp = it.next();
                String name = ebp.getKey();
                FormDataBodyPart bp = ebp.getValue().get(0);

                if (name.endsWith("file")) {
                    if ("frontfile".equals(name)) {
                        se.setFrontPhoto(getPiFromMultipart(bp));
                    }
                    if ("profilefile".equals(name)) {
                        se.setProfilePhoto(getPiFromMultipart(bp));
                    }
                } else if (name.startsWith("sender_")) {
                    if (name.endsWith("name")) {
                        sender.setName(bp.getValue());
                    } else if (name.endsWith("email")) {
                        sender.setEmail(bp.getValue());
                    } else if (name.endsWith("company")) {
                        sender.setCompany(bp.getValue());
                    } else if (name.endsWith("street1")) {
                        sender.setStreet1(bp.getValue());
                    } else if (name.endsWith("street2")) {
                        sender.setStreet2(bp.getValue());
                    } else if (name.endsWith("city")) {
                        sender.setCity(bp.getValue());
                    } else if (name.endsWith("state")) {
                        sender.setState_c(bp.getValue());
                    } else if (name.endsWith("postalcode")) {
                        sender.setPostalcode(bp.getValue());
                    } else if (name.endsWith("country")) {
                        sender.setCountry(bp.getValue());
                    }
                } else if (name.startsWith("recipient_")) {
                    String rname = name.substring(name.lastIndexOf("_"));
                    if (!mr.containsKey(rname)) {
                        mr.put(rname, new PeopleAndAdress());
                    }
                    if (name.startsWith("recipient_name")) {
                        mr.get(rname).setName(bp.getValue());
                    } else if (name.startsWith("recipient_email")) {
                        mr.get(rname).setEmail(bp.getValue());
                    } else if (name.startsWith("recipient_company")) {
                        mr.get(rname).setCompany(bp.getValue());
                    } else if (name.startsWith("recipient_street1")) {
                        mr.get(rname).setStreet1(bp.getValue());
                    } else if (name.startsWith("recipient_street2")) {
                        mr.get(rname).setStreet2(bp.getValue());
                    } else if (name.startsWith("recipient_city")) {
                        mr.get(rname).setCity(bp.getValue());
                    } else if (name.startsWith("recipient_state")) {
                        mr.get(rname).setState_c(bp.getValue());
                    } else if (name.startsWith("recipient_postalcode")) {
                        mr.get(rname).setPostalcode(bp.getValue());
                    } else if (name.startsWith("recipient_country")) {
                        mr.get(rname).setCountry(bp.getValue());
                    }
                } else if (name.equals("message")) {
                    se.setMessage(bp.getEntityAs(String.class));
                }


            }
            se.setTestMode(true);
            se.setRecipients(new ArrayList<PeopleAndAdress>(mr.values()));
            se.setSender(sender);

            sef.create(se);

            sef.launchPrintProcedure(se);
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
