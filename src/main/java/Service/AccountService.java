package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;
public class AccountService {
    
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
    private AccountDAO accountDAO;
    /**
     * no-args constructor for creating a new AccountService with a new AccountDAO.
     * There is no need to change this constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * There is no need to modify this constructor.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    /**
     * The accountDAO retrieves all accounts.
     *
     * @return all accounts
     */
    public List<Account> getAllAccounts() {
        return this.accountDAO.getAllAccounts();
    }
    /**
     *
     * @param account an account object.
     * @return The persisted account if the persistence is successful.
     */
    public Account RegisterNew(Account account) {
        if (account.getPassword().length() < 4 || account.getUsername().length() == 0) {
            return null;
        } // password and username requirements
        return this.accountDAO.insertAccount(account); //insertAccount should return null if username is not unique
    }
    public Account Login(Account account) {
        Account credentials = this.accountDAO.getAccountByUser(account.getUsername());
        if (credentials == null || !account.getPassword().equals(credentials.getPassword())) //we'd probably want to distinguish the errors, throw an exception? Return a dummy account with specifics?
        {
            // System.out.println("login was" + account.getPassword() + "but actually " + credentials.getPassword());
            
            // System.out.println(account.getUsername() == credentials.getUsername());
            return null;
        }
        return credentials;
    }
}
