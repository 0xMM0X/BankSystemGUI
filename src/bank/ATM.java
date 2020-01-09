package bank;

import java.io.IOException;
import java.sql.*;
import javax.swing.JOptionPane;

public class ATM extends Usr {

    public ATM() throws IOException, SQLException {
    }

    public ATM(int AccNumber, int AccPin) throws IOException, SQLException {
        this.AccNumber = AccNumber;
        this.AccPin = AccPin;
    }

    int AccNumber, AccPin;

    public boolean AtmLogin(int Account, int Accpin) throws SQLException {
        ResultSet result = GetData(Account);
        int AccountNumber = 0;
        int AccountPin = 0;
        while (result.next()) {
            AccountNumber = result.getInt("AccNum");
            AccountPin = result.getInt("pinCode");
        }

        if (Account == AccountNumber && AccountPin == Accpin) {

            return true;
        } else {
            return false;
        }

    }

    public void TransferMoneyATM(int AccNumber) throws SQLException, ClassNotFoundException {
        String in = JOptionPane.showInputDialog(null, " Enter the amount that you want to Transfer:");
        float value = Float.parseFloat(in);
        float Balance = getBalance(AccNumber);
        if (value > Balance) {
            JOptionPane.showMessageDialog(null, "Dear Customer , Please Enter A money You have you are poor : " + Balance + " .");
        }
        String acc = JOptionPane.showInputDialog(null, "Enter the Account  of receiver : ");
        int acco_of_recei = Integer.parseInt(acc);
        if (!EmailExist(acco_of_recei).equals("Not Found")) {
            float SenderBalance = Balance - value;
            Transaction(AccNumber, "Transffered amount", SenderBalance);
            UpdateBal(AccNumber, SenderBalance);
            float ReseverBalance = getBalance(acco_of_recei) + value;
            UpdateBal(acco_of_recei, ReseverBalance);
            Transaction(acco_of_recei, "Transffered amount", SenderBalance);

            JOptionPane.showMessageDialog(null, "Transaction succeeded\n Your Balance has Become : " + SenderBalance);
        } else {
            JOptionPane.showMessageDialog(null, EmailExist(acco_of_recei));
        }

    }

    public void AccInfo() throws SQLException {
        ResultSet result = GetData(AccNumber);
        String Name = "", email = "";
        int AccountNumber = AccNumber;
        float Balance1 = 0;
        if (!EmailExist(AccNumber).equals("Not Found")) {
            while (result.next()) {
                Name = result.getString("FirstName") + " " + result.getString("lastName");
                email = result.getString("email");
                AccountNumber = result.getInt("AccNum");
                Balance1 = result.getFloat("Balance");
            }

        }

    }

    public void changePin(int AccNum, int pin) throws ClassNotFoundException, SQLException {
        Statement State = DBconnection();

        try {
            String query = "UPDATE acc SET `pinCode` = '" + pin + "' WHERE AccNum = '" + AccNum + "'";
            State.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Pin Code Changed successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An Error Occered!");

        }

    }

    public void WithdrawATM(int AccNum) throws ClassNotFoundException, SQLException {
        String in = JOptionPane.showInputDialog(null, " Enter the amount that you want to Withdraw:");
        float value = Float.parseFloat(in);
        float balance = getBalance(AccNum);
        if (value <= balance) {
            balance = balance - value;
            UpdateBal(AccNum, balance);
            JOptionPane.showMessageDialog(null, "Your new Balnce : " + balance + "\n Withdrow successfully!");
            Transaction(AccNum, "Withdrow amount", value);
        } else {
            JOptionPane.showMessageDialog(null, "You don't have that amount of money.\n Your Balance is : " + balance);
        }
    }

    public void DepositsMoney(int AccNum) throws ClassNotFoundException, SQLException {
        String in = JOptionPane.showInputDialog(null, " Enter the amount that you want to deposit:");
        float value = Float.parseFloat(in);
        float balance = getBalance(AccNum) + value;
        UpdateBal(AccNum, balance);
        JOptionPane.showMessageDialog(null, " Now, Your Balance become: " + (balance));
        Transaction(AccNum, "Deposited amount", value);
    }

    public void deactivation(int Acc) throws SQLException, ClassNotFoundException {
        Statement State = DBconnection();

        try {
            String query = "DELETE FROM acc WHERE AccNum='" + Acc + "'";
            State.executeUpdate(query);
            AccDeleted(Acc);
            JOptionPane.showMessageDialog(null, "Your records have been deleted from our database");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An Error Occered");

        }
    }

}
