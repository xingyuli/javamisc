package org.swordess.toy.javamisc.springdata.jpa;

import org.springframework.data.jpa.domain.Specification;
import org.swordess.toy.javamisc.springdata.jpa.model.Scoped;
import org.swordess.toy.javamisc.springdata.jpa.model.Scoped_;

public final class Specs {

    private Specs() {
    }

    public static <T extends Scoped> Specification<T> hasScope(Scoped.Scope scope, String scopeValue) {
        return (r, q, b) -> b.and(
                b.equal(r.get(Scoped_.scopeName), scope.get()),
                b.equal(r.get(Scoped_.scopeValue), scopeValue)
        );
    }

}
