package org.swordess.toy.javamisc.springdata.jpa.service.core;

import java.util.Optional;

public class QueryResponseImpl implements QueryResponse {

    private Exception error;

    @Override
    public Optional<Exception> error() {
        return Optional.ofNullable(error);
    }

    public QueryResponseImpl error(Exception error) {
        this.error = error;
        return this;
    }

}
