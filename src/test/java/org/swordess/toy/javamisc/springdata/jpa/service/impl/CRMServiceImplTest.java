package org.swordess.toy.javamisc.springdata.jpa.service.impl;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.swordess.toy.javamisc.springdata.jpa.SpringDataJPAUnitTest;
import org.swordess.toy.javamisc.springdata.jpa.model.Scoped.Scope;
import org.swordess.toy.javamisc.springdata.jpa.service.CRMService;
import org.swordess.toy.javamisc.springdata.jpa.service.core.QueryContext;
import org.swordess.toy.javamisc.springdata.jpa.service.core.QueryContextImpl;
import org.swordess.toy.javamisc.springdata.jpa.service.core.QueryRequestImpl;

public class CRMServiceImplTest extends SpringDataJPAUnitTest {

    @Autowired
    private CRMService crmService;

    @Test
    public void testAddEmployee() {
        QueryRequestImpl request = new QueryRequestImpl();
        request.scope(Scope.CLUB_UUID, "swordess")
                .param("name", "vic").param("age", 26);

        QueryContext context = new QueryContextImpl(request);
        crmService.addEmployee(context);

        Assert.assertFalse(context.getResponse().error().isPresent());
    }

}
