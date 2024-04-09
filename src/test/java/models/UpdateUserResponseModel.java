package models;

import lombok.Data;

@Data
public class UpdateUserResponseModel {
    String name, job, email, updatedAt;
    Integer id;
}
