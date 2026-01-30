package com.project.saas_billing_system.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {

    private String type;
    private String recipientEmail;
    private String subject;
    private String body;
    private String templateId;
    private Object templateData;
}
