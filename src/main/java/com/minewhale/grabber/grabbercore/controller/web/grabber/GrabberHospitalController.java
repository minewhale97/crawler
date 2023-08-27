package com.minewhale.grabber.grabbercore.controller.web.grabber;


import com.minewhale.grabber.grabbercore.service.GrabberConstant;
import com.minewhale.grabber.grabbercore.service.external.dto.GrabberElement;
import com.minewhale.grabber.grabbercore.service.external.dto.GrabberResult;
import com.minewhale.grabber.grabbercore.service.hospital.HospitalGrabberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class GrabberHospitalController {

    @Resource
    private HospitalGrabberService hospitalGrabberService;
    @GetMapping("/grabber/hospital")
    public String getLatestGrabber(Model model) {
        List<GrabberResult<GrabberElement>> result = hospitalGrabberService.executeService("2023-08-25").getResult();
        model.addAttribute(GrabberConstant.GRABBER_RESULTS, result);
        return "exchange-grabber.html";
    }
}
