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

    HashMap<String, ArrayList<Tag>> mTagsInCategory = new HashMap<String, ArrayList<Tag>>(); // 不同类型的tag集合

    HashMap<String, Tag>            mTagsById       = new HashMap<String, Tag>();            // 不同id的tag集合

    /** 实例化CursorLoader **/
    public static CursorLoader createCursorLoader(Context context) {
        return new CursorLoader(context, ScheduleContract.Tags.CONTENT_URI, TagsQuery.PROJECTION, null, null, null);
    }

    /** 构造方法 **/
    public TagMetadata(Cursor cursor) {
        
        /** 循环遍历Cursor **/
        while (cursor.moveToNext()) {
            
            /** 读取Cursor中的值 **/
            String tagId = cursor.getString(TagsQuery.TAG_ID);
            String tagName = cursor.getString(TagsQuery.TAG_NAME);
            String tagCategory =  cursor.getString(TagsQuery.TAG_CATEGORY);
            int tagOrderCategory = cursor.getInt(TagsQuery.TAG_ORDER_IN_CATEGORY);
            String tagAbstract =  cursor.getString(TagsQuery.TAG_ABSTRACT);
            int tagColor = cursor.getInt(TagsQuery.TAG_COLOR);
            
            /** 实例化Tag **/
            Tag tag = new Tag(tagId, tagName, tagCategory, tagOrderCategory, tagAbstract, tagColor);
             
            mTagsById.put(tag.getId(), tag);
            
            if (!mTagsInCategory.containsKey(tag.getCategory())) {
                mTagsInCategory.put(tag.getCategory(), new ArrayList<Tag>());
            }
            
            mTagsInCategory.get(tag.getCategory()).add(tag);
        }

        for (ArrayList<Tag> list : mTagsInCategory.values()) {
            Collections.sort(list);                            // 排序
        }
    }

    /** 根据id，获得tag **/
    public Tag getTag(String tagId) {
        return mTagsById.containsKey(tagId) ? mTagsById.get(tagId) : null;
    }

    /** 根据tag类型，获得tag集合 **/
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

    /** 自定义比较 **/
    public static Comparator<Tag> TAG_DISPLAY_ORDER_COMPARATOR = new Comparator<Tag>() {
        
       @Override
       public int compare(Tag tag, Tag tag2) {
           
           if (!TextUtils.equals(tag.getCategory(),tag2.getCategory())) {
               
               // 毛毛线啊，怎么在Config里面Tag
               return Config.Tags.CATEGORY_DISPLAY_ORDERS.get(tag.getCategory())- Config.Tags.CATEGORY_DISPLAY_ORDERS.get(tag2.getCategory());
          
           } else if (tag.getOrderInCategory() != tag2.getOrderInCategory()) {
               
               return tag.getOrderInCategory()- tag2.getOrderInCategory();
           }

           return tag.getName().compareTo(tag2.getName());
       }
   };
   
    /** Tag查询参数 **/
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

    /** Tag实体类 **/
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

        /** 自定义比较 **/
        @Override
        public int compareTo(Tag another) {
            return mOrderInCategory - another.mOrderInCategory;
        }
    }
}
