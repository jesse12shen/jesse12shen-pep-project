package Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import Model.*; 
// import DAO.*;
import Service.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);
        app.post("/register", SocialMediaController.regiHandler);
        // localhost:8080 might already be loaded in
        app.post("/login", SocialMediaController.login);
        return app;
    }
    public static Handler regiHandler = ctx -> {
        ObjectMapper om = new ObjectMapper();
        String jsonSt = ctx.body();
        // boolean success = false;
        // AccountDAO DAO = new AccountDAO();
        AccountService regiCall = new AccountService();
        Account new_user = om.readValue(jsonSt, Account.class);
        // if (new_user.getPassword().length() > 4 &&
        
        // ) {
        //     success = true;
        // }
        Account result_a = regiCall.RegisterNew(new_user); // RegisterNew either returns a null object if unsuccessful, or Account added if successful
        System.out.println("this account was created:" + result_a);
        if (result_a != null) {
        ctx.status(200);
        ctx.json(result_a);
        }
        else {
        ctx.status(400);
        }
    };
    public static Handler login = ctx -> {
        ObjectMapper om = new ObjectMapper();
        String jsonBody = ctx.body();
        Account loginInfo = om.readValue(jsonBody, Account.class);
        AccountService loginCheck = new AccountService();
        Account result_a = loginCheck.Login(loginInfo);
        if (result_a == null) {
            ctx.status(401);
        } else {
            ctx.status(200);
            ctx.json(result_a);
        }
    };
    public static Handler send_msg = ctx -> {
        ObjectMapper om = new ObjectMapper();
        String jsonBody = ctx.body();
        Message msg_text = om.readValue(jsonBody, Message.class);
        MessageService sendCheck = new MessageService();
        Message result_m = sendCheck.Send(msg_text);
        if (result_m == null) {
            ctx.status(400);
        }
        else {
            ctx.status(200);
            ctx.json(result_m);
        }
    };
    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}