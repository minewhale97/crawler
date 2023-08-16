package com.minewhale.grabber.grabbercore.service.external.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodyEntity {
    private String recipientType;
    private String address;
}

