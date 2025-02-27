package Service;

import Model.*;
// import Model.Account;
// import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import static org.mockito.ArgumentMatchers.booleanThat;

import java.util.List;
public class MessageService {
    private MessageDAO MessageDAO;
    /**
     * no-args constructor for creating a new MessageService with a new MessageDAO.
     */
    public MessageService(){
        MessageDAO = new MessageDAO();
    }
    /**
     * Constructor for a MessageService when a MessageDAO is provided.
     * This is used for when a mock MessageDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of MessageService independently of MessageDAO.
     * @param MessageDAO
     */
    public MessageService(MessageDAO MessageDAO){
        this.MessageDAO = MessageDAO;
    }
    /**
     *
     * @return all Messages
     */
    public List<Message> getAllMessages() {
        return this.MessageDAO.getAllMessages();
    }
    /**
     *
     * @param Message a Message object.
     * @return The persisted Message if the persistence is successful.
     */
    public Message Send(Message Message) { // creates a message
        int msg_length = Message.getMessage_text().length();
        AccountDAO accounts = new AccountDAO();
        boolean doesUserExist = accounts.getUserByID(Message.getPosted_by()) != null;
        if (msg_length > 255 || msg_length == 0 || !doesUserExist) {
            return null;
        } // message requirements
        return this.MessageDAO.insertMessage(Message); //insertMessage should return null if username is not unique
    }
    public Message getMessageByID(int id) {
        return this.MessageDAO.getMessageByID(id);
    }
    public Message deleteMsg(int id){
        Message result_m = this.MessageDAO.getMessageByID(id);
        if (result_m != null){
            this.MessageDAO.deleteMessage(id); // not actually a void method, but this if-statement should determine outcome instead
        } 
        return result_m;
    }
    public Message editMsg(int id, String text){
        Message msg_oi = MessageDAO.getMessageByID(id);
        boolean doesMsgExist = msg_oi != null;
        int msg_length = text.length();
        if (msg_length > 255 || msg_length == 0 || !doesMsgExist) {
            return null;
        } // message requirements
        boolean updateSuccessful = MessageDAO.updateMessage(id, text);
        if (updateSuccessful){
            msg_oi = MessageDAO.getMessageByID(id);
            return msg_oi;
        }

        return null;
    }
    public Message getAllMsgByAcct(int a_id){
        AccountDAO accounts = new AccountDAO();
        String username_oi = accounts.getUserByID(a_id);
        
        return this.MessageDAO.getAllMessages();
    }
    // public Message Login(Message Message) {
    //     Message credentials = this.MessageDAO.getMessageByUser(Message.getUsername());
    //     if (credentials == null || !Message.getPassword().equals(credentials.getPassword())) //we'd probably want to distinguish the errors, throw an exception? Return a dummy Message with specifics?
    //     {
    //         // System.out.println("login was" + Message.getPassword() + "but actually " + credentials.getPassword());
            
    //         // System.out.println(Message.getUsername() == credentials.getUsername());
    //         return null;
    //     }
    //     return credentials;
    // }
}
