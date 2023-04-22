package de.tetris.server.tetrisserver.controller;

import de.tetris.server.tetrisserver.model.HistoryEntry;
import de.tetris.server.tetrisserver.service.HistoryEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-API controller for adding history, getting history
 * and getting the highest reached level
 */
@RestController
@RequestMapping("/tetris")
public class HistoryEntryController {
    @Autowired
    private HistoryEntryService historyEntryService;

    @PostMapping("/history/add")
    public HistoryEntry postHistoryEntry(@RequestBody HistoryEntry historyEntry) {

        historyEntryService.save(historyEntry);

        return historyEntry;
    }


    @GetMapping("/history/user")
    public List<HistoryEntry> findByUserId() {

        return historyEntryService.findByUsername();

    }

    @GetMapping("/history/max-level")
    public Integer findMaxLevelByUsername() {

        return historyEntryService.findMaxLevelByUsername();

    }


}

