package com.theagent.tinyLobby;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class Names {
    
    final static String EXIT_ITEM_NAME = "Exit Server";

    /**
     * Extracts the text out of a Component
     *
     * @param component Component containing Text
     * @return String of Component's text
     */
    public static String TextComponentToString(Component component) {
        // extract text from component
        String text = PlainTextComponentSerializer.plainText().serialize(component);

        // replace [ ] on both ends of the String
        text = text.replace("[", "");
        text = text.replace("]", "");

        return text;
    }

}
