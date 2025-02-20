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
        Account result = regiCall.RegisterNew(new_user);
        // System.out.println(result);
        if (result != null) {
        ctx.status(200);
        ctx.json(result);
        }
        else {
        ctx.status(400);
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