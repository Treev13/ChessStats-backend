package com.codecool.chess.player;

import com.codecool.chess.stats.ScoresByTournamentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerDAO playerDAO;

    public PlayerService(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public List<PlayerDTO> getPlayers() {
        return playerDAO.getPlayers();

    }

    public PlayerDTO getPlayer(int id) {
        return playerDAO.getPlayer(id);
    }

    public ScoresByTournamentDTO getPlayerScore(String id, int fide_id) {
        return playerDAO.getPlayerScore(id,fide_id);
    }
}
