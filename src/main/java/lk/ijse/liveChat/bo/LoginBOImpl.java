package lk.ijse.liveChat.bo;

import lk.ijse.liveChat.dao.ClientDAOImpl;

import java.sql.SQLException;

public class LoginBOImpl {

    ClientDAOImpl dao = new ClientDAOImpl();
    public boolean exitClient(String name, String password) throws SQLException {
        return dao.exitClient(name,password);
    }
}
