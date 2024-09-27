package com.bookstore.Services;

import com.bookstore.DatabaseConnection;
import com.bookstore.Models.User;
import com.bookstore.Utils.GuavaCacheUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserService {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    // Register a new user
    public Future<Void> registerUser(String username, String password) {
        return executorService.submit(() -> {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                statement.executeUpdate();

                // Assuming the user has an auto-generated ID, you could retrieve it and cache the user
                User user = new User(username, password);
                GuavaCacheUtil.put(user.getId(), user);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    // Authenticate a user
    public Future<User> authenticate(String username, String password) {
        return executorService.submit(() -> {
            // First, check the cache for the user
            for (Object cachedUser : GuavaCacheUtil.getAllCacheValues()) {
                User user = (User) cachedUser;
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    System.out.println("User found in cache.");
                    return user;
                }
            }

            // If not found in cache, check the database
            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // Create user object
                    User user = new User(resultSet.getString("username"),
                            resultSet.getString("password"));
                    user.setId(resultSet.getLong("id"));

                    // Cache the user
                    GuavaCacheUtil.put(user.getId(), user);

                    return user;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null; // Return null if authentication fails
        });
    }

    // Shutdown the executor service
    public void shutdown() {
        executorService.shutdown();
    }
}
