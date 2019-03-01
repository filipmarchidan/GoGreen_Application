package API.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService  implements UserDetailsService {

    @Autowired
    private AppDAO appDAO;

    @Autowired
    private AppPrivilegeDAO appPrivilegeDAO;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        AppUser appUser = this.appDAO.findUserAccount(user);

        if (appUser == null) {
            System.out.println("User not found! " + user);
            throw new UsernameNotFoundException("User " + user + " was not found in the database");
        }
        System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.appPrivilegeDAO.getRoleNames(appUser.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), //
                appUser.getPass(), grantList);

        return userDetails;
    }

}
