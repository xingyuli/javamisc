package org.swordess.toy.javamisc.springdata.jpa.service.core;

public interface QueryContext {

    QueryRequest getRequest();

    QueryResponse getResponse();

    void setResponse(QueryResponse response);

}
