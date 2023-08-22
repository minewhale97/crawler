package com.minewhale.grabber.grabbercore.service.hospital;

import us.codecraft.webmagic.processor.PageProcessor;

public abstract class AbstractHospitalGrabber implements PageProcessor {
    abstract public String getHospitalName();
}
