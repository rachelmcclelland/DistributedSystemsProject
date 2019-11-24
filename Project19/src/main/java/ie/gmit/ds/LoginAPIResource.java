package ie.gmit.ds;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Set;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginAPIResource {

    private final Validator validator;

    public LoginAPIResource(Validator validator) {

            this.validator = validator;
    }

    @POST
    public Response Login(UserAccount user)
    {
        Set<ConstraintViolation<UserAccount>> violations = validator.validate(user);
        UserAccount user1;

        user1 = UserDB.getUser(user.getUserID());


        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<UserAccount> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }

        boolean validatePass = UserDB.login(user.getUserID(), user);

        if(validatePass)
        {
            return Response.ok("Password on account is correct").build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).entity("Incorrect password").build();
        }

    }

}
