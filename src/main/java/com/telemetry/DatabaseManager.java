// Archivo: src/main/java/com/telemetry/DatabaseManager.java
package com.telemetry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseManager {
    // Connection URL for PostgreSQL database hosted on Supabase
    private static final String URL = "jdbc:postgresql://aws-1-eu-west-1.pooler.supabase.com:5432/postgres?sslmode=require";
    
    // Indicate the username and password for the database connection
    private static final String USER = "postgres.qjowpcvlexdgnhzinqxi";
    // Read the password from an environment variable for security reasons
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static void initDatabase() {
        // Specify the SQL command to create the metrics table if it doesn't already exist
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS metrics (
                id SERIAL PRIMARY KEY,
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                cpu_usage DOUBLE PRECISION,
                ram_usage DOUBLE PRECISION
            );
        """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Base de datos PostgreSQL inicializada y verificada.");
        } catch (Exception e) {
            System.err.println("Error al inicializar BD: " + e.getMessage());
        }
    }

    public static void insertMetric(TelemetryPayload payload) {
        String insertSQL = "INSERT INTO metrics (cpu_usage, ram_usage) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setDouble(1, payload.cpu_percent());
            pstmt.setDouble(2, payload.ram_used_mb());
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            System.err.println("Error al insertar métrica: " + e.getMessage());
        }
    }

    public static void clearTable() {
    // RESTART IDENTITY to reset the auto-incrementing ID back to 1 after truncating the table
    String truncateSQL = "TRUNCATE TABLE metrics RESTART IDENTITY;";

    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         Statement stmt = conn.createStatement()) {
        stmt.execute(truncateSQL);
        System.out.println("[DB] Tabla limpiada para la nueva sesión.");
    } catch (Exception e) {
        System.err.println("Error al limpiar la tabla: " + e.getMessage());
    }
}
}