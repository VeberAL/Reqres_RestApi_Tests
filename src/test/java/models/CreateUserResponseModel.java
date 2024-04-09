package models;

import lombok.Data;

@Data
public class CreateUserResponseModel {
    String name, job, email, createdAt;
    Integer id;
}
