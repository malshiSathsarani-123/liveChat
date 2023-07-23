package lk.ijse.liveChat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientDTO {
    private String userName;
    private String password;
    private String contact;
}
