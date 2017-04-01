package com.example.ayodeji.myapplication.malta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MaltaContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 3;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), getContent(position), makeDetails(position), getImageUrl(position));
    }

    private static String getContent(int position) {
        String content = "";
        switch (position) {
            case 1:
                content = "http://www.mdx.edu.mt/";
                break;
            case 2:
                content = "https://www.facebook.com/mdxmalta/";
                break;
            case 3:
                content = "https://twitter.com/MiddlesexUni";
                break;
            default:
                content = "https://www.facebook.com/MiddlesexUniversity/";
                break;
        }
        return content;
    }

    private static String getImageUrl(int position) {
        String url = "";
        switch (position) {
            case 1:
                url = "dribble";
                break;
            case 2:
                url = "logofb";
                break;
            case 3:
                url = "logotwi";
                break;
            default:
                url = "dribble";
                break;
        }
        return url;
    }

    private static String makeDetails(int position) {
        /**StringBuilder builder = new StringBuilder("");
         builder.append("Details about Item: ").append(position);
         for (int i = 0; i < position; i++) {
         builder.append("\nMore details information here.");
         }**/
        String linkName = "";
        switch (position) {
            case 1:
                linkName = "Malta Website";
                break;
            case 2:
                linkName = "Malta Facebook Page";
                break;
            case 3:
                linkName = "Malta Twitter Page";
                break;
            default:
                linkName = "Website";
                break;
        }
        return linkName;
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;
        //****1
        public final String imageUrl;

        public DummyItem(String id, String content, String details, String imageUrl) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.imageUrl = imageUrl;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
