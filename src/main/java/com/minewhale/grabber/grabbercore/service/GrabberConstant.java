package com.minewhale.grabber.grabbercore.service;

import java.util.regex.Pattern;

public interface GrabberConstant {
    String EXTRACT_BETWEEN_SINGLE_QUOTES = "(?<=')(?:(?!')([^,]))+(?=')";


    Pattern PATTERN_BETWEEN_SINGLE_QUOTES = Pattern.compile(EXTRACT_BETWEEN_SINGLE_QUOTES);


    String GRABBER_RESULTS = "grabberResultS";
}
