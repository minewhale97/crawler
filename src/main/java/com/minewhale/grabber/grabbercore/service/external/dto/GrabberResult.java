package com.minewhale.grabber.grabbercore.service.external.dto;

import lombok.Data;

import java.util.List;

@Data
public class GrabberResult {
    private String category;

    private List<GrabberElement> elements;
}
