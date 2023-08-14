package com.minewhale.grabber.grabbercore.service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author mineWen
 * @date 2023/8/14
 * @apiNote
 **/
public class JiangsuProvinceHospitalGrabber implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        Selectable listPath = page.getHtml().xpath("//*[@class='llist']");
        Selectable titlePath = listPath.xpath("//dt//font//text()");
        int index = 1;
        Selectable listDate = listPath.xpath("//dd[" + index + "]//span//text()");
        Selectable listContent = listPath.xpath("//dd[" + index + "]//a//text()");
        page.putField("title", page.getHtml().xpath("//div[@class='cg']//div[@class='wrap']//div[@class='lright']//dt[1]//font[1]/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        //page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        var property = System.getProperties().getProperty("user.dir");

        Spider.create(new JiangsuProvinceHospitalGrabber())
                .addUrl("http://zhaobiao.jsph.org.cn/supplier/release/cgInfoList")
                .addPipeline(new JsonFilePipeline("F:\\webmagic\\"))
                //开启5个线程抓取
                .thread(1)
                //启动爬虫
                .run();
    }
}
