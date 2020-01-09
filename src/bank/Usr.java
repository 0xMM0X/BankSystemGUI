package bank;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Usr extends People {

   public Usr() throws IOException {

    }

    public Usr(String Email, String Password) throws IOException, SQLException {
        this.Email = Email;
        this.Password = Password;
        if (!EmailExist(Email).equals("Not Found")) {
            if (login(Email, Password)) {
                setter(Email);
            }
        }

    }

    protected String FirstName, LastName, Email, Password;
    protected int Age, Accountnum;
    protected float Balance;

    private void setter(String Email) throws SQLException {
        ResultSet result = GetData(Email);

        while (result.next()) {
            this.Accountnum = result.getInt("AccNum");
            this.FirstName = result.getString("FirstName");
            this.LastName = result.getString("lastName");
            this.Age = result.getInt("Age");
            this.Balance = result.getFloat("Balance");

        }
    }
    Scanner input = new Scanner(System.in);

    public void PrintUserData(String Email) throws SQLException {
        setter(Email);
        System.out.println("Dear Customer, Your Information : ");
        System.out.println("Name : " + FirstName + " " + LastName);
        System.out.println("Your Email : " + Email);
        System.out.println("Your Balance : " + Balance);

    }

    public void DepositsMoney(String Email) throws ClassNotFoundException, SQLException {
        float value;
        String in = JOptionPane.showInputDialog(null, "Enter The amount to Deposite");
        value = Float.parseFloat(in);
        float balance = getBalance(Email) + value;
        UpdateBal(Email, balance);
        JOptionPane.showMessageDialog(null, (" Now, Your Balance become: " + (balance)));
        Transaction(Email, "Deposited amount", value);
    }

    public void WithdrawMoney(String Email) throws ClassNotFoundException, SQLException {
        Float value;
        String in = JOptionPane.showInputDialog(null, "Enter The amount to Deposite");
        value = Float.parseFloat(in);
        float balance = getBalance(Email);
        if (value <= balance) {
            balance = balance - value;
            UpdateBal(Email, balance);
            JOptionPane.showMessageDialog(null, ("Your Balance has Become : " + balance));
            Transaction(Email, "Withdrew amount", value);
        } else {
            JOptionPane.showMessageDialog(null, ("You don't have that amount of money.\n Your Balance is : " + balance));
        }
    }

    public void TransfarMoney(String Email) throws SQLException, ClassNotFoundException {

        String in = JOptionPane.showInputDialog(null, "Dear  Customer, Enter the amount that you want to Transfar: ");

        float value = Float.parseFloat(in);
        float balance = getBalance(Email);
        if (value > balance) {
            JOptionPane.showMessageDialog(null, "Dear Customer , the balance isn't enough: " + balance + " .");
        }

        String acco_of_recei = JOptionPane.showInputDialog(null, "Enter the Account  of recipient : ");
        if (!EmailExist(acco_of_recei).equals("Not Found")) {
            float SenderBalance = balance - value;
            Transaction(Email, "Transferred amount", (float) value);
            UpdateBal(Email, SenderBalance);

            float ReseverBalance = getBalance(acco_of_recei) + value;
            UpdateBal(acco_of_recei, ReseverBalance);
            JOptionPane.showMessageDialog(null, "Transfer successfully!\n Your Balance has become : " + SenderBalance);
            Transaction(acco_of_recei, "received amount", (float) value);
        } else {
            JOptionPane.showMessageDialog(null, EmailExist(acco_of_recei));
        }

    }

}
