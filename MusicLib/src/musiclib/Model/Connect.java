package musiclib.Model;

import java.sql.*;
import java.util.Properties;

public class Connect {

    // init connection object
    private Connection connection;

    public Connect(){
    }

    // create properties
    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("user", MusicDAO.USERNAME);
        properties.setProperty("password", MusicDAO.PASSWORD);
        properties.setProperty("MaxPooledStatements", "250");

        return properties;
    }

    // connect database
    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://"+ MusicDAO.DATABASE_URL +":3306/"+ MusicDAO.DATABASE_NAME, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getLocalizedMessage());
                    System.out.println("connect");
            }
        }
        return connection;
    }

    // disconnect database
    void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
                System.out.println("disconnect");
            }
        }
    }

    Statement createStmt(){
        Statement state = null;
        if(connection != null) {
            try {
                state = connection.createStatement();
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
                System.out.println("createStatement");
            }
        }else{
            System.out.println("merda");
        }
        return state;
    }
}
