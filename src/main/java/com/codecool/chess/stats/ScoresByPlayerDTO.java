package com.codecool.chess.stats;

import java.sql.Date;

public record ScoresByPlayerDTO(int id, String name, String site, Date start_date, Date end_date, int years, int months,int elo, double points, int games, int performance) {
}
