package com.codecool.chess.tournament;

import java.sql.Date;

public record TournamentDTO(String id, String name, String site, Date start_date, Date end_date, int number_of_players) {
}
