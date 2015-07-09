package org.swordess.toy.javamisc.springdata.jpa.service.core;

public class QueryContextImpl implements QueryContext {

    private QueryRequest request;
    private QueryResponse response;

    public QueryContextImpl(QueryRequest request) {
        this.request = request;
    }

    @Override
    public QueryRequest getRequest() {
        return request;
    }

    @Override
    public QueryResponse getResponse() {
        return response;
    }

    @Override
    public void setResponse(QueryResponse response) {
        this.response = response;
    }

}
