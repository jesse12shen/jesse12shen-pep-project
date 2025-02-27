package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// mostly copied from Revature mini-project library
public class AccountDAO {
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try { // markdown said it's not best?
            // Write SQL logic here
            String sql = "SELECT * FROM account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
                }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getInt("account_id");
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
                }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account getAccountByUser(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT account.account_id, account.username, account.password FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString method here.
            preparedStatement.setString(1, username);
            ResultSet ResultSet = preparedStatement.executeQuery();
            
            // if(ResultSet.next()){ // we expect no more than 1 username returned
            while(ResultSet.next()){
                Account credentials = new Account(ResultSet.getInt("account_id"), ResultSet.getString("username"), ResultSet.getString("password"));
                return credentials;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    // for Message Services: note that we want a username because it will be useful retrieving messages written by a user
    public String[] getUserByID(int id){
        /*
         * @return a String[] because both account_id and username are retrieved 
         */
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT account.account_id, account.username FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString method here.
            preparedStatement.setInt(1, id);
            ResultSet ResultSet = preparedStatement.executeQuery();
            
            // if(ResultSet.next()){ // we expect no more than 1 username returned
            while(ResultSet.next()){
                String[] accountInfo = {
                    // ((Integer) ResultSet.getInt("account_id")).toString()
                    ResultSet.getString("account_id"), ResultSet.getString("username")};
                // System.out.println("accountInfo is " + accountInfo[0]);
                return accountInfo; // getString() is able to cast account_id
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


}
