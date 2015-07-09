package org.swordess.toy.javamisc.springdata.jpa.service.core;

import org.swordess.toy.javamisc.springdata.jpa.model.Scoped;

import java.util.Optional;

public interface QueryRequest {
    // TODO refactoring
    Optional<Scoped.Scope> scope();
    Optional<String> scopeValue();
    <T> T param(String parameterName);
}
