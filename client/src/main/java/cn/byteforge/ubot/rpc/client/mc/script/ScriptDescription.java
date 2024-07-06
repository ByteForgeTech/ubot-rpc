package cn.byteforge.ubot.rpc.client.mc.script;

import lombok.Builder;
import lombok.Data;

import java.io.Reader;

/**
 * 附属脚本描述类
 * */
@Builder
@Data
public class ScriptDescription {

    /**
     * 脚本标识码
     * */
    private String code;

    /**
     * 脚本名称
     * */
    private String name;

    /**
     * 脚本功能描述
     * */
    private String description;

    /**
     * 脚本内容
     * */
    private transient String content;

}
