package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// mostly copied from Revature mini-project library
public class MessageDAO {
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> Messages = new ArrayList<>();
        try { // markdown said it's not best?
            // Write SQL logic here
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message Message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                Messages.add(Message);
                }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return Messages;
    }
    public Message insertMessage(Message Message){
        Connection connection = ConnectionUtil.getConnection();
        try {
//          Write SQL logic here. You should only be inserting with the name column, so that the database may
//          automatically generate a primary key.
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setInt(1, Message.getPosted_by());
            preparedStatement.setString(2, Message.getMessage_text());
            preparedStatement.setLong(3, Message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            
            if(pkeyResultSet.next()){
                int generated_Message_id = (int) pkeyResultSet.getInt("Message_id");
                return new Message(generated_Message_id, Message.getPosted_by(), Message.getMessage_text(), Message.getTime_posted_epoch());
                }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    // Don't think I need this method from the AuthorDAO
    // public Message getMessageByUser(String username){
    //     Connection connection = ConnectionUtil.getConnection();
    //     try {
    //         String sql = "SELECT Message.Message_id, Message.username, Message.password FROM Message WHERE username = ?";
    //         PreparedStatement preparedStatement = connection.prepareStatement(sql);

    //         //write preparedStatement's setString method here.
    //         preparedStatement.setString(1, username);
    //         ResultSet ResultSet = preparedStatement.executeQuery();
            
    //         // if(ResultSet.next()){ // we expect no more than 1 username returned
    //         while(ResultSet.next()){
    //             Message credentials = new Message(ResultSet.getInt("Message_id"), ResultSet.getString("username"), ResultSet.getString("password"));
    //             return credentials;
    //         }
    //     }catch(SQLException e){
    //         System.out.println(e.getMessage());
    //     }
    //     return null;
    // }    
}
