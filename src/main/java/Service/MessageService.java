package Service;

import Model.Account;
import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import static org.mockito.ArgumentMatchers.booleanThat;

import java.util.List;
public class MessageService {
    
    /**
     * The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
     * persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
     * SQL: programming tasks like checking that the input is valid, conducting additional security checks, or saving the
     * actions undertaken by the API to a logging file.
     *
     * It's perfectly normal to have Service methods that only contain a single line that calls a DAO method. An
     * application that follows best practices will often have unnecessary code, but this makes the code more
     * readable and maintainable in the long run!
     */
    private MessageDAO MessageDAO;
    /**
     * no-args constructor for creating a new MessageService with a new MessageDAO.
     * There is no need to change this constructor.
     */
    public MessageService(){
        MessageDAO = new MessageDAO();
    }
    /**
     * Constructor for a MessageService when a MessageDAO is provided.
     * This is used for when a mock MessageDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of MessageService independently of MessageDAO.
     * There is no need to modify this constructor.
     * @param MessageDAO
     */
    public MessageService(MessageDAO MessageDAO){
        this.MessageDAO = MessageDAO;
    }
    /**
     * The MessageDAO retrieves all Messages.
     *
     * @return all Messages
     */
    public List<Message> getAllMessages() {
        return this.MessageDAO.getAllMessages();
    }
    /**
     *
     * @param Message an Message object.
     * @return The persisted Message if the persistence is successful.
     */
    public Message Send(Message Message) { // creates a message
        int msg_length = Message.getMessage_text().length();
        AccountDAO accounts = new AccountDAO();
        boolean doesUserExist = accounts.getAccountByUser(Message.get)
        if (msg_length <= 255 || msg_length == 0 || ) {
            return null;
        } // password and username requirements
        return this.MessageDAO.insertMessage(Message); //insertMessage should return null if username is not unique
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
