package org.padadak.data;

import org.padadak.classes.Cennik;
import org.padadak.classes.Osoba;
import org.padadak.classes.Rezerwacja;
import org.padadak.classes.Zaklad;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Baza {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:baza.db";

    public Statement stat;
    public Connection conn;

    public Baza() {
        try {
            Class.forName(Baza.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }

        createFiles();
    }

    public boolean createFiles() {
        String createOsoby = "CREATE TABLE IF NOT EXISTS Osoby (nr INTEGER PRIMARY KEY AUTOINCREMENT, nr_zakladu varchar(255), imie varchar(255), rola varchar(255))";
        String createCennik= "CREATE TABLE IF NOT EXISTS Cennik (nr INTEGER PRIMARY KEY AUTOINCREMENT, nazwa_uslugi varchar(255), cena int)";
        String createRezerwacja = "CREATE TABLE IF NOT EXISTS Rezerwacja (nr INTEGER PRIMARY KEY AUTOINCREMENT, nr_zakladu varchar(255), data varchar(255), godzina int, nazwa_uslugi varchar(255), nr_pracownika int, nr_klienta int, wykonano Boolean)";
        String createZaklad = "CREATE TABLE IF NOT EXISTS Zaklad (nr INTEGER PRIMARY KEY AUTOINCREMENT, nazwa varchar(255), liczba_stanowisk int, numer_wlasciciela int, money int)";
        String sql = """
                    CREATE TABLE IF NOT EXISTS SystemTime (
                        id INTEGER PRIMARY KEY AUTOINCREMENT ,
                        current_date varchar(255), current_time varchar(255)
                    );
                    """;
        try {
            stat.execute(createOsoby);
            stat.execute(createCennik);
            stat.execute(createRezerwacja);
            stat.execute(createZaklad);
            stat.executeUpdate(sql);
            PreparedStatement prepStmt = conn.prepareStatement("insert into SystemTime values (NULL, ?, ?);");
            prepStmt.setString(1, LocalDate.now().toString());
            prepStmt.setString(2, LocalTime.now().toString());
            prepStmt.execute();

        } catch (SQLException e) {
            System.out.println("Blad przy tworz tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertOsoba(int nr_zakladu, String imie, String rola) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert into Osoby values (NULL, ?, ?, ?);");
            prepStmt.setInt(1, nr_zakladu);
            prepStmt.setString(2, imie);
            prepStmt.setString(3, rola);
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Blad przy insertOsoby");
            return false;
        }
        return true;
    }

    public boolean insertCennik(String nazw_uslugi, int cena) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert into Cennik values (NULL, ?, ?);");
            prepStmt.setString(1, nazw_uslugi);
            prepStmt.setInt(2, cena);
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Blad przy insertCennik");
            return false;
        }
        return true;
    }

    public boolean insertRezerwacja(int nr_zakladu, String data, String godzina, String nazwa_uslugi, int nr_pracownika, int nr_klienta, boolean wykonano) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert into Rezerwacja values (NULL, ?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setInt(1, nr_zakladu);
            prepStmt.setString(2, data);
            prepStmt.setString(3, godzina);
            prepStmt.setString(4, nazwa_uslugi);
            prepStmt.setInt(5, nr_pracownika);
            prepStmt.setInt(6, nr_klienta);
            prepStmt.setBoolean(7, wykonano);
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Blad przy insertRezerwacja");
            return false;
        }
        return true;
    }

    public boolean insertZaklad(String nazwa, int liczba_stanowisk, int numer_wlasiciela) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert into Zaklad values (NULL, ?, ?, ?, ?);");
            prepStmt.setString(1, nazwa);
            prepStmt.setInt(2, liczba_stanowisk);
            prepStmt.setInt(3, numer_wlasiciela);
            prepStmt.setInt(4, 0);
            prepStmt.execute();
        } catch (SQLException e) {
            System.out.println("Blad przy insertZaklad");
            return false;
        }
        return true;
    }

    public List<Osoba> selectOsoby() {
        List<Osoba> osoby = new LinkedList<Osoba>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM Osoby");
            int nr, nr_zakladu;
            String imie, rola;
            while (result.next()) {
                nr = result.getInt("nr");
                nr_zakladu = result.getInt("nr_zakladu");
                imie = result.getString("imie");
                rola = result.getString("rola");
                osoby.add(new Osoba(nr, nr_zakladu, imie, rola));
            }
        }catch (Exception e) {
            System.out.println("Blad przy selectOsoby");
            return null;
        }
        return osoby;
    }

    public List<Cennik> selectCenniky() {
        List<Cennik> Cenniky = new LinkedList<Cennik>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM Cennik");
            int nr, cena;
            String nazwa_uslugi;
            while (result.next()) {
                nr = result.getInt("nr");
                nazwa_uslugi = result.getString("nazwa_uslugi");
                cena = result.getInt("cena");
                Cenniky.add(new Cennik(nr, nazwa_uslugi, cena));
            }
        }catch (Exception e) {
            System.out.println("Blad przy selectCenniky");
            return null;
        }
        return Cenniky;
    }

    public List<Rezerwacja> selectRezerwacji() {
        List<Rezerwacja> Rezerwacji = new LinkedList<Rezerwacja>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM Rezerwacja");
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
                Rezerwacji.add(new Rezerwacja(nr, nr_zakladu, data, godzina, nazwa_uslugi, nr_pracownika, nr_klienta, wykonano ));
            }
        }catch (Exception e) {
            System.out.println("Blad przy selectRezerwacji");
            return null;
        }
        return Rezerwacji;
    }

    public void changeCennu(String nazwa, int cenna) {

        try {
            String sql = "UPDATE Cennik SET cena=? WHERE nazwa_uslugi=?";
            var prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, cenna);
            prepStmt.setString(2, nazwa);

            prepStmt.executeUpdate();
        }catch (Exception e) {
            System.out.println("Blad przy changeCennu");
        }
    }

    public List<Zaklad> selectZaklady() {
        List<Zaklad> Zaklady = new LinkedList<Zaklad>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM Zaklad");
            int nr, liczba_stanowisk, numer_wlasciciela, money;
            String nazwa;
            while (result.next()) {
                nr = result.getInt("nr");
                nazwa = result.getString("nazwa");
                liczba_stanowisk = result.getInt("liczba_stanowisk");
                numer_wlasciciela = result.getInt("numer_wlasciciela");
                money = result.getInt("money");
                Zaklady.add(new Zaklad(nr, nazwa, liczba_stanowisk, numer_wlasciciela, money));
            }
        }catch (Exception e) {
            System.out.println("Blad przy selectZaklady");
            return null;
        }
        return Zaklady;
    }

    public boolean deleteReservation(String name, int nr) {
        try {
            Baza b = new Baza();
            List<Osoba> osoby = b.selectOsoby();
            List<Rezerwacja> rez = b.selectRezerwacji();

            Osoba client = null;
            Rezerwacja found = null;

            for (Osoba o : osoby)
                if (o.getImie().equals(name))
                    client = o;

            if (client == null)
                return false;

            for (Rezerwacja r : rez)
                if (r.getNr() == nr)
                    found = r;

            if (found == null)
                return false;

            if (found.getNr_klienta() != client.getNr())
                return false;

            String sql = "DELETE FROM Rezerwacja WHERE nr=?";
            var prepStmt = b.conn.prepareStatement(sql);
            prepStmt.setInt(1, nr);
            prepStmt.executeUpdate();
            return true;

        } catch (Exception e) {

            System.out.println("Blad przy deleteZaklady");
            return false;
        }
    }

    public boolean didReservation(int nr) {
        try {
            String sql = "UPDATE Rezerwacja SET wykonano = 1 WHERE nr = ?";
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, nr);
            prepStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Błąd przy didReservation");
            return false;
        }
    }

    public boolean delReservation(int nr) {
        try {
            String sql = "DELETE FROM Rezerwacja WHERE nr = ?";
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, nr);
            int check = prepStmt.executeUpdate();
            return check > 0;
        } catch (Exception e) {
            System.out.println("Błąd przy delReservation");
            return false;
        }
    }

    public boolean addmoney(int nr, int cena) {
        try {
            String sql = "UPDATE Zaklad SET money = money + ? WHERE nr = ?";
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setInt(1, cena);
            prepStmt.setInt(2, nr);
            prepStmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Błąd przy addMoney");
            return false;
        }
    }

    public void set(LocalDate date, LocalTime time) {
        try {
            String sql = "UPDATE SystemTime SET current_date=?, current_time=? WHERE id=1";
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, date.toString());
            prepStmt.setString(2, time.toString());
            prepStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error saving SystemTime");
        }
    }

    public LocalDateTime teraz() {
        try {
            ResultSet rs = stat.executeQuery("SELECT * FROM SystemTime");
            return LocalDateTime.of(LocalDate.parse(rs.getString("current_date")), LocalTime.parse(rs.getString("current_time")));

        } catch (Exception e) {
            System.out.println("Error reading SystemTime, using real time.");
            return LocalDateTime.now();
        }
    }

    public void print() {
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM SystemTime");
            String date, time;
            date = result.getString("current_date");
            time = result.getString("current_time");
            System.out.println("Date and time: " + date + " " + time);
            conn.close();

        } catch (Exception e) {
            System.out.println("Error reading time from SystemTime.");
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }

}
