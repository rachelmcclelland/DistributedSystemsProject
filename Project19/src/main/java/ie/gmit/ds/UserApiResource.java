package ie.gmit.ds;

import com.google.protobuf.ByteString;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserApiResource {

    private final Validator validator;

    public UserApiResource(Validator validator)
    {
        this.validator = validator;
    }


    @GET
    public Response getUsers() {
        return Response.ok(UserDB.getUsers()).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Integer id) {

        UserAccount user = UserDB.getUser(id);
        if (user != null)
            return Response.ok(user).build();
        else
            return Response.status(Status.NOT_FOUND).build();

    }

    @POST
    public Response createUser(UserAccount user) throws Exception
    {
        Set<ConstraintViolation<UserAccount>> violations = validator.validate(user);
        UserAccount u = UserDB.getUser(user.getUserID());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<UserAccount> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (u != null) {
            PasswordClient pClient = new PasswordClient("localhost", 50551);
            UserDB.updateUser(user.getUserID(), user);
            System.out.println("password: " +  user.getPassword());
            ByteString hashedPwd = pClient.hashPwd(user.getPassword(), user.getUserID());

            System.out.println("hashed password" + hashedPwd);

            return Response.created(new URI("/users/" + user.getUserID()))
                    .build();


        } else {
            UserDB.createUser(user.getUserID(), user);
            return Response.created(new URI("/users/" + user.getUserID()))
                    .build();

            //return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateUserById(@PathParam("id") Integer id, UserAccount user) {
        // validation
        Set<ConstraintViolation<UserAccount>> violations = validator.validate(user);
        UserAccount u = UserDB.getUser(user.getUserID());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<UserAccount> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (u != null) {
            user.setUserID(id);
            UserDB.updateUser(id, user);
            return Response.ok(user).build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Integer id) {
        UserAccount user = UserDB.getUser(id);
        if (user != null) {
            UserDB.deleteUser(id);
            return Response.ok().build();
        } else
            return Response.status(Status.NOT_FOUND).build();
    }
}
