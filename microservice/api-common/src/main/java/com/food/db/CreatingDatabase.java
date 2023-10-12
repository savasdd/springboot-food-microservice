package com.food.db;

import com.food.props.ApplicationProperties;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
@Builder
public class CreatingDatabase {

    public CreatingDatabase() {
        Connection connection = null;
        Statement statement = null;
        try {
            log.debug("Creating database if not exist...");
            connection = DriverManager.getConnection("jdbc:postgresql://" + ApplicationProperties.getValue("server.host") + ":5432/", ApplicationProperties.getValue("spring.datasource.username"), ApplicationProperties.getValue("spring.datasource.password"));
            statement = connection.createStatement();
            statement.executeQuery("SELECT count(*) FROM pg_database WHERE datname = '" + ApplicationProperties.getValue("server.db") + "'");
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count <= 0) {
                statement.executeUpdate("CREATE DATABASE " + ApplicationProperties.getValue("server.db"));
                log.debug("Database created.");
            } else {
                log.debug("Database already exist.");
            }
        } catch (Exception e) {
            log.error(e.toString());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    log.debug("Closed Statement.");
                }
                if (connection != null) {
                    log.debug("Closed Connection.");
                    connection.close();
                }
            } catch (SQLException e) {
                log.error(e.toString());
            }
        }
    }
}
