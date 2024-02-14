package com.codecool.chess.stats;

import java.sql.Date;

public record MatchDTO(int fide_id, Date date, int round, String color, String opponent, String opp_nat, int opp_elo, double result, int moves) {
}
