package pers.lichen.weixin.mp.controller;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.web.bind.annotation.*;
import pers.lichen.weixin.mp.config.WxMpConfiguration;

import java.util.List;

@RestController
@RequestMapping("/wx/templateMsg/{appid}")
public class WxTemplateMessageController {

    /**
     * @Description: 发送模板消息
     * @Param: [appid, toUser, templateId, url, data]
     * @return: void
     * @Author: lichen
     * @Date: 2018-12-22 15:39
     */
    @PostMapping(value = "/send")
    public void sendTempMessage(@PathVariable String appid, @RequestBody WxMpTemplateMessage data) throws WxErrorException {

        WxMpTemplateMessage wxMpTemplateMessage = data;
        WxMpConfiguration.getMpServices().get(appid).getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
    }
}
