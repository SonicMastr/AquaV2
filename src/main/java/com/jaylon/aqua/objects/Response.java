package com.jaylon.aqua.objects;


public class Response {
    private int status;
    private String responseContent;

    public Response(int status, String responseContent) {
        this.status = status;
        this.responseContent = responseContent;
    }

    public int getStatus() {
        return this.status;
    }

    public String getResponseContent() {
        return this.responseContent;
    }

}
