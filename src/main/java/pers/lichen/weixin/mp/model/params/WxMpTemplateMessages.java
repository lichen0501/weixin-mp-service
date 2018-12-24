package pers.lichen.weixin.mp.model.params;

import lombok.Builder;
import lombok.Data;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Data
public class WxMpTemplateMessages extends WxMpTemplateMessage {
    /**
     * 批量发送用户
     */
    private String[] toUsers;

    public WxMpTemplateMessage setSuper(){
        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder()
                .url(this.getUrl())
                .templateId(this.getTemplateId())
                .data(this.getData())
                .miniProgram(this.getMiniProgram()).build();
        return wxMpTemplateMessage;
    }
}
