package DAO;

import Model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// mostly copied from Revature mini-project library
public class AccountDAO {
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("id"), rs.getString("name"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
        public account insertaccount(account account){
            Connection connection = ConnectionUtil.getConnection();
            try {
    //          Write SQL logic here. You should only be inserting with the name column, so that the database may
    //          automatically generate a primary key.
                String sql = "INSERT INTO account (name) VALUES (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    
                //write preparedStatement's setString method here.
                preparedStatement.setString(1, account.getName());
                preparedStatement.executeUpdate();
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                
                if(pkeyResultSet.next()){
                    int generated_account_id = (int) pkeyResultSet.getLong(1);
                    return new account(generated_account_id, account.getName());
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
}
