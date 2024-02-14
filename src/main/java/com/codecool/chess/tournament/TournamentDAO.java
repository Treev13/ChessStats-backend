package com.codecool.chess.tournament;

import com.codecool.chess.stats.MatchDTO;
import com.codecool.chess.stats.ScoresByPlayerDTO;
import com.codecool.chess.stats.ScoresByTournamentDTO;

import java.util.List;
import java.util.Optional;

public interface TournamentDAO {
    List<TournamentDTO> getTournaments();
    List<ScoresByPlayerDTO> getTournamentsByPlayer(int id);
    int insertTournament(TournamentDTO tournament);
    int deleteTournament(int id);
    Optional<TournamentDTO> selectTournamentById(int id);

    TournamentDTO getTournamentById(int id);
    List<ScoresByTournamentDTO> getPlayersByTournament(int id);

    List<MatchDTO> getPlayerMatchesByTournament(String id, int fideId);
    // TODO: Update
}
