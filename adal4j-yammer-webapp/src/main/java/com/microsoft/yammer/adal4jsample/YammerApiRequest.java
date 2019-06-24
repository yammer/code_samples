package com.microsoft.yammer.adal4jsample;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class YammerApiRequest {

    private String endpoint;

    private HttpMethod httpMethod;

    private HttpEntity httpEntity;

    private HttpStatus successHttpStatus;

    private String modelName;

    private String uriVariable;

    public YammerApiRequest(String endpoint,
                            HttpMethod httpMethod,
                            HttpEntity httpEntity,
                            HttpStatus successHttpStatus,
                            String modelName,
                            String uriVariable) {
        this.endpoint = endpoint;
        this.httpMethod = httpMethod;
        this.httpEntity = httpEntity;
        this.successHttpStatus = successHttpStatus;
        this.modelName = modelName;
        this.uriVariable = uriVariable;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpEntity getHttpEntity() {
        return httpEntity;
    }

    public String getUriVariable() {
        return uriVariable;
    }

    public HttpStatus getSuccessHttpStatus() {
        return successHttpStatus;
    }

    public String getModelName() {
        return modelName;
    }

}
