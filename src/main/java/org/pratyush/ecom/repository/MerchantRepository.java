package org.pratyush.ecom.repository;

import org.dozer.Mapper;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class MerchantRepository {

    private final DSLContext dsl;
    private final Mapper mapper;

    public MerchantRepository(DSLContext dsl, Mapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
    }

    public void save() {

    }
}
