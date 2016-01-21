package zx.soft.utils.springboot.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import zx.soft.utils.springboot.domain.User;
import zx.soft.utils.springboot.domain.User.Name;

@Transactional
public interface UserDao extends JpaRepository<User, Long> {

	/**
	* This method will find an User instance in the database by its email.
	* Note that this method is not implemented and its working code will be
	* automagically generated from its signature by Spring Data JPA.
	*/
	User findByEmail(String email);

	/**
	 * ERROR : Long deleteByLastName(String lastName);
	 * Property expressions can refer only to a direct property of the managed entity
	 * 下面的使用是正确的
	 */
	long deleteByName(Name name);

	List<User> removeByName(Name name);

	List<User> findByName(Name name);

	List<User> findByEmailAndName(String email, Name name);

	List<User> findDistinctUserByEmail(String email);

	/**
	 * 根据嵌套属性查询。
	 * 详见： http://docs.spring.io/spring-data/jpa/docs/1.9.0.RC1/reference/html/#repositories.query-methods.query-property-expressions
	 * @param lastName
	 * @return
	 */
	List<User> findByName_LastName(String lastName);

	/**
	 * apply pagination and sorting to your queries dynamically
	 * @param lastname
	 * @param pageable
	 * @return
	 */
	Page<User> findByEmail(String email, Pageable pageable);

	List<User> findByEmail(String email, Sort sort);

	/**
	 * first / top N
	 * @return
	 */
	User findTopByOrderByName_LastNameAsc();

	/**
	 * @query 使用
	 * @param firstname
	 * @return
	 */
	@Query("select u from User u where u.name.firstName like %?1")
	List<User> findByFirstnameEndsWith(String firstName);

	@Query("select u from User u where u.name.firstName = :firstname or u.name.lastName = :lastname")
	User findByLastnameOrFirstname(@Param("lastname") String lastname, @Param("firstname") String firstname);

	@Modifying
	@Query("update User u set u.name.lastName = ?2 where u.name.firstName = ?1")
	int setFixedFirstnameFor(String firstname, String lastname);

	/**
	 * @Lock 和 @Transactional 使用
	 */

}