package lk.ijse.liveChat.bo;

import lk.ijse.liveChat.dao.ClientDAOImpl;

import java.sql.Blob;
import java.sql.SQLException;

public class CreateAccountBOImpl {

    ClientDAOImpl dao = new ClientDAOImpl();
    public boolean saveClient(String name, String password, String contact) throws SQLException {
        return dao.saveClient(name,password,contact);
    }

    public boolean saveImage(String message) throws SQLException {
        return dao.saveImg(message);
    }

    public Blob getImage(String userName) throws SQLException {
        return dao.getImg(userName);
    }
}
