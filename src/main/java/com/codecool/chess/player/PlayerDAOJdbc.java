package com.codecool.chess.player;

import com.codecool.chess.database.Database;
import com.codecool.chess.stats.ScoresByTournamentDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerDAOJdbc implements PlayerDAO {
    private final Database database;

    public PlayerDAOJdbc(Database database) {
        this.database = database;
    }

    @Override
    public List<PlayerDTO> getPlayers() {
        String query = """
                select * from players
                order by name;
                """;
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            List<PlayerDTO> players = new ArrayList<>();
            while (resultSet.next()) {
                PlayerDTO player = toEntity(resultSet);
                players.add(player);
            }
            return players;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PlayerDTO toEntity(ResultSet resultSet) throws SQLException {
        return new PlayerDTO(
                resultSet.getString("name"),
                resultSet.getInt("fide_id"),
                resultSet.getDate("born").toLocalDate(),
                resultSet.getString("born_place"),
                resultSet.getInt("earn_gm"),
                resultSet.getString("country"),
                resultSet.getInt("id")
        );
    }

    @Override
    public int insertPlayer(PlayerDTO player) {
        String template = "INSERT INTO players(name,fide_id,born,born_place,earn_gm,id)\n" +
                "VALUES (?,?,?,?,?,?);";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {
            prepare(player, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private void prepare(PlayerDTO player, PreparedStatement statement) throws SQLException {
        statement.setString(1, player.name());
        statement.setInt(2, player.fide_id());
        statement.setObject(3, player.born());
        statement.setString(4, player.born_place());
        statement.setInt(5, player.earn_gm());
        statement.setInt(6, player.id());
    }

    @Override
    public int deletePlayer(int id) {
        return 0;
    }

    @Override
    public Optional<PlayerDTO> selectPlayerById(int id) {
        return Optional.empty();
    }

    @Override
    public PlayerDTO getPlayer(int id) {
        String template = "SELECT * FROM players WHERE players.id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            PlayerDTO player = null;
            if (resultSet.next()) {
                player = toEntity(resultSet);
            }
            return player;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ScoresByTournamentDTO getPlayerScore(String id, int fide_id) {
        String template = """
                select fide_id, event, player,
                    extract(year from age(tour.start_date,players.born)) years,
                    extract(month from age(tour.start_date,players.born)) monthes,
                    elo,
                    sum(result) points,
                    count(*) games,
                    round( avg(o_elo) ) o_elo_avg,
                    round( avg(o_elo + 800*result -400) ) performance
                from matches
                    inner join tournaments tour on tour.id = matches.event
                    inner join players on players.name = matches.player
                where event = ?
                  and fide_id = ?
                group by event,fide_id,player,elo,years,monthes,tour.id, tour.name, site, start_date, end_date
                order by points desc, elo desc;""";
        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(template)) {
            statement.setString(1, id);
            statement.setInt(2, fide_id);
            ResultSet resultSet = statement.executeQuery();
            ScoresByTournamentDTO score = null;
            while (resultSet.next()) {
                score = toScoresByTourEntity(resultSet);
            }
            return score;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ScoresByTournamentDTO toScoresByTourEntity(ResultSet resultSet) throws SQLException{
        return new ScoresByTournamentDTO(
                resultSet.getInt("fide_id"),
                resultSet.getString("event"),
                resultSet.getString("player"),
                resultSet.getInt("years"),
                resultSet.getInt("monthes"),
                resultSet.getInt("elo"),
                resultSet.getDouble("points"),
                resultSet.getInt("games"),
                resultSet.getInt("o_elo_avg"),
                resultSet.getInt("performance")
        );
    }
}
