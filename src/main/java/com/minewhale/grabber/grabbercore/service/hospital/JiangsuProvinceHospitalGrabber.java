package com.minewhale.grabber.grabbercore.service.hospital;

import com.minewhale.grabber.grabbercore.service.GrabberConstant;
import com.minewhale.grabber.grabbercore.service.external.dto.GrabberElement;
import com.minewhale.grabber.grabbercore.service.external.dto.GrabberResult;
import com.minewhale.grabber.grabbercore.utils.DateUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.selector.Selectors;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * @author mineWen
 * @date 2023/8/14
 * @apiNote
 **/
public class JiangsuProvinceHospitalGrabber extends AbstractHospitalGrabber implements PageProcessor {
    final static String DETAIL_URL = "http://zhaobiao.jsph.org.cn/supplier/release/releaseCgInfoDetail?id=%s&projectXmfl=%s&qbStatus=1&title=";

    final static String HOSPITAL_NAME = "江苏省中医院";

    private String date = null;

    public JiangsuProvinceHospitalGrabber() {
    }

    public JiangsuProvinceHospitalGrabber(String date) {
        this.date = date;
    }

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        String dateStr = Objects.isNull(date)?
                DateUtils.getDateStr(LocalDateTime.now(ZoneId.of("+8"))):date;
        // 部分二：定义如何抽取页面信息，并保存下来
        Selectable listPath = page.getHtml().xpath("//*[@class='llist']");
        String category = listPath.xpath("//dt//font//text()").get();
        List<Selectable> nodes = listPath.selectList(Selectors.xpath("//dd")).nodes();
        List<GrabberElement> grabberElements = new ArrayList<>();
        GrabberResult<GrabberElement> grabberResult;
        for (Selectable listNode : nodes) {
            String eleDateStr = listNode.xpath("//span//text()").toString();
            if (!dateStr.equals(eleDateStr)) {
                break;
            }
            String eleTitle = listNode.xpath("//a//text()").get();

            Selectable xpath = listNode.xpath("//a");
            String urlSr = xpath.links().get();
            if (urlSr != null) {
                // javascript:cgInfoDetail('46608682e6c34fe3b59deb67e2c7ad40','');
                Matcher matcher = GrabberConstant.PATTERN_BETWEEN_SINGLE_QUOTES.matcher(urlSr);
                String[] args = new String[2];
                int index = 0;
                while (matcher.find()) {
                    String group = matcher.group();
                    args[index++] = group;
                }
                String formatUrl = String.format(DETAIL_URL, Objects.isNull(args[0]) ? "" : args[0], Objects.isNull(args[1]) ? "" : args[1]);
                grabberElements.add(new GrabberElement(eleTitle, formatUrl));
            }
        }
        if (grabberElements.isEmpty()) {
            return;
        }
        grabberResult = new GrabberResult<>();
        grabberResult.setCategory(category);
        grabberResult.setElements(grabberElements);
        ResultItems resultItems = page.getResultItems();
        List<GrabberResult<GrabberElement>> grabberResults = resultItems.get(GrabberConstant.GRABBER_RESULTS);
        if (Objects.isNull(grabberResults)) {
            grabberResults = new ArrayList<>();
            resultItems.put(GrabberConstant.GRABBER_RESULTS, grabberResults);
        }
        grabberResult.setSiteName(getHospitalName());
        grabberResults.add(grabberResult);
    }

    @Override
    public Site getSite() {
        // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
        return Site.me().setRetryTimes(3).setSleepTime(1000);
    }

    @Override
    public String getHospitalName() {
        return HOSPITAL_NAME;
    }
}
