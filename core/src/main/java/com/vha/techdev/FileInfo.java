package com.vha.techdev;

public class FileInfo {
//    private final String name;
//    private final String contentType;
//    private final Long length;
    private String name;
    private String contentType;
    private Long length;

    public FileInfo() {

    }

    public FileInfo(String name, String contentType, long length) {
        this.name = name;
        this.contentType = contentType;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

    public long getLength() {
        return length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
