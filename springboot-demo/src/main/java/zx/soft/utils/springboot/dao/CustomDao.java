package zx.soft.utils.springboot.dao;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import zx.soft.utils.springboot.domain.User;

public interface CustomDao extends MyBaseRepository<User, Long> {
	User findByEmail(String email);
}

/**
 * NoRepositoryBean : Spring Data should not create instances for at runtime.
 *
 * @author donglei
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
interface MyBaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

	T findOne(ID id);

	T save(T entity);
}
