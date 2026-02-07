package org.padadak.data;

import org.padadak.model.Facility;
import org.padadak.model.Price;
import org.padadak.model.Reservation;
import org.padadak.model.User;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Database {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:data/database.db";

    public Statement stat;
    public Connection conn;

    public Database() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdirs(); // Створює папку, якщо її немає
        }

        try {
            Class.forName(Database.DRIVER);
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (Exception e) {
            System.err.println("Problem with connection: " + e.getMessage());
        }
        createTables();
    }


    public boolean createTables() {
        String createUsers = "CREATE TABLE IF NOT EXISTS Users (nr INTEGER PRIMARY KEY AUTOINCREMENT, nr_zakladu varchar(255), imie varchar(255), rola varchar(255))";
        String createPriceList= "CREATE TABLE IF NOT EXISTS PriceList (nr INTEGER PRIMARY KEY AUTOINCREMENT, nazwa_uslugi varchar(255), cena int)";
        String createReservations = "CREATE TABLE IF NOT EXISTS Reservations (nr INTEGER PRIMARY KEY AUTOINCREMENT, nr_zakladu varchar(255), data varchar(255), godzina int, nazwa_uslugi varchar(255), nr_pracownika int, nr_klienta int, wykonano Boolean)";
        String createFacilities = "CREATE TABLE IF NOT EXISTS Facilities (nr INTEGER PRIMARY KEY AUTOINCREMENT, nazwa varchar(255), liczba_stanowisk int, numer_wlasciciela int, money int)";
        String createSystemTime = "CREATE TABLE IF NOT EXISTS SystemTime (id INTEGER PRIMARY KEY AUTOINCREMENT, current_date varchar(255), current_time varchar(255))";
        try {
            stat.execute(createUsers);
            stat.execute(createPriceList);
            stat.execute(createReservations);
            stat.execute(createFacilities);
            stat.executeUpdate(createSystemTime);
            PreparedStatement prepStmt = conn.prepareStatement("insert into SystemTime values (NULL, ?, ?);");
            prepStmt.setString(1, LocalDate.now().toString());
            prepStmt.setString(2, LocalTime.now().toString());
            prepStmt.execute();

        } catch (SQLException e) {
            System.out.println("Error creating tables");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertUser(int facilityName, String name, String role) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert into Users values (NULL, ?, ?, ?);");
            prepStmt.setInt(1, facilityName);
            prepStmt.setString(2, name);
            prepStmt.setString(3, role);
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Error in insertUser");
            return false;
        }
        return true;
    }

    public boolean insertPrice(String serviceName, int price) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert into PriceList values (NULL, ?, ?);");
            prepStmt.setString(1, serviceName);
            prepStmt.setInt(2, price);
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Error in insertPrice");
            return false;
        }
        return true;
    }

    public boolean insertReservation(int facilityId, String date, String time, String serviceName, int employeeId, int clientId, boolean isCompleted) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert into Reservations values (NULL, ?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setInt(1, facilityId);
            prepStmt.setString(2, date);
            prepStmt.setString(3, time);
            prepStmt.setString(4, serviceName);
            prepStmt.setInt(5, employeeId);
            prepStmt.setInt(6, clientId);
            prepStmt.setBoolean(7, isCompleted);
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Error in insertReservation");
            return false;
        }
        return true;
    }

    public boolean insertFacility(String name, int stationsCount, int ownerId) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert into Facilities values (NULL, ?, ?, ?, ?);");
            prepStmt.setString(1, name);
            prepStmt.setInt(2, stationsCount);
            prepStmt.setInt(3, ownerId);
            prepStmt.setInt(4, 0);
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Error in insertFacility");
            return false;
        }
        return true;
    }

    public List<User> selectUsers() {
        List<User> users = new LinkedList<User>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM Users");
            int nr, nr_zakladu;
            String imie, rola;
            while (result.next()) {
                nr = result.getInt("nr");
                nr_zakladu = result.getInt("nr_zakladu");
                imie = result.getString("imie");
                rola = result.getString("rola");
                users.add(new User(nr, nr_zakladu, imie, rola));
            }
        }catch (Exception e) {
            System.out.println("Error in selectUsers");
            return null;
        }
        return users;
    }

    public List<Price> selectPriceList() {
        List<Price> Prices = new LinkedList<Price>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM PriceList");
            int nr, cena;
            String nazwa_uslugi;
            while (result.next()) {
                nr = result.getInt("nr");
                nazwa_uslugi = result.getString("nazwa_uslugi");
                cena = result.getInt("cena");
                Prices.add(new Price(nr, nazwa_uslugi, cena));
            }
        }catch (Exception e) {
            System.out.println("Error in selectPriceList");
            return null;
        }
        return Prices;
    }

    public List<Reservation> selectReservations() {
        List<Reservation> reservations = new LinkedList<Reservation>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM Reservations");
            int nr, nr_zakladu, nr_pracownika, nr_klienta;
            String  nazwa_uslugi, data, godzina;
            boolean wykonano;
            while (result.next()) {
                nr = result.getInt("nr");
                nr_zakladu = result.getInt("nr_zakladu");
                data = result.getString("data");
                nazwa_uslugi = result.getString("nazwa_uslugi");
                godzina = result.getString("godzina");
                nr_pracownika = result.getInt("nr_pracownika");
                nr_klienta = result.getInt("nr_klienta");
                wykonano = result.getBoolean("wykonano");
                reservations.add(new Reservation(nr, nr_zakladu, data, godzina, nazwa_uslugi, nr_pracownika, nr_klienta, wykonano ));
            }
        }catch (Exception e) {
            System.out.println("Error in selectReservations");
            return null;
        }
        return reservations;
    }

    public List<Facility> selectFacilities() {
        List<Facility> facilities = new LinkedList<Facility>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM Facilities");
            int nr, liczba_stanowisk, numer_wlasciciela, money;
            String nazwa;
            while (result.next()) {
                nr = result.getInt("nr");
                nazwa = result.getString("nazwa");
                liczba_stanowisk = result.getInt("liczba_stanowisk");
                numer_wlasciciela = result.getInt("numer_wlasciciela");
                money = result.getInt("money");
                facilities.add(new Facility(nr, nazwa, liczba_stanowisk, numer_wlasciciela, money));
            }
        }catch (Exception e) {
            System.out.println("Error in selectFacilities");
            return null;
        }
        return facilities;
    }

    public void updatePrice(String serviceName, int newPrice) {
        try {
            String sql = "UPDATE PriceList SET cena=? WHERE nazwa_uslugi=?";
            var prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, newPrice);
            prepStmt.setString(2, serviceName);

            prepStmt.executeUpdate();
        }catch (Exception e) {
            System.out.println("Error in updatePrice");
        }
    }

    public boolean didReservation(int nr) {
        try {
            String sql = "UPDATE Reservations SET wykonano = 1 WHERE nr = ?";
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, nr);
            prepStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error in didReservation");
            return false;
        }
    }

    public boolean deleteReservationforClient(String name, int id) {
        try {
            Database db = new Database();
            List<User> users = db.selectUsers();
            List<Reservation> reservations = db.selectReservations();

            User client = null;
            Reservation found = null;

            for (User o : users)
                if (o.getName().equals(name))
                    client = o;

            if (client == null)
                return false;

            for (Reservation r : reservations)
                if (r.getId() == id)
                    found = r;

            if (found == null)
                return false;

            if (found.getClientId() != client.getId())
                return false;

            String sql = "DELETE FROM Reservations WHERE nr=?";
            var prepStmt = db.conn.prepareStatement(sql);
            prepStmt.setInt(1, id);
            prepStmt.executeUpdate();
            return true;

        } catch (Exception e) {

            System.out.println("Error in deleteReservationforClient");
            return false;
        }
    }

    public boolean deleteReservation(int id) {
        try {
            String sql = "DELETE FROM Reservations WHERE nr = ?";
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, id);
            int check = prepStmt.executeUpdate();
            return check > 0;
        } catch (Exception e) {
            System.out.println("Error in deleteReservation");
            return false;
        }
    }

    public boolean addMoney(int facilityId, int amout) {
        try {
            String sql = "UPDATE Facilities SET money = money + ? WHERE nr = ?";
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, amout);
            prepStmt.setInt(2, facilityId);
            prepStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error in addMoney");
            return false;
        }
    }

    public void setSystemTime(LocalDate date, LocalTime time) {
        try {
            String sql = "UPDATE SystemTime SET current_date=?, current_time=? WHERE id=1";
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, date.toString());
            prepStmt.setString(2, time.toString());
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in setSystemTime");
        }
    }

    public LocalDateTime getNow() {
        try {
            ResultSet rs = stat.executeQuery("SELECT * FROM SystemTime");
            return LocalDateTime.of(LocalDate.parse(rs.getString("current_date")), LocalTime.parse(rs.getString("current_time")));

        } catch (Exception e) {
            System.out.println("Error reading SystemTime, using real-time.");
            return LocalDateTime.now();
        }
    }

    public void printCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {
            ResultSet result = stat.executeQuery("SELECT * FROM SystemTime");
            String dateStr, timeStr;
            dateStr = result.getString("current_date");
            timeStr = result.getString("current_time");

            LocalDate date = LocalDate.parse(dateStr);
            LocalTime time = LocalTime.parse(timeStr);

            LocalDateTime dateTime = LocalDateTime.of(date, time);

            System.out.println("Date and time: " + dateTime.format(formatter));
            conn.close();

        } catch (Exception e) {
            System.out.println("Error reading time from SystemTime.");
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error in closeConnection");
            e.printStackTrace();
        }
    }
}
