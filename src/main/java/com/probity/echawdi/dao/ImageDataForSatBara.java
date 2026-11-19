package com.probity.echawdi.dao;

public class ImageDataForSatBara {

    private String mimeType;
    private String base64;
    private long size;

    public ImageDataForSatBara() {}

    public ImageDataForSatBara(String mimeType, String base64, long size) {
        this.mimeType = mimeType;
        this.base64 = base64;
        this.size = size;
    }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public String getBase64() { return base64; }
    public void setBase64(String base64) { this.base64 = base64; }

    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }
}
