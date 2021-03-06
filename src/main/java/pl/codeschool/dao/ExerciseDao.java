package pl.codeschool.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.codeschool.model.Exercise;
import pl.codeschool.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseDao.class);

    private static final String CREATE_EXERCISE_QUERY =
        "INSERT INTO exercises(title, description) VALUES (?,?);";
    private static final String READ_EXERCISE_QUERY =
        "SELECT * FROM exercises where id = ?;";
    private static final String UPDATE_EXERCISE_QUERY =
        "UPDATE exercises SET title = ?, description = ? where id = ?;";
    private static final String DELETE_EXERCISE_QUERY =
        "DELETE FROM exercises WHERE id = ?;";
    private static final String FIND_ALL_EXERCISES_QUERY =
        "SELECT * FROM exercises;";

    public static Exercise create(Exercise exercise) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_EXERCISE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

    public static Exercise read(int exerciseId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_EXERCISE_QUERY);
            statement.setInt(1, exerciseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                return exercise;
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    public static void update(Exercise exercise) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_EXERCISE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3, exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }
    }

    public static void delete(int exerciseId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_EXERCISE_QUERY);
            statement.setInt(1, exerciseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }
    }

    public static List<Exercise> findAll() {
        try (Connection conn = DBUtil.getConnection()) {
            List<Exercise> exercises = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISES_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises.add(exercise);
            }
            return exercises;
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
            return null;
        }
    }

}