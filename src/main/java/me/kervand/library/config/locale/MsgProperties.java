package me.kervand.library.config.locale;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MsgProperties {

    String[] messages;
    boolean single;
    public MsgProperties(String... messages) {
        this.messages = messages;
        single = messages.length == 1;
    }

    public Message get() {
        return new Message(Arrays.stream(messages).toList());
    }
}
