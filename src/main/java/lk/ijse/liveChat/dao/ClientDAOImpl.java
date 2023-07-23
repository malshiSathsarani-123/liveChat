package lk.ijse.liveChat.dao;

import lk.ijse.liveChat.util.CrudUtil;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAOImpl {

    public boolean exitClient(String name, String password) throws SQLException {
        String sql = "SELECT * FROM client WHERE UserName = ? && PassWord = ?";
        ResultSet resultSet = CrudUtil.execute(sql,name,password);
        if (resultSet.next()){
            return true;
        }
        return false;
    }

    public boolean saveClient(String name, String password, String contact) throws SQLException {
        String sql = "INSERT INTO client (UserName,Password,contact) VALUES (?,?,?)";
        return CrudUtil.execute(sql,name,password,contact);
    }

    public boolean saveImg(String message) throws SQLException {
        String sql = "INSERT INTO client (UserName,Password,photo) VALUES (?,?,?)";
        return CrudUtil.execute(sql,"name",1234,message);
    }

    public Blob getImg(String userName) throws SQLException {
        String sql = "SELECT photo FROM client WHERE UserName = ? ";
        ResultSet resultSet = CrudUtil.execute(sql,userName);
        if (resultSet.next()){
            return resultSet.getBlob(1);
        }
        return null;
    }
}
