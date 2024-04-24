package me.kervand.library.config;

import me.kervand.library.config.locale.MsgKey;
import me.kervand.library.config.locale.MsgProperties;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LocaleConfig<E extends Enum<E> & MsgKey> extends BaseConfig {

    private final Map<E, MsgProperties> messages = new HashMap<>();

    public LocaleConfig(File file) {
        super(file);
    }

    public MsgProperties getMessage(E enumValue) {
        if (messages.containsKey(enumValue)) {
            return messages.get(enumValue);
        } else {

            if (getHandle().contains(enumValue.name().toLowerCase())) {
                if (getHandle().isList(enumValue.name().toLowerCase())) {
                    MsgProperties msgProperties = new MsgProperties(getHandle().getStringList(enumValue.name().toLowerCase())
                            .toArray(new String[0]));
                    messages.put(enumValue, msgProperties);
                } else {
                    MsgProperties msgProperties = new MsgProperties(getHandle().getString(enumValue.name().toLowerCase()));
                    messages.put(enumValue, msgProperties);
                }
            } else {
                MsgProperties msgProperties = enumValue.getProperties();
                if (msgProperties.isSingle()) {
                    getHandle().set(enumValue.name().toLowerCase(), msgProperties.getMessages()[0]);
                } else {
                    getHandle().set(enumValue.name().toLowerCase(), Arrays.stream(msgProperties.getMessages()).toList());
                }
                messages.put(enumValue, msgProperties);
                save();
            }
        }
        return messages.get(enumValue);
    }

}
