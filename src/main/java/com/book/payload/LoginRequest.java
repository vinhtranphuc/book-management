package com.book.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
    
    public boolean isRememberMe;

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
		return isRememberMe;
	}

	public void setRememberMe(boolean isRememberMe) {
		this.isRememberMe = isRememberMe;
	}
}