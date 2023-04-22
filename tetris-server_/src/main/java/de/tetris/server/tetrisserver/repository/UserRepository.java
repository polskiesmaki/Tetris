package de.tetris.server.tetrisserver.repository;

import de.tetris.server.tetrisserver.model.HistoryEntry;
import de.tetris.server.tetrisserver.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Repository class for Default Spring database interaction
 * and usecase specific methods
 * for User class
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(@Param("username") String username);


}
