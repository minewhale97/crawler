package com.minewhale.grabber.grabbercore.service.hospital;


import com.minewhale.grabber.grabbercore.service.pipeline.PipelineContainer;
import com.minewhale.grabber.grabbercore.service.external.dto.GrabberElement;
import com.minewhale.grabber.grabbercore.service.external.dto.GrabberResult;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.List;

@Component
public class HospitalGrabberService {

    public void executeService() {
        PipelineContainer<List<GrabberResult<GrabberElement>>> listPipeLineContainer = new PipelineContainer<>();
        Spider.create(new JiangsuProvinceHospitalGrabber())
                .addUrl("http://zhaobiao.jsph.org.cn/supplier/release/cgInfoList")
                .addPipeline(listPipeLineContainer)
                //开启5个线程抓取
                .thread(1)
                //启动爬虫
                .run();
    }

    public static void main(String[] args) {
        (new HospitalGrabberService()).executeService();
    }

}
