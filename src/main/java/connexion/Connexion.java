package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/foodTruckDb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private static volatile Connexion connexion;

    private Connexion() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found on classpath: " + e.getMessage());
        }
    }

    public static Connexion getconnexion() {
        if (connexion == null) {
            synchronized (Connexion.class) {
                if (connexion == null) {
                    connexion = new Connexion();
                }
            }
        }
        return connexion;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
