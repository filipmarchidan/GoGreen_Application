package API.User;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserDBconnector implements RowMapper<AppUser> {
    //EXAMPLE OF SQL QUERY
    public static final String BASE_SQL //
            = "Select u.User_Id, u.User_Name, u.Encryted_Password From App_User u ";

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        //COLUMN LABELS ARE CONCEPTUAL I DON'T HAVE THE ACTUAL NAMES YET
        Long userId = rs.getLong("User_Id");
        String user = rs.getString("User_Name");
        String pass = rs.getString("Encryted_Password");

        return new AppUser(userId, user, pass);
    }
}
