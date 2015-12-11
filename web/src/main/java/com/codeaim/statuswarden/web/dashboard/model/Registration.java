package com.codeaim.statuswarden.web.dashboard.model;

import com.codeaim.statuswarden.common.constraint.FieldMatch;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
    @FieldMatch(
        first = "password",
        second = "passwordMatch",
        errorMessage = "The password fields must match",
        message = "The password fields must match",
        groups = {Registration.ValidationGroup.class})
})
public class Registration
{
    @NotNull
    @Email
    private String email;

    @Size(min = 8, max = 25)
    @NotEmpty(groups={ValidationGroup.class}, message="Passwords must not be blank")
    private String password;

    @Size(min = 8, max = 25)
    @NotEmpty(groups={ValidationGroup.class}, message="Passwords must not be blank")
    private String passwordMatch;

    @NotNull
    private String name;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPasswordMatch()
    {
        return passwordMatch;
    }

    public void setPasswordMatch(String passwordMatch)
    {
        this.passwordMatch = passwordMatch;
    }

    public interface ValidationGroup {}
}
