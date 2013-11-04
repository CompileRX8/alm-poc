package com.vha.techdev.api.impl;

import com.vha.techdev.FileInfo;
import com.vha.techdev.api.FileService;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class TestDefaultFileService {
    private TomcatHandler tomcat;

    @Before
    public void startTomcat()
            throws Exception {
        tomcat = new TomcatHandler();
    }

    @After
    public void stopTomcat()
            throws Exception {
        tomcat.shutdown();
    }

    @Test
    public void testFileUpload() throws IOException {
        // Need to add a JSON provider so the test CXF client can deserialize the response payload
        List<Object> providers = new ArrayList<Object>();
        providers.add(new JacksonJsonProvider());

        FileService service =
                JAXRSClientFactory.create("http://localhost:" + tomcat.port + "/" + tomcat.getRestServicesPath() + "/testServices/",
                        FileService.class, providers);

        String testFileName = "testfile.bin";

        InputStream testStream = getTestStream();
        int streamLength = testStream.available(); // If test stream is a ByteArrayInputStream, available() cannot throw IOException

        ContentDisposition cd = new ContentDisposition("form-data; name=\"uploadfile\"; filename=\"" + testFileName + "\"");
        Attachment a = new Attachment("root", testStream, cd);
        MultipartBody formBody = new MultipartBody(a);

        FileInfo serviceResponse = service.fileUpload(formBody);

        Assert.assertEquals(testFileName, serviceResponse.getName());
        Assert.assertEquals(a.getContentType().toString(), serviceResponse.getContentType());
        Assert.assertEquals(streamLength, serviceResponse.getLength());
    }

    private InputStream getTestStream() {
        byte[] buffer = new byte[1024];
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        return is;
    }
}
