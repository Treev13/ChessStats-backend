package com.codecool.chess;

import com.codecool.chess.database.Database;
import com.codecool.chess.player.PlayerDAO;
import com.codecool.chess.player.PlayerDAOJdbc;
import com.codecool.chess.tournament.TournamentDAO;
import com.codecool.chess.tournament.TournamentDAOJdbc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChessDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChessDatabaseApplication.class, args);
    }
    @Bean
    public PlayerDAO playerDAO() {
        Database database = new Database(
                System.getenv("database"),
                System.getenv("dbuser"),
                System.getenv("password"));
        return new PlayerDAOJdbc(database);
    }
    @Bean
    public TournamentDAO tournamentDAO() {
        Database database = new Database(
                System.getenv("database"),
                System.getenv("dbuser"),
                System.getenv("password"));
        return new TournamentDAOJdbc(database);
    }
}
