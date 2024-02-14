package com.codecool.chess.tournament;

import com.codecool.chess.database.Database;
import com.codecool.chess.stats.MatchDTO;
import com.codecool.chess.stats.ScoresByPlayerDTO;
import com.codecool.chess.stats.ScoresByTournamentDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TournamentDAOJdbc implements TournamentDAO{
    private final Database database;

    public TournamentDAOJdbc(Database database) {
        this.database = database;
    }
    @Override
    public List<TournamentDTO> getTournaments() {
        String query = """
                select id,name,site,start_date,end_date,number_of_players from tournaments
                order by start_date;
                """;
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            List<TournamentDTO> tournaments = new ArrayList<>();
            while (resultSet.next()) {
                TournamentDTO tournament = toEntity(resultSet);
                tournaments.add(tournament);
            }
            return tournaments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ScoresByPlayerDTO> getTournamentsByPlayer(int id) {
        String template = """
                select tour.id, tour.name, site, start_date, end_date,
                    extract(year from age(tour.start_date,players.born)) years,
                    extract(month from age(tour.start_date,players.born)) months,
                    elo,
                    sum(result) points,
                    count(*) games,
                    round( avg(o_elo + 800 * result - 400) ) performance
                from matches
                    inner join tournaments tour on tour.short_name = matches.event
                    inner join players on players.name = matches.player
                  where players.id = ?
                  group by player,event,elo,years,months,tour.id, tour.name, site, start_date, end_date
                order by start_date;
                """;
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<ScoresByPlayerDTO> scores = new ArrayList<>();
            while (resultSet.next()) {
                ScoresByPlayerDTO score = toScoresEntity(resultSet);
                scores.add(score);
            }
            return scores;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ScoresByTournamentDTO> getPlayersByTournament(int id) {
        String template = """
                select players.id, event, player,
                    extract(year from age(tour.start_date,players.born)) years,
                    extract(month from age(tour.start_date,players.born)) months,
                    elo,
                    sum(result) points,
                    count(*) games,
                    round( avg(o_elo) ) o_elo_avg,
                    round( avg(o_elo + 800 * result - 400) ) performance
                from matches
                    inner join tournaments tour on tour.short_name = matches.event
                    inner join players on players.name = matches.player
                where tour.id = ?
                group by players.id,event,player,elo,years,months,tour.id, tour.name, site, start_date, end_date
                order by points desc, elo desc;""";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<ScoresByTournamentDTO> scores = new ArrayList<>();
            while (resultSet.next()) {
                ScoresByTournamentDTO score = toScoresByTourEntity(resultSet);
                scores.add(score);
            }
            return scores;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MatchDTO> getPlayerMatchesByTournament(String id, int fideId) {
        String template = """
                select fide_id,date,round,color,opponent,o_nat,o_elo,result,move\s
                from matches
                inner join players on players.name = matches.player
                where event = ?
                  and fide_id = ?
                order by round;""";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {
            statement.setString(1, id);
            statement.setInt(2, fideId);
            ResultSet resultSet = statement.executeQuery();
            List<MatchDTO> matches = new ArrayList<>();
            while (resultSet.next()) {
                MatchDTO match = toMatchEntity(resultSet);
                matches.add(match);
            }
            return matches;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private MatchDTO toMatchEntity(ResultSet resultSet) throws SQLException{
        return new MatchDTO(
                resultSet.getInt("fide_id"),
                resultSet.getDate("date"),
                resultSet.getInt("round"),
                resultSet.getString("color"),
                resultSet.getString("opponent"),
                resultSet.getString("o_nat"),
                resultSet.getInt("o_elo"),
                resultSet.getDouble("result"),
                resultSet.getInt("move")
        );
    }

    private ScoresByTournamentDTO toScoresByTourEntity(ResultSet resultSet) throws SQLException{
        return new ScoresByTournamentDTO(
                resultSet.getInt("id"),
                resultSet.getString("event"),
                resultSet.getString("player"),
                resultSet.getInt("years"),
                resultSet.getInt("months"),
                resultSet.getInt("elo"),
                resultSet.getDouble("points"),
                resultSet.getInt("games"),
                resultSet.getInt("o_elo_avg"),
                resultSet.getInt("performance")
        );
    }

    private TournamentDTO toEntity(ResultSet resultSet) throws SQLException {
        return new TournamentDTO(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("site"),
                resultSet.getDate("start_date"),
                resultSet.getDate("end_date"),
                resultSet.getInt("number_of_players")
        );
    }
    private ScoresByPlayerDTO toScoresEntity(ResultSet resultSet) throws SQLException {
        return new ScoresByPlayerDTO(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("site"),
                resultSet.getDate("start_date"),
                resultSet.getDate("end_date"),
                resultSet.getInt("years"),
                resultSet.getInt("months"),
                resultSet.getInt("elo"),
                resultSet.getDouble("points"),
                resultSet.getInt("games"),
                resultSet.getInt("performance")
        );
    }
    @Override
    public int insertTournament(TournamentDTO tournament) {
        return 0;
    }

    @Override
    public int deleteTournament(int id) {
        return 0;
    }

    @Override
    public Optional<TournamentDTO> selectTournamentById(int id) {
        return Optional.empty();
    }

    @Override
    public TournamentDTO getTournamentById(int id) {
        String template = "SELECT * FROM tournaments WHERE id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            TournamentDTO tournament = null;
            if (resultSet.next()) {
                tournament = toEntity(resultSet);
            }
            return tournament;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
