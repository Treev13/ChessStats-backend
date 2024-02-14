package com.codecool.chess.player;

import com.codecool.chess.stats.ScoresByPlayerDTO;
import com.codecool.chess.stats.ScoresByTournamentDTO;
import com.codecool.chess.tournament.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerController {
    private final PlayerService playerService;
    private final TournamentService tourService;

    @Autowired
    public PlayerController(PlayerService playerService, TournamentService tourService) {
        this.playerService = playerService;
        this.tourService = tourService;
    }

    @GetMapping
    @RequestMapping(path = "/api/players")
    public List<PlayerDTO> getPlayers() {
        return playerService.getPlayers();
    }

    @GetMapping
    @RequestMapping(path = "/api/player/{id}")
    public PlayerDTO getPlayer(@PathVariable int id) {return playerService.getPlayer(id);}

    @GetMapping(path = "/api/player/{id}/tours")
    public List<ScoresByPlayerDTO> getTournamentsByPlayer(@PathVariable int id) {
        return tourService.getTournamentsByPlayer(id);
    }
    @GetMapping
    @RequestMapping(path = "/api/player/{id}/{fide_id}")
    public ScoresByTournamentDTO getPlayerScore(@PathVariable String id, @PathVariable int fide_id) {
        return playerService.getPlayerScore(id,fide_id);
    }
}
