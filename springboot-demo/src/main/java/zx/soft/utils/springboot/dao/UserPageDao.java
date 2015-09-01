package zx.soft.utils.springboot.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import zx.soft.utils.springboot.domain.User;

public interface UserPageDao extends PagingAndSortingRepository<User, Long> {

}
