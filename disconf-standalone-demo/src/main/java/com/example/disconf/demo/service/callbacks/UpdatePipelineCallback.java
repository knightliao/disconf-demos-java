package com.example.disconf.demo.service.callbacks;

import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.update.IDisconfUpdatePipeline;

/**
 * Created by knightliao on 16/3/22.
 */
@Service
public class UpdatePipelineCallback implements IDisconfUpdatePipeline {

    public void reloadDisconfFile(String key, String filePath) throws Exception {
        System.out.println(key + " : " + filePath);
    }

    public void reloadDisconfItem(String key, Object content) throws Exception {
        System.out.println(key + " : " + content);
    }
}
