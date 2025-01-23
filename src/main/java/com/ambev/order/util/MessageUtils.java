package com.ambev.order.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;


import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtils {

    private static MessageSource messageSource;

    public MessageUtils(MessageSource messageSource) {
        MessageUtils.messageSource = messageSource;
    }

    public static String getMessage(String key, Object... args) {
        Locale locale = Locale.getDefault();
        return messageSource.getMessage(key, args, locale);
    }

    public static void setMessageSource(MessageSource source) {
        messageSource = source;
    }
}