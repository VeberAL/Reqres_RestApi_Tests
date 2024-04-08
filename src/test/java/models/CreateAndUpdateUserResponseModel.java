package models;

import lombok.Data;

@Data
public class CreateAndUpdateUserResponseModel {
    String name, job, email, createdAt;
    Integer id;
}
