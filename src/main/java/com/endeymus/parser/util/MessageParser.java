package com.endeymus.parser.util;

import com.endeymus.parser.entity.PostType;
import it.tdlight.jni.TdApi;

/**
 * @author Mark Shamray
 */
public interface MessageParser {
    Object parseContent(String content);
    PostType getTypeContent(String content);
}
