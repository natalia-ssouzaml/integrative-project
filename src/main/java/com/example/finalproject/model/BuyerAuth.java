package com.example.finalproject.model;
import com.sun.istack.NotNull;
import lombok.*;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerAuth {

    private String name;
    @NotNull
    @Email
    @Length(min = 5, max = 50)
    private String username;

    @NotNull @Length(min = 8)
    private String password;
}