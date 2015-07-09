package org.swordess.toy.javamisc.springdata.jpa.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class Scoped {

    public static final String PROPERTY_SCOPE_NAME = "scopeName";
    public static final String PROPERTY_SCOPE_VALUE = "scopeValue";

    public enum Scope {

        CLUB_UUID("clubUUID");

        Scope(String scope) {
            this.scope = scope;
        }

        private String scope;

        public String get() {
            return scope;
        }

    }

    private String scopeName;
    private String scopeValue;

    @Column(name = PROPERTY_SCOPE_NAME)
    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    @Column(name = PROPERTY_SCOPE_VALUE)
    public String getScopeValue() {
        return scopeValue;
    }

    public void setScopeValue(String scopeValue) {
        this.scopeValue = scopeValue;
    }

    @Transient
    public void setScope(Scope scope, String scopeValue) {
        setScopeName(scope.get());
        setScopeValue(scopeValue);
    }

}
