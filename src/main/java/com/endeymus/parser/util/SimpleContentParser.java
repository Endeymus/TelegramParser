package com.endeymus.parser.util;

import com.endeymus.parser.entity.PostType;
import it.tdlight.jni.TdApi;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Mark Shamray
 */
@Component("contentParser")
public class SimpleContentParser implements ContentParser {


    @Override
    public String getContent(TdApi.MessageContent content) {

        String result = null;

        switch (getType(content)){
            case AUDIO -> {
                TdApi.MessageAudio audio = (TdApi.MessageAudio) content;
                result = audio.caption.text;
            }
            case DOCUMENT -> {
                TdApi.MessageDocument document = (TdApi.MessageDocument) content;
                result = document.caption.text;
            }
            case ANIMATION -> {
                TdApi.MessageAnimation animation = (TdApi.MessageAnimation) content;
                result = animation.caption.text;
            }
            case CONTACT -> {
                TdApi.MessageContact contact = (TdApi.MessageContact) content;
                result = contact.contact.phoneNumber;
            }

            case PHOTO -> {
                TdApi.MessagePhoto photo = (TdApi.MessagePhoto) content;
                result = photo.caption.text;
            }
            case TEXT -> {
                TdApi.MessageText text = (TdApi.MessageText) content;
                result = text.text.text;
            }
            case VIDEO -> {
                TdApi.MessageVideo video = (TdApi.MessageVideo) content;
                result = video.caption.text;
            }
            case VOICE_NOTE -> {
                TdApi.MessageVoiceNote voiceNote = (TdApi.MessageVoiceNote) content;
                result = Arrays.toString(voiceNote.voiceNote.waveform);
            }

            case VIDEO_NOTE -> {
                TdApi.MessageVideoNote videoNote = (TdApi.MessageVideoNote) content;
                result = Arrays.toString(videoNote.videoNote.minithumbnail.data);
            }
            case STICKER -> {
                TdApi.MessageSticker sticker = (TdApi.MessageSticker) content;
                result = sticker.sticker.emoji;
            }
            case ANOTHER -> result = "Channel system information";
        }
        return result;
    }

    @Override
    public PostType getType(TdApi.MessageContent content) {
        PostType type;

        switch (content.getConstructor()) {
            case TdApi.MessageAudio.CONSTRUCTOR -> type = PostType.AUDIO;
            case TdApi.MessageDocument.CONSTRUCTOR -> type = PostType.DOCUMENT;
            case TdApi.MessageAnimation.CONSTRUCTOR -> type = PostType.ANIMATION;
            case TdApi.MessageContact.CONSTRUCTOR -> type = PostType.CONTACT;
            case TdApi.MessagePhoto.CONSTRUCTOR -> type = PostType.PHOTO;
            case TdApi.MessageText.CONSTRUCTOR -> type = PostType.TEXT;
            case TdApi.MessageVideo.CONSTRUCTOR -> type = PostType.VIDEO;
            case TdApi.MessageVoiceNote.CONSTRUCTOR -> type = PostType.VOICE_NOTE;
            case TdApi.MessageVideoNote.CONSTRUCTOR -> type = PostType.VIDEO_NOTE;
            case TdApi.MessageSticker.CONSTRUCTOR -> type = PostType.STICKER;
            default -> type = PostType.ANOTHER;
        }

        return type;
    }


}
