package com.minewhale.grabber.grabbercore.service.pipeline;

import com.minewhale.grabber.grabbercore.service.GrabberConstant;
import lombok.Data;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Data
public class PipelineContainer<T> implements Pipeline {
    T result;
    @Override
    public void process(ResultItems resultItems, Task task) {
        result = resultItems.get(GrabberConstant.GRABBER_RESULTS);
    }
}
