package com.assignments.ecomerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private Integer status;
    private Boolean gender;
    private Date birthday;
}
