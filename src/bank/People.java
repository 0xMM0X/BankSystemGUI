package bank;

import java.util.Scanner;
import java.io.*;
import java.sql.*;

public class People {

    Scanner input = new Scanner(System.in);

    public People() throws IOException {

    }

    //DB Configuration
    Statement DBconnection() throws ClassNotFoundException, SQLException {
        final String DRIVER = "com.mysql.jdbc.Driver";
        final String URL = "jdbc:mysql://localhost/users";
        String USER = "root";
        String PASSWORD = "";
        Connection Cursor = null;
        Statement State = null;
        Class.forName(DRIVER);
        Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
        State = Cursor.createStatement();
        return State;
    }

    public void Transaction(String filename, String str, float i) throws SQLException {
        try {
            int AccNum = getAccNum(filename);
            filename = String.valueOf(AccNum);
            File file = new File("C:/Users/MMOX/Desktop/BankSyS-master/Data/" + filename + ".txt");
            try (FileWriter fr = new FileWriter(file, true)) {
                fr.write(str + " " + i + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }

    public void Transaction(int AccNum, String str, float i) throws SQLException {
        try {

            String filename = String.valueOf(AccNum);
            File file = new File("C:/Users/MMOX/Desktop/BankSyS-master/Data/" + filename + ".txt");
            try (FileWriter fr = new FileWriter(file, true)) {
                fr.write(str + " " + i + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }

    public void AccDeleted(int AccNum) throws SQLException {
        try {

            String filename = String.valueOf(AccNum);
            File file = new File("C:/Users/MMOX/Desktop/BankSyS-master/Data/" + filename + ".txt");
            try (FileWriter fr = new FileWriter(file, true)) {
                fr.write("Account Deleted \n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }

    public void AccDeleted(String Email) throws SQLException {
        try {
            String filename = String.valueOf(getAccNum(Email));
            File file = new File("C:/Users/MMOX/Desktop/BankSyS-master/Data/" + filename + ".txt");
            try (FileWriter fr = new FileWriter(file, true)) {
                fr.write("Account Deleted \n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }

    public void AccCreated(String Email) throws SQLException {
        try {
            String filename = String.valueOf(getAccNum(Email));
            File file = new File("C:/Users/MMOX/Desktop/BankSyS-master/Data/" + filename + ".txt");
            try (FileWriter fr = new FileWriter(file, true)) {
                fr.write("Account Created \n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }

    public void UpdateBal(String email, float NewBalance) throws ClassNotFoundException, SQLException {
        Statement State = DBconnection();
        String query = "UPDATE acc SET `Balance` = '" + NewBalance + "' WHERE email = '" + email + "'";
        State.executeUpdate(query);

    }

    public void UpdateBal(int AccNum, float NewBalance) throws ClassNotFoundException, SQLException {
        Statement State = DBconnection();
        String query = "UPDATE acc SET `Balance` = '" + NewBalance + "' WHERE AccNum = '" + AccNum + "'";
        State.executeUpdate(query);

    }

    public int getAccNum(String Email) throws SQLException {
        ResultSet result = GetData(Email);
        int AccNum = 0;
        while (result.next()) {
            AccNum = result.getInt("AccNum");
        }
        return AccNum;
    }

    public ResultSet GetData(String email_sel) {
        ResultSet result = null;
        try {
            Statement State = DBconnection();
            String EmailToGetData = "SELECT * FROM acc WHERE email= '" + email_sel + "'";
            result = State.executeQuery(EmailToGetData);
        } catch (ClassNotFoundException | SQLException e) {
        }
        if (result != null) {
            return result;
        } else {
            return null;
        }

    }

    public ResultSet GetData(int AccountNum) {
        ResultSet result = null;
        try {
            Statement State = DBconnection();
            String sader = "SELECT * FROM acc WHERE AccNum= '" + AccountNum + "'";
            result = State.executeQuery(sader);

        } catch (ClassNotFoundException | SQLException e) {
        }
        if (result != null) {
            return result;
        } else {
            return null;
        }

    }

    public boolean login(String Email, String Password) throws SQLException {
        String DatabaseEmail = null;
        String DatabasePassword = null;
        ResultSet result = GetData(Email);
        while (result.next()) {
            DatabaseEmail = result.getString("email");
            DatabasePassword = result.getString("PASSWORD");
        }

        return Email.equals(DatabaseEmail) && Password.equals(DatabasePassword);
        //IMPORTANT: This flag is meant for the main as to provide a flag to be given and thus help you make the user
        // enter the email and password till he  gets it right.
    }

    public String EmailExist(String email_sel) throws SQLException {
        ResultSet result = GetData(email_sel);
        String Email = "Not Found";
        while (result.next()) {
            Email = result.getString("email");
        }
        return Email;
    }

    public String EmailExist(int AccNum) throws SQLException {
        ResultSet result = GetData(AccNum);
        String Email = "Not Found";
        while (result.next()) {
            Email = result.getString("AccNum");
        }
        return Email;
    }

    public int getBalance(String Email) throws SQLException {
        ResultSet result = GetData(Email);
        int Balance = 0;
        while (result.next()) {
            Balance = result.getInt("Balance");
        }
        return Balance;
    }

    public int getBalance(int AccNumber) throws SQLException {
        ResultSet result = GetData(AccNumber);
        int Balance = 0;
        while (result.next()) {
            Balance = result.getInt("Balance");
        }
        return Balance;
    }
}
