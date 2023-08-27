package com.minewhale.grabber.grabbercore.service.external.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GrabberResult <T> {
    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 类目
     */
    private String category;

    /**
     * 链接
     */
    private String categoryUrl;

    /**
     * 元素
     */
    @Singular
    private List<T> elements;
}
