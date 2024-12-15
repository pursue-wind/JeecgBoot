package org.jeecg.common.util.encryption;


import lombok.Data;

/**
 * @Description: EncryptedString
 * @author: jeecg-boot
 */
@Data
public class  EncryptedString {

    /**
     * 长度为16个字符
     */
    public static  String key = "qwer1234567890za";

    /**
     * 长度为16个字符
     */
    public static  String iv  = "aiop1234567890as";
}
