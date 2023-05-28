package org.pratyush.ecom.repository;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.pratyush.ecom.db.tables.records.TblRolesRecord;
import org.pratyush.ecom.model.dto.User;
import org.pratyush.ecom.constants.Role;
import org.pratyush.ecom.model.request.AddNewRoleRequest;
import org.pratyush.ecom.model.request.UserRegistrationRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.pratyush.ecom.db.tables.TblRoles.TBL_ROLES;
import static org.pratyush.ecom.db.tables.TblUserRoles.TBL_USER_ROLES;
import static org.pratyush.ecom.db.tables.TblUsers.TBL_USERS;

@Repository
@Slf4j
public class RoleRepository {

    private final DSLContext dsl;

    public RoleRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Mono<Boolean> addNewRole(AddNewRoleRequest request) {
        TblRolesRecord record = dsl.newRecord(TBL_ROLES, request);
        return Mono.from(
                dsl.insertInto(TBL_ROLES)
                        .set(record))
                .map(i -> i > 0);
    }

    public Mono<User> attachRoles(User user) {
        return Flux.from(
                dsl.select(TBL_ROLES.ROLE)
                        .from(TBL_ROLES)
                        .join(TBL_USER_ROLES)
                        .on(TBL_USER_ROLES.ROLE_ID.eq(TBL_ROLES.ID))
                        .where(TBL_USER_ROLES.USER_ID.eq(user.getId()))
        )
                .map(this::toRoleString)
                .collectList()
                .map(list -> updateRoleList(user, list));
    }

    private User updateRoleList(User user, List<String> list) {
        user.setRoles(list);
        return user;
    }

    public Mono<Integer> addDefaultRoleForUser(UserRegistrationRequest request) {
        return Mono.zip(
                findUserIdByEmail(request.getEmail()),
                getDefaultRole()
        ).flatMap(tuple -> addRoleForUser(tuple.getT1(), tuple.getT2()));
    }

    public Mono<Integer> findUserIdByEmail(String email) {
        return Mono.from(dsl.select(TBL_USERS.ID)
                        .from(TBL_USERS)
                        .where(TBL_USERS.EMAIL.eq(email)))
                .map(Record1::value1);
    }

    public Mono<Integer> addRoleForUser(Integer userId, Integer roleId) {
        return Mono.from(dsl.insertInto(TBL_USER_ROLES)
                .set(TBL_USER_ROLES.USER_ID, userId)
                .set(TBL_USER_ROLES.ROLE_ID, roleId));
    }

    public Mono<Integer> getDefaultRole() {
        return Mono.from(dsl.select(TBL_ROLES.ID)
                        .from(TBL_ROLES)
                        .where(TBL_ROLES.ROLE.eq(Role.ROLE_USER.name())))
                .map(Record1::value1);
    }

    private String toRoleString(Record1<String> record) {
        return (String) record.get(0);
    }
}
