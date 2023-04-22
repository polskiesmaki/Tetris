package de.tetris.server.tetrisserver.service;

import de.tetris.server.tetrisserver.model.User;

/**
 * Service interface for Users
 */
public interface UserService {

    User save(User user);
}
