package com.codecool.chess.tournament;

import com.codecool.chess.stats.MatchDTO;
import com.codecool.chess.stats.ScoresByPlayerDTO;
import com.codecool.chess.stats.ScoresByTournamentDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TournamentService {
    private final TournamentDAO tournamentDAO;

    public TournamentService(TournamentDAO tournamentDAO) {
        this.tournamentDAO = tournamentDAO;
    }

    public List<TournamentDTO> getTournaments() {
        return tournamentDAO.getTournaments();
    }
    public List<ScoresByPlayerDTO> getTournamentsByPlayer(int id) {
        return tournamentDAO.getTournamentsByPlayer(id);
    }

    public TournamentDTO getTournamentById(int id) {
        return tournamentDAO.getTournamentById(id);
    }

    public List<ScoresByTournamentDTO> getPlayersByTournament(int id) {
        return tournamentDAO.getPlayersByTournament(id);
    }

    public List<MatchDTO> getPlayerMatchesByTournament(String id, int fideId) {
        return tournamentDAO.getPlayerMatchesByTournament(id,fideId);
    }
}
