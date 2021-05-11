package com.endeymus.parser.model;

import com.endeymus.parser.entity.Channel;
import com.endeymus.parser.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Содержит информацию о канале и его последнем сообщении
 * @author Mark Shamray
 */
@Data
@AllArgsConstructor
public class ChannelPostInfo {
    private Channel channel;
    private Posts lastPost;
}
