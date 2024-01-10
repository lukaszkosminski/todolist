package com.todolist.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    private static Validator validator;
    private User user;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        user = new User();
        user.setUserName("testuser");
        user.setPassword("testpass");
        user.setEmail("wp@wp.pl");
        user.setRole("USER");
    }

    @Test
    @DisplayName("Should detect invalid username")
    public void shouldValidateUserNameSize() {
        // Given
        user.setUserName(""); // too short

        // When
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Then
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting("message").contains("Username is required", "Username must be between 3 and 10 characters");

        // Given
        user.setUserName("abcdefghijk"); // too long

        // When
        violations = validator.validate(user);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username must be between 3 and 10 characters");
    }

    @Test
    @DisplayName("Should accept valid username")
    public void shouldAcceptValidUsername() {
        // Given
        user.setUserName("abc"); // within the size range

        // When
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        violations = validator.validate(user);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should detect null username")
    public void shouldDetectNullUsername() {
        // Given
        user.setUserName(null); //

        // When
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        violations = validator.validate(user);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains("Username is required");
    }

    @Test
    @DisplayName("Should detect empty password")
    public void shouldDetectEmptyPassword() {

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword("");
        });

        // Then
        assertThat(exception.getMessage()).isEqualTo("Password cannot be null or empty");

    }

    @Test
    @DisplayName("Should detect null password")
    public void shouldDetectNullPassword() {
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            user.setPassword(null);
        });

        // Then
        assertThat(exception.getMessage()).isEqualTo("Password cannot be null or empty");

    }

    @Test
    @DisplayName("Should accept valid password")
    public void shouldAcceptValidPassword() {
        // Given
        user.setPassword("abcdef"); // valid password

        // When
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should return user name")
    public void shouldReturnUserName() {
        //When
        String username = user.getUsername();
        //Then
        assertThat(username).isEqualTo("testuser");
    }

    @Test
    @DisplayName("Should return true for account non expired")
    public void shouldReturnTrueForAccountNonExpired() {
        //When
        boolean accountNonExpired = user.isAccountNonExpired();
        //Then
        assertThat(accountNonExpired).isTrue();
    }

    @Test
    @DisplayName("Should return true for account non locked")
    public void shouldReturnTrueForAccountNonLocked() {
        //When
        boolean accountNonLocked = user.isAccountNonLocked();
        //Then
        assertThat(accountNonLocked).isTrue();
    }

    @Test
    @DisplayName("Should return true for credentials non expired")
    public void shouldReturnTrueForCredentialsNonExpired() {
        //When
        boolean credentialsNonExpired = user.isCredentialsNonExpired();
        //Then
        assertThat(credentialsNonExpired).isTrue();
    }

    @Test
    @DisplayName("Should return true for enabled account")
    public void shouldReturnTrueForEnabledAccount() {
        //When
        boolean userEnabled = user.isEnabled();
        //Then
        assertThat(userEnabled).isTrue();
    }

}