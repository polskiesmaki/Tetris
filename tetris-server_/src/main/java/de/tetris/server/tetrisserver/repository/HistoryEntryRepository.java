package de.tetris.server.tetrisserver.repository;

import de.tetris.server.tetrisserver.model.HistoryEntry;
import de.tetris.server.tetrisserver.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Repository class for Default Spring database interaction
 * and usecase specific methods with  JQL statements
 * for historyEntry class
 */
public interface HistoryEntryRepository extends CrudRepository<HistoryEntry, Long> {

    @Query("select h from HistoryEntry h where h.user.username = :username")
    List<HistoryEntry> findByUsername(@Param("username") String username);

    @Query("select max(h.level) from HistoryEntry h where h.user.username = :username")
    Integer findMaxLevelByUsername(@Param("username") String username);
}
