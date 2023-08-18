package com.azeroth.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailDomain {
    private String address;
    private final String TITLE = "Change Password Request";
    private final String MESSAGE = "To reset your password, use this 5 digit code.";
}
