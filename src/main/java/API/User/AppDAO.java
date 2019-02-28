package API.User;

/*
    DAO = DATA ACCESS OBJECT
    with this method we can use the queries to add to the database
    //this class checks for the user in the database
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
@Transactional
public class AppDAO extends JdbcDaoSupport{
    @Autowired
    public AppDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public AppUser findUserAccount(String userName) {
        // Select .. from App_User u Where u.User_Name = ?
        String sql = AppUserDBconnector.BASE_SQL + " where u.User_Name = ? ";

        Object[] params = new Object[] { userName };
        AppUserDBconnector mapper = new AppUserDBconnector();
        try {
            AppUser userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
