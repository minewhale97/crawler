package com.minewhale.grabber.grabbercore.service;

import com.minewhale.grabber.grabbercore.service.external.MailService;
import com.minewhale.grabber.grabbercore.utils.DateUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.selector.Selectors;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author mineWen
 * @date 2023/8/14
 * @apiNote
 **/
public class JiangsuProvinceHospitalGrabber implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Resource
    private MailService mailService;

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        String dateStr = DateUtils.getDateStr(LocalDateTime.now(ZoneId.of("+8")));
        // 部分二：定义如何抽取页面信息，并保存下来
        Selectable listPath = page.getHtml().xpath("//*[@class='llist']");
        Selectable titlePath = listPath.xpath("//dt//font//text()");
        int index = 1;
        List<Selectable> nodes = listPath.selectList(Selectors.xpath("//dd")).nodes();
        nodes.forEach(listNode -> {

        });
        while (index > 0) {
            Selectable listDate = listPath.xpath("//dd[" + index + "]//span//text()");
            if (listDate.match()) {

            }
            Selectable selectable = listPath.selectList(Selectors.xpath("//dd"));
            Selectable listContent = listPath.xpath("//dd[" + index + "]//a//text()");
        }
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
