package com.vha.techdev.api;

import com.vha.techdev.FileInfo;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("FileService")
@Produces(MediaType.APPLICATION_JSON)
public interface FileService {
    @Path("fileUpload")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    FileInfo fileUpload(MultipartBody formBody);
}
