package Controller;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
// import io.javalin.http.Context;
import io.javalin.http.Handler;
// import java.util.Optional;
import Model.*; 
// import DAO.*;
import Service.*;

/** The endpoints you will need can be
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
        // localhost:8080 already loaded in as part of setup
        app.post("/login", SocialMediaController.login);
        app.post("messages", SocialMediaController.sendMsg);
        app.get("messages", SocialMediaController.rtvAllMsg);
        app.get("messages/{message_id}", SocialMediaController.rtvMsg);
        app.delete("messages/{message_id}", SocialMediaController.deleteMsg);
        app.patch("messages/{message_id}",SocialMediaController.editMsg);
        app.get("/accounts/{account_id}/messages",SocialMediaController.rtvAllMsg_a);
        return app;
    }

    public static Handler regiHandler = ctx -> {
        //#region
        /**
         * Implementation of handler for new user registration. JSON Body of the request for this endpoint/handler should have Account constructor args
        **/
        ObjectMapper om = new ObjectMapper();
        String jsonSt = ctx.body();
        // boolean success = false;
        // AccountDAO DAO = new AccountDAO();
        AccountService regiCall = new AccountService();
        Account new_user = om.readValue(jsonSt, Account.class);
        // if (new_user.getPassword().length() > 4 &&
        // best practice is to use a service class
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
    }; //#endregion
    public static Handler login = ctx -> {
        //#region  
        /**
         * Implementation of handler for user login. JSON request body should contain account info
        **/
        ObjectMapper om = new ObjectMapper();
        String jsonBody = ctx.body();
        Account loginInfo = om.readValue(jsonBody, Account.class);
        AccountService loginCheck = new AccountService();
        Account result_a = loginCheck.Login(loginInfo);
        if (result_a == null) {
            ctx.status(401);
        } else {
            ctx.status(200).json(result_a);
        }
        }; //#endregion
    public static Handler sendMsg = ctx -> {
        //#region
        /**
        * Implementation of handler for message creation. JSON request body should contain message info
        **/
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
    }; //#endregion
    public static Handler rtvAllMsg = ctx -> {
        //#region
        /**
         * Handler implementation for retrieving all messages
         */
        // ObjectMapper om = new ObjectMapper();
        MessageService getAll = new MessageService();
        ctx.status(200);
        ctx.json(getAll.getAllMessages());
    }; //#endregion
    public static Handler rtvMsg = ctx -> {
        //#region
        /**
         * Handler implementation for returning a message given a message_id in the URI. Responds w/ a JSON message
         */
        MessageService getMsg = new MessageService();
        ctx.status(200);
        // Optional<Message> result_m = Optional.ofNullable(getMsg.getMessageByID(Integer.parseInt(ctx.pathParam("message_id")))); // needs a bit more implementation to work
        Message result_m = getMsg.getMessageByID(Integer.parseInt(ctx.pathParam("message_id")));
        
        if (result_m != null) { 
        ctx.json(result_m);
        } else {
            ctx.json("");
        }
        // ctx.json(null); // you get a 500 error when this happens
    }; //#endregion
    public static Handler deleteMsg = ctx -> {
        //#region
        /**
         * Handler implementation for deleting a message given the message_id in the URI. Always returns a 200 with or without the now-deleted message to be idempotent.
         */
        MessageService dltMsg = new MessageService();
        ctx.status(200);
        int id_oi = Integer.parseInt(ctx.pathParam("message_id"));
        Message result_m = dltMsg.getMessageByID(id_oi);
        if (result_m != null) { 
            dltMsg.deleteMsg(id_oi);
            ctx.json(result_m);
        } else {
            ctx.json("");
        }
    };
    //#endregion
    public static Handler editMsg = ctx -> {
        //#region
        /**
         * Handler implementation for updating a message given the request body meets requirements. Error (400) otherwise
         */
        MessageService eMsg = new MessageService();
        // ObjectMapper om = new ObjectMapper();
        // System.out.println("initialized!");
        String jsonBody = ctx.body();
        // String umsg_text = om.readValue(jsonBody,  String.class); // check if "message_text:" is in the output
        // String umsg_text = ctx.body().substring(18); //"message_text:" is 18 characters long //ObjectMapper might need more args for this to work
        String umsg_text = jsonBody.substring(18,jsonBody.length()-3); //"message_text:" is 18 characters long, "}" 3 chars at end
        int id_oi = Integer.parseInt(ctx.pathParam("message_id"));
        // System.out.println("Going to services!");
        Message result_m = eMsg.editMsg(id_oi, umsg_text);
        // System.out.println("message updated!");
        if (result_m != null) { 
            ctx.json(result_m).status(200);
        } else {
            ctx.status(400);
        }
    };
    //#endregion
    public static Handler rtvAllMsg_a = ctx -> {
        //#region
        /**
         * Handler implementation for looking at a given user's messages. Uses account_id in the URI. Always 200 
         */
        MessageService rtvMsg = new MessageService();
        int id_oi = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.status(200);
        List<Message> result_ml = rtvMsg.getAllMsgByAcct(id_oi);
        if (result_ml == null){
            ctx.json(new ArrayList<Message>());  // an empty json fails the test
        } else {
            ctx.json(result_ml);
        }  
        // ctx.json(result_ml); //beware of null pointer exception
    };
    //#endregion
}
