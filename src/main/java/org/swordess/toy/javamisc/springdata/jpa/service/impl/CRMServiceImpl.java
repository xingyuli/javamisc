package org.swordess.toy.javamisc.springdata.jpa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.swordess.toy.javamisc.springdata.jpa.model.Employee;
import org.swordess.toy.javamisc.springdata.jpa.repositories.EmployeeRepository;
import org.swordess.toy.javamisc.springdata.jpa.service.CRMService;
import org.swordess.toy.javamisc.springdata.jpa.service.core.QueryContext;
import org.swordess.toy.javamisc.springdata.jpa.service.core.QueryRequest;
import org.swordess.toy.javamisc.springdata.jpa.service.core.QueryResponseImpl;

public class CRMServiceImpl implements CRMService {

    private final Logger logger = LoggerFactory.getLogger(CRMServiceImpl.class);

    private EmployeeRepository employeeRepository;

    @Override
    public void addEmployee(QueryContext context) {
        // use aspect before to accomplish this
        logger.debug("addEmployee request received: {} ", context.getRequest());

        QueryRequest req = context.getRequest();
        String name = req.param("name");
        int age = req.param("age");

        Employee e = new Employee();
        if (req.scope().isPresent()) {
            e.setScope(req.scope().get(), req.scopeValue().get());
        }
        e.setName(name);
        e.setAge(age);

        QueryResponseImpl resp = new QueryResponseImpl();
        try {
            employeeRepository.save(e);
        } catch (Exception ex) {
            logger.error("failed to save employee: {}", req, ex);
            resp.error(ex);
        }
        context.setResponse(resp);
    }

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

}
