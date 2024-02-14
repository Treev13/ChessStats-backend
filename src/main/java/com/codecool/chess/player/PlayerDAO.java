package com.codecool.chess.player;

import com.codecool.chess.stats.ScoresByTournamentDTO;

import java.util.List;
import java.util.Optional;

public interface PlayerDAO {
    List<PlayerDTO> getPlayers();
    int insertPlayer(PlayerDTO player);
    int deletePlayer(int id);
    Optional<PlayerDTO> selectPlayerById(int id);

    PlayerDTO getPlayer(int id);

    ScoresByTournamentDTO getPlayerScore(String id, int fideId);
    // TODO: Update
}
