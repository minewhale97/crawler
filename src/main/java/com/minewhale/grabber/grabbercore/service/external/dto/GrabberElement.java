package com.minewhale.grabber.grabbercore.service.external.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrabberElement {
    private String title;

    private String link;
}
