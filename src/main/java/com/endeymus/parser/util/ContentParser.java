package com.endeymus.parser.util;

import com.endeymus.parser.entity.PostType;
import it.tdlight.jni.TdApi;

/**
 * @author Mark Shamray
 */
public interface ContentParser {
    String getContent(TdApi.MessageContent content);
    PostType getType(TdApi.MessageContent content);
}
