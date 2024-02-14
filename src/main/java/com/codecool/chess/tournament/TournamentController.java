package com.codecool.chess.tournament;

import com.codecool.chess.stats.MatchDTO;
import com.codecool.chess.stats.ScoresByPlayerDTO;
import com.codecool.chess.stats.ScoresByTournamentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(path = "/api")
public class TournamentController {
    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping(path = "/tournaments")
    public List<TournamentDTO> getTournaments() {
        return tournamentService.getTournaments();
    }

    @GetMapping(path = "/tournament/{id}")
    public TournamentDTO getTournamentById(@PathVariable int id) {
        return tournamentService.getTournamentById(id);
    }
    @GetMapping(path = "/tournament/{id}/players")
    public List<ScoresByTournamentDTO> getPlayersByTournament(@PathVariable int id) {
        return tournamentService.getPlayersByTournament(id);
    }

    @GetMapping(path = "/tournament/{id}/{fide_id}")
    public List<MatchDTO> getPlayerMatchesByTournament(@PathVariable String id, @PathVariable int fide_id) {
        return tournamentService.getPlayerMatchesByTournament(id,fide_id);
    }
}
