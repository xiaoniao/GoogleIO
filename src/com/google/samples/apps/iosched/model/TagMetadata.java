package com.google.samples.apps.iosched.model;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.google.samples.apps.iosched.Config;
import com.google.samples.apps.iosched.provider.ScheduleContract;

import java.util.*;

public class TagMetadata {

    HashMap<String, ArrayList<Tag>> mTagsInCategory = new HashMap<String, ArrayList<Tag>>(); // ��ͬ���͵�tag����

    HashMap<String, Tag>            mTagsById       = new HashMap<String, Tag>();            // ��ͬid��tag����

    /** ʵ����CursorLoader **/
    public static CursorLoader createCursorLoader(Context context) {
        return new CursorLoader(context, ScheduleContract.Tags.CONTENT_URI, TagsQuery.PROJECTION, null, null, null);
    }

    /** ���췽�� **/
    public TagMetadata(Cursor cursor) {
        
        /** ѭ������Cursor **/
        while (cursor.moveToNext()) {
            
            /** ��ȡCursor�е�ֵ **/
            String tagId = cursor.getString(TagsQuery.TAG_ID);
            String tagName = cursor.getString(TagsQuery.TAG_NAME);
            String tagCategory =  cursor.getString(TagsQuery.TAG_CATEGORY);
            int tagOrderCategory = cursor.getInt(TagsQuery.TAG_ORDER_IN_CATEGORY);
            String tagAbstract =  cursor.getString(TagsQuery.TAG_ABSTRACT);
            int tagColor = cursor.getInt(TagsQuery.TAG_COLOR);
            
            /** ʵ����Tag **/
            Tag tag = new Tag(tagId, tagName, tagCategory, tagOrderCategory, tagAbstract, tagColor);
             
            mTagsById.put(tag.getId(), tag);
            
            if (!mTagsInCategory.containsKey(tag.getCategory())) {
                mTagsInCategory.put(tag.getCategory(), new ArrayList<Tag>());
            }
            
            mTagsInCategory.get(tag.getCategory()).add(tag);
        }

        for (ArrayList<Tag> list : mTagsInCategory.values()) {
            Collections.sort(list);                            // ����
        }
    }

    /** ����id�����tag **/
    public Tag getTag(String tagId) {
        return mTagsById.containsKey(tagId) ? mTagsById.get(tagId) : null;
    }

    /** ����tag���ͣ����tag���� **/
    public List<Tag> getTagsInCategory(String category) {
        return mTagsInCategory.containsKey(category) ? Collections.unmodifiableList(mTagsInCategory.get(category)) : null;
    }

    /** Given the set of tags on a session, returns its group label. */
    public Tag getSessionGroupTag(String[] sessionTags) {
        
        int bestOrder = Integer.MAX_VALUE;
        
        Tag bestTag = null;
        for (String tagId : sessionTags) {
            Tag tag = getTag(tagId);
            if (tag != null && Config.Tags.SESSION_GROUPING_TAG_CATEGORY.equals(tag.getCategory()) && tag.getOrderInCategory() < bestOrder) {
                bestOrder = tag.getOrderInCategory();
                bestTag = tag;
            }
        }
        return bestTag;
    }

    /** �Զ���Ƚ� **/
    public static Comparator<Tag> TAG_DISPLAY_ORDER_COMPARATOR = new Comparator<Tag>() {
        
       @Override
       public int compare(Tag tag, Tag tag2) {
           
           if (!TextUtils.equals(tag.getCategory(),tag2.getCategory())) {
               
               // ëë�߰�����ô��Config����Tag
               return Config.Tags.CATEGORY_DISPLAY_ORDERS.get(tag.getCategory())- Config.Tags.CATEGORY_DISPLAY_ORDERS.get(tag2.getCategory());
          
           } else if (tag.getOrderInCategory() != tag2.getOrderInCategory()) {
               
               return tag.getOrderInCategory()- tag2.getOrderInCategory();
           }

           return tag.getName().compareTo(tag2.getName());
       }
   };
   
    /** Tag��ѯ���� **/
    private interface TagsQuery {
        int      _TOKEN                = 0x1;

        String[] PROJECTION            = {BaseColumns._ID, ScheduleContract.Tags.TAG_ID,
                                               ScheduleContract.Tags.TAG_NAME, ScheduleContract.Tags.TAG_CATEGORY,
                                               ScheduleContract.Tags.TAG_ORDER_IN_CATEGORY,
                                               ScheduleContract.Tags.TAG_ABSTRACT, ScheduleContract.Tags.TAG_COLOR};

        int      _ID                   = 0;
        int      TAG_ID                = 1;
        int      TAG_NAME              = 2;
        int      TAG_CATEGORY          = 3;
        int      TAG_ORDER_IN_CATEGORY = 4;
        int      TAG_ABSTRACT          = 5;
        int      TAG_COLOR             = 6;
    }

    /** Tagʵ���� **/
    static public class Tag implements Comparable<Tag> {

        private String mId;
        private String mName;
        private String mCategory;
        private int    mOrderInCategory;
        private String mAbstract;
        private int    mColor;

        public Tag(String id, String name, String category, int orderInCategory, String _abstract, int color) {
            mId = id;
            mName = name;
            mCategory = category;
            mOrderInCategory = orderInCategory;
            mAbstract = _abstract;
            mColor = color;
        }

        public String getId() {
            return mId;
        }

        public String getName() {
            return mName;
        }

        public String getCategory() {
            return mCategory;
        }

        public int getOrderInCategory() {
            return mOrderInCategory;
        }

        public String getAbstract() {
            return mAbstract;
        }

        public int getColor() {
            return mColor;
        }

        /** �Զ���Ƚ� **/
        @Override
        public int compareTo(Tag another) {
            return mOrderInCategory - another.mOrderInCategory;
        }
    }
}
