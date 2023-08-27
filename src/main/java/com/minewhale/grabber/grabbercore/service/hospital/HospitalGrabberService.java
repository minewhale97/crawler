package com.minewhale.grabber.grabbercore.service.hospital;


import com.minewhale.grabber.grabbercore.service.pipeline.PipelineContainer;
import com.minewhale.grabber.grabbercore.service.external.dto.GrabberElement;
import com.minewhale.grabber.grabbercore.service.external.dto.GrabberResult;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import java.util.List;

@Component
public class HospitalGrabberService {

    public PipelineContainer<List<GrabberResult<GrabberElement>>> executeService(String date) {
        PipelineContainer<List<GrabberResult<GrabberElement>>> listPipeLineContainer = new PipelineContainer<>();
        Request request = new Request();
        // 请求头
        request.setUrl("http://zhaobiao.jsph.org.cn/supplier/release/cgInfoList");
        request.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        request.addHeader("Host","zhaobiao.jsph.org.cn");
        request.addHeader("Proxy-Connection","Proxy-Connection");
        request.addHeader("Upgrade-Insecure-Requests","1");
        request.addHeader("Cookie","JSESSIONID=482E8254A2D512154D040BA7D26887CA; uECufbblU9BmS=605O5wDmVq8wR41Vv6HMvClAvbTIhFmrKSif7_JL1JF.dILTK2StJ2.k8yFb5tAgSQ9xtbbnH9ymqg4NKTgX1f9q; uECufbblU9BmT=02p0GK8UoItgmvqMjoeP4d.aTEabPzQpcP.3j75BhG3rIDAObze2ttG23yaIcAILoAbQArd7qf5SVhS7sN.baj4tZxDRp.DBmkVHRVvJp1SJehY7uXtDphUL5GNJ08yAPZ6nFnIi74HEX3.47comdD2HgXEIsCAq80GHTApYHDFGRZrxP3g5NXNxj6.vl6k12Tud7FxDyEUhr0s2V1TSna4eUq6_WxVRFnEt85DA0_nc2TR4Ne_N6xxjnHfJldtaTsMfnysmuRmWH2e9pHUNa0VcswZIhMDXCCwC6qrxRIuxOKGknYI_Wyyzcr9FDgGthHj0_cl76RmMJjMdaKQK4lrBn31hVya.yqVdd8C19p4Q");
        request.addHeader("Cache-Control","3");
        request.addHeader("Accept-Language","zh-CN,zh;q=0.9");
        request.addHeader("Accept-Encoding","gzip, deflate");
        request.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");

        Spider.create(new JiangsuProvinceHospitalGrabber(date))
                .addRequest(request)
                //.addUrl("http://zhaobiao.jsph.org.cn/supplier/release/cgInfoList")
                .addPipeline(listPipeLineContainer)
                //开启5个线程抓取
                .thread(1)
                //启动爬虫
                .run();
        return listPipeLineContainer;
    }

    public static void main(String[] args) {
        PipelineContainer<List<GrabberResult<GrabberElement>>> listPipelineContainer = (new HospitalGrabberService()).executeService("2023-08-25");
        System.out.println();
    }

}
