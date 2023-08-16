package com.minewhale.grabber.grabbercore.service.external;

import org.springframework.stereotype.Service;
@Service
public class GetCode {
    public String getCode() {
        String str = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // no zero
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * str.length());
            code.append(str.charAt(index));
        }
        return code.toString();
    }
}

