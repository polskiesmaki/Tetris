package de.tetris.server.tetrisserver.service;

import de.tetris.server.tetrisserver.model.HistoryEntry;
import de.tetris.server.tetrisserver.model.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Service interface for HistoryEntries
 */
public interface HistoryEntryService {

    HistoryEntry save(HistoryEntry historyEntry);

    List<HistoryEntry> findByUsername();

    Integer findMaxLevelByUsername();
}
