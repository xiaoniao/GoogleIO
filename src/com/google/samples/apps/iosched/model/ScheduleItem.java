package com.google.samples.apps.iosched.model;

import com.google.samples.apps.iosched.provider.ScheduleContract;

/** ����ʵ���� **/
public class ScheduleItem implements Cloneable, Comparable<ScheduleItem> {

    // types:
    public static final int FREE                         = 0;   // a free chunk of time
    public static final int SESSION                      = 1;   // a session
    public static final int BREAK                        = 2;   // a break (lunch, breaks, after-hours party)

    // item type
    public int              type                         = FREE;

    // start and end time for this item
    public long             startTime                    = 0;
    public long             endTime                      = 0;

    // number of sessions available in this block (usually for free blocks)
    public int              numOfSessions                = 0;

    // session id
    public String           sessionId                    = "";

    // title and subtitle
    public String           title                        = "";
    public String           subtitle                     = "";

    // has feedback been given on this session?
    public boolean          hasGivenFeedback;

    // background image URL
    public String           backgroundImageUrl           = "";
    public int              backgroundColor              = 0;

    // flags
    public int              flags                        = 0;
    public static final int FLAG_HAS_LIVESTREAM          = 0x01;
    public static final int FLAG_NOT_REMOVABLE           = 0x02;
    public static final int FLAG_CONFLICTS_WITH_PREVIOUS = 0x04;

    public void setTypeFromBlockType(String blockType) {
        if (!ScheduleContract.Blocks.isValidBlockType(blockType) || ScheduleContract.Blocks.BLOCK_TYPE_FREE.equals(blockType)) {
            type = FREE;
        } else {
            type = BREAK;
        }
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            
            return new ScheduleItem();
        }
    }

    @Override
    public int compareTo(ScheduleItem another) {
        return this.startTime < another.startTime ? -1 : (this.startTime > another.startTime ? 1 : 0);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof ScheduleItem)) {
            return false;
        }
        ScheduleItem i = (ScheduleItem)o;
        return type == i.type && sessionId == i.sessionId && startTime == i.startTime && endTime == i.endTime;
    }

    @Override
    public String toString() {
        return String.format("[item type=%d, startTime=%d, endTime=%d, title=%s, subtitle=%s, flags=%d]", type,
                startTime, endTime, title, subtitle, flags);
    }
}
