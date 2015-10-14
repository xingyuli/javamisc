package org.swordess.toy.javamisc.springdata.jpa.service.core;

import org.swordess.toy.javamisc.springdata.jpa.model.Scoped;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QueryRequestImpl implements QueryRequest {

    private Scoped.Scope scope;
    private String scopeValue;

    private Map<String, Object> parameters = new HashMap<>();

    @Override
    public Optional<Scoped.Scope> scope() {
        return Optional.ofNullable(scope);
    }

    @Override
    public Optional<String> scopeValue() {
        return Optional.ofNullable(scopeValue);
    }

    public QueryRequestImpl scope(Scoped.Scope scope, String scopeValue) {
        this.scope = scope;
        this.scopeValue = scopeValue;
        return this;
    }

    @Override
    public <T> T param(String parameterName) {
        return (T) parameters.get(parameterName);
    }

    public QueryRequestImpl param(String parameterName, Object parameterValue) {
        parameters.put(parameterName, parameterValue);
        return this;
    }

    @Override
    public String toString() {
        return String.format("{scope=%s, scopeValue=%s, parameters=%s}", scope, scopeValue, parameters);
    }

}
