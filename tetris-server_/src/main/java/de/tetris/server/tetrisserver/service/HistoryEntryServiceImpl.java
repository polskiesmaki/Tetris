package de.tetris.server.tetrisserver.service;

import de.tetris.server.tetrisserver.model.HistoryEntry;
import de.tetris.server.tetrisserver.model.User;
import de.tetris.server.tetrisserver.repository.HistoryEntryRepository;
import de.tetris.server.tetrisserver.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class for HistoryEntries
 * Defines business logic
 */
@Service
public class HistoryEntryServiceImpl implements HistoryEntryService {
    @Autowired
    private HistoryEntryRepository historyEntryRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public HistoryEntry save(HistoryEntry historyEntry) {
        historyEntry.setUser(userRepository.findByUsername(getUsername()));
        return historyEntryRepository.save(historyEntry);
    }

    @Override
    public List<HistoryEntry> findByUsername() {
        return historyEntryRepository.findByUsername(getUsername());
    }

    @Override
    public Integer findMaxLevelByUsername() {
        return historyEntryRepository.findMaxLevelByUsername(getUsername());
    }

    private static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
