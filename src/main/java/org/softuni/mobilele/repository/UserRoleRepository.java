package org.softuni.mobilele.repository;

import org.softuni.mobilele.model.entity.UserRoleEntity;
import org.softuni.mobilele.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    List<UserRoleEntity> findAllByRoleIn(List<UserRoleEnum> roles);
}
