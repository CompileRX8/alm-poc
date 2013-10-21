package com.vha.techdev.api.impl;


import com.vha.techdev.FileInfo;
import com.vha.techdev.api.FileService;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import java.io.InputStream;
import java.util.List;

@Service("fileService#default")
public class DefaultFileService implements FileService {
    private FileInfo handleFileUpload(Attachment a) {
        try {
            ContentDisposition cd = a.getContentDisposition();

            String filename = cd.getParameter("filename");
            if(filename == null) {
                filename = "unknown.bin";
            }

            DataHandler dh = a.getDataHandler();
            String contentType = dh.getContentType();
            InputStream fileStream = dh.getInputStream();

            return new FileInfo(filename, contentType, fileStream.available());
        } catch (Exception e) {
            return null;
        }
    }

    public FileInfo fileUpload(MultipartBody formBody) {
        List<Attachment> allAttachments = formBody.getAllAttachments();
        if(allAttachments.isEmpty()) {
            return null;
        }
        for(Attachment a : allAttachments) {
            ContentDisposition cd = a.getContentDisposition();
            String name = cd.getParameter("name");
            if("uploadfile".equals(name)) {
                return handleFileUpload(a);
            }
        }
        return null;
    }
}
