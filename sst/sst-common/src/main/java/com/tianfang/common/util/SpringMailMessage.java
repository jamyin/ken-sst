package com.tianfang.common.util;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Peter on 14-9-17.
 */
public class SpringMailMessage {
    @Getter
    @Setter
    private String text;
    @Getter
    @Setter
    private String subject;
    @Getter
    @Setter
    private String from;
    @Getter
    @Setter
    private String to;

}
