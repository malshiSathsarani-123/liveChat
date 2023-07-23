package lk.ijse.liveChat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Client {
    private String userName;
    private String password;
    private String contact;
}
