package com.google.samples.apps.iosched.provider;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.text.format.DateUtils;

import com.google.samples.apps.iosched.util.AccountUtils;
import com.google.samples.apps.iosched.util.ParserUtils;

import java.util.List;

public class ScheduleContract {
    
    private ScheduleContract() {}

    public static final String QUERY_PARAMETER_DISTINCT       = "distinct";
    public static final String OVERRIDE_ACCOUNTNAME_PARAMETER = "overrideAccount";

    /**************************************** 列名 ********************************************/

    public interface SyncColumns {
        String UPDATED = "updated";
    }

    interface BlocksColumns {
        String BLOCK_ID       = "block_id";
        String BLOCK_TITLE    = "block_title";
        String BLOCK_START    = "block_start";
        String BLOCK_END      = "block_end";
        String BLOCK_TYPE     = "block_type";
        String BLOCK_SUBTITLE = "block_subtitle";
    }

    interface TagsColumns {
        String TAG_ID                = "tag_id";
        String TAG_CATEGORY          = "tag_category";
        String TAG_NAME              = "tag_name";
        String TAG_ORDER_IN_CATEGORY = "tag_order_in_category";
        String TAG_COLOR             = "tag_color";
        String TAG_ABSTRACT          = "tag_abstract";
    }

    interface RoomsColumns {
        String ROOM_ID    = "room_id";
        String ROOM_NAME  = "room_name";
        String ROOM_FLOOR = "room_floor";
    }

    interface MyScheduleColumns {
        String SESSION_ID               = SessionsColumns.SESSION_ID;
        String MY_SCHEDULE_ACCOUNT_NAME = "account_name";
        String MY_SCHEDULE_IN_SCHEDULE  = "in_schedule";
        String MY_SCHEDULE_DIRTY_FLAG   = "dirty";
    }

    interface SessionsColumns {
        String SESSION_ID              = "session_id";
        String SESSION_LEVEL           = "session_level";
        String SESSION_START           = "session_start";
        String SESSION_END             = "session_end";
        String SESSION_TITLE           = "session_title";
        String SESSION_ABSTRACT        = "session_abstract";
        String SESSION_REQUIREMENTS    = "session_requirements";
        String SESSION_KEYWORDS        = "session_keywords";
        String SESSION_HASHTAG         = "session_hashtag";
        String SESSION_URL             = "session_url";
        String SESSION_YOUTUBE_URL     = "session_youtube_url";
        String SESSION_PDF_URL         = "session_pdf_url";
        String SESSION_NOTES_URL       = "session_notes_url";
        String SESSION_IN_MY_SCHEDULE  = "session_in_my_schedule";
        String SESSION_CAL_EVENT_ID    = "session_cal_event_id";
        String SESSION_LIVESTREAM_URL  = "session_livestream_url";
        String SESSION_MODERATOR_URL   = "session_moderator_url";
        String SESSION_TAGS            = "session_tags";
        String SESSION_SPEAKER_NAMES   = "session_speaker_names";
        String SESSION_GROUPING_ORDER  = "session_grouping_order";
        String SESSION_IMPORT_HASHCODE = "session_import_hashcode";
        String SESSION_MAIN_TAG        = "session_main_tag";
        String SESSION_COLOR           = "session_color";
        String SESSION_CAPTIONS_URL    = "session_captions_url";
        String SESSION_INTERVAL_COUNT  = "session_interval_count";
        String SESSION_PHOTO_URL       = "session_photo_url";
        String SESSION_RELATED_CONTENT = "session_related_content";
    }

    interface SpeakersColumns {
        String SPEAKER_ID              = "speaker_id";
        String SPEAKER_NAME            = "speaker_name";
        String SPEAKER_IMAGE_URL       = "speaker_image_url";
        String SPEAKER_COMPANY         = "speaker_company";
        String SPEAKER_ABSTRACT        = "speaker_abstract";
        String SPEAKER_URL             = "speaker_url";
        String SPEAKER_IMPORT_HASHCODE = "speaker_import_hashcode";
    }

    interface AnnouncementsColumns {
        String ANNOUNCEMENT_ID            = "announcement_id";
        String ANNOUNCEMENT_TITLE         = "announcement_title";
        String ANNOUNCEMENT_ACTIVITY_JSON = "announcement_activity_json";
        String ANNOUNCEMENT_URL           = "announcement_url";
        String ANNOUNCEMENT_DATE          = "announcement_date";
    }

    interface MapMarkerColumns {
        String MARKER_ID        = "map_marker_id";
        String MARKER_TYPE      = "map_marker_type";
        String MARKER_LATITUDE  = "map_marker_latitude";
        String MARKER_LONGITUDE = "map_marker_longitude";
        String MARKER_LABEL     = "map_marker_label";
        String MARKER_FLOOR     = "map_marker_floor";
    }

    interface FeedbackColumns {
        String SESSION_ID       = "session_id";
        String SESSION_RATING   = "feedback_session_rating";
        String ANSWER_RELEVANCE = "feedback_answer_q1";
        String ANSWER_CONTENT   = "feedback_answer_q2";
        String ANSWER_SPEAKER   = "feedback_answer_q3";
        String COMMENTS         = "feedback_comments";
        String SYNCED           = "synced";
    }

    interface MapTileColumns {
        String TILE_FLOOR = "map_tile_floor";
        String TILE_FILE  = "map_tile_file";
        String TILE_URL   = "map_tile_url";
    }

    interface ExpertsColumns {
        String EXPERT_ID              = "expert_id";
        String EXPERT_NAME            = "expert_name";
        String EXPERT_IMAGE_URL       = "expert_image_url";
        String EXPERT_TITLE           = "expert_title";
        String EXPERT_ABSTRACT        = "expert_abstract";
        String EXPERT_URL             = "expert_url";
        String EXPERT_COUNTRY         = "expert_country";
        String EXPERT_CITY            = "expert_city";
        String EXPERT_ATTENDING       = "expert_attending";
        String EXPERT_IMPORT_HASHCODE = "expert_import_hashcode";
    }

    interface PartnersColumns {
        String PARTNER_ID          = "partner_id";
        String PARTNER_NAME        = "partner_name";
        String PARTNER_DESC        = "partner_desc";
        String PARTNER_WEBSITE_URL = "partner_website_url";
        String PARTNER_LOGO_URL    = "partner_logo_url";
    }

    interface HashtagColumns {
        String HASHTAG_NAME        = "hashtag_name";
        String HASHTAG_DESCRIPTION = "hashtag_description";
        String HASHTAG_COLOR       = "hashtag_color";
        String HASHTAG_ORDER       = "hashtag_order";
    }

    interface PeopleIveMetColumns {
        String PERSON_ID        = "person_id";
        String PERSON_TIMESTAMP = "person_timestamp";
        String PERSON_NAME      = "person_name";
        String PERSON_IMAGE_URL = "person_image_url";
        String PERSON_NOTE      = "person_note";
    }

    interface VideoColumns {
        String VIDEO_ID              = "video_id";
        String VIDEO_YEAR            = "video_year";
        String VIDEO_TITLE           = "video_title";
        String VIDEO_DESC            = "video_desc";
        String VIDEO_VID             = "video_vid";
        String VIDEO_TOPIC           = "video_topic";
        String VIDEO_SPEAKERS        = "video_speakers";
        String VIDEO_THUMBNAIL_URL   = "video_thumbnail_url";
        String VIDEO_IMPORT_HASHCODE = "video_import_hashcode";
    }

    /**************************************** URI ********************************************/

    public static final String   CONTENT_AUTHORITY       = "com.google.samples.apps.iosched";
    public static final Uri      BASE_CONTENT_URI        = Uri.parse("content://" + CONTENT_AUTHORITY);

    /************************************** URI . Path ***************************************/

    private static final String  PATH_BLOCKS             = "blocks";
    private static final String  PATH_AFTER              = "after";
    private static final String  PATH_TAGS               = "tags";
    private static final String  PATH_ROOM               = "room";
    private static final String  PATH_UNSCHEDULED        = "unscheduled";
    private static final String  PATH_ROOMS              = "rooms";
    private static final String  PATH_SESSIONS           = "sessions";
    private static final String  PATH_FEEDBACK           = "feedback";
    private static final String  PATH_MY_SCHEDULE        = "my_schedule";
    private static final String  PATH_SESSIONS_COUNTER   = "counter";
    private static final String  PATH_SPEAKERS           = "speakers";
    private static final String  PATH_ANNOUNCEMENTS      = "announcements";
    private static final String  PATH_MAP_MARKERS        = "mapmarkers";
    private static final String  PATH_MAP_FLOOR          = "floor";
    private static final String  PATH_MAP_TILES          = "maptiles";
    private static final String  PATH_HASHTAGS           = "hashtags";
    private static final String  PATH_VIDEOS             = "videos";
    private static final String  PATH_SEARCH             = "search";
    private static final String  PATH_SEARCH_SUGGEST     = "search_suggest_query";
    private static final String  PATH_SEARCH_INDEX       = "search_index";
    private static final String  PATH_EXPERTS            = "experts";
    private static final String  PATH_PARTNERS           = "partners";
    private static final String  PATH_PEOPLE_IVE_MET     = "people_ive_met";

    /************************************** Path数组 *****************************************/

    public static final String[] TOP_LEVEL_PATHS         = {PATH_BLOCKS, PATH_TAGS, PATH_ROOMS, PATH_SESSIONS,
                                                            PATH_FEEDBACK, PATH_MY_SCHEDULE, PATH_SPEAKERS, 
                                                            PATH_ANNOUNCEMENTS, PATH_MAP_MARKERS, PATH_MAP_FLOOR,
                                                            PATH_MAP_MARKERS, PATH_MAP_TILES, PATH_HASHTAGS, 
                                                            PATH_VIDEOS, PATH_EXPERTS, PATH_PARTNERS,
                                                            PATH_PEOPLE_IVE_MET                          };

    public static final String[] USER_DATA_RELATED_PATHS = {PATH_SESSIONS, PATH_MY_SCHEDULE};

    /************************************** 表类 *********************************************/

    public static class Blocks implements BlocksColumns, BaseColumns {

        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BLOCKS).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.block";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.block";
        public static final String DEFAULT_SORT      = BlocksColumns.BLOCK_START + " ASC, " + BlocksColumns.BLOCK_END + " ASC";
        public static final String BLOCK_TYPE_FREE    = "free";
        public static final String BLOCK_TYPE_BREAK   = "break";
        public static final String BLOCK_TYPE_KEYNOTE = "keynote";

        public static final boolean isValidBlockType(String type) {
            return BLOCK_TYPE_FREE.equals(type) || BLOCK_TYPE_BREAK.equals(type) || BLOCK_TYPE_KEYNOTE.equals(type);
        }

        public static Uri buildBlockUri(String blockId) {
            return CONTENT_URI.buildUpon().appendPath(blockId).build();
        }

        public static String getBlockId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String generateBlockId(long startTime, long endTime) {
            startTime /= DateUtils.SECOND_IN_MILLIS;
            endTime /= DateUtils.SECOND_IN_MILLIS;
            return ParserUtils.sanitizeId(startTime + "-" + endTime);
        }
    }
 
    public static class Tags implements TagsColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TAGS).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.tag";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.tag";
        public static final String DEFAULT_SORT      = TagsColumns.TAG_ORDER_IN_CATEGORY;

        public static Uri buildTagsUri() {
            return CONTENT_URI;
        }

        public static Uri buildTagUri(String tagId) {
            return CONTENT_URI.buildUpon().appendPath(tagId).build();
        }

        public static String getTagId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class MySchedule implements MyScheduleColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MY_SCHEDULE).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.myschedule";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.myschedule";

        public static Uri buildMyScheduleUri(Context context) {
            return buildMyScheduleUri(context, null);
        }

        public static Uri buildMyScheduleUri(Context context, String accountName) {
            if (accountName == null) {
                accountName = AccountUtils.getActiveAccountName(context);
            }
            return addOverrideAccountName(CONTENT_URI, accountName);
        }

    }

    public static class Rooms implements RoomsColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ROOMS).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.room";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.room";
        public static final String DEFAULT_SORT      = RoomsColumns.ROOM_FLOOR + " ASC, " + RoomsColumns.ROOM_NAME + " COLLATE NOCASE ASC";

        public static Uri buildRoomUri(String roomId) {
            return CONTENT_URI.buildUpon().appendPath(roomId).build();
        }

        public static Uri buildSessionsDirUri(String roomId) {
            return CONTENT_URI.buildUpon().appendPath(roomId).appendPath(PATH_SESSIONS).build();
        }

        public static String getRoomId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Feedback implements BaseColumns, FeedbackColumns, SyncColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FEEDBACK).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.session_feedback";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.session_feedback";

        public static final String DEFAULT_SORT      = BaseColumns._ID + " ASC, ";

        public static Uri buildFeedbackUri(String sessionId) {
            return CONTENT_URI.buildUpon().appendPath(sessionId).build();
        }

        public static String getSessionId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Sessions implements SessionsColumns, RoomsColumns, SyncColumns, BaseColumns {
        
        public static final String QUERY_PARAMETER_TAG_FILTER          = "filter";
        public static final Uri    CONTENT_URI                         = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SESSIONS).build();
        public static final Uri    CONTENT_MY_SCHEDULE_URI             = CONTENT_URI.buildUpon().appendPath(PATH_MY_SCHEDULE).build();
        public static final String CONTENT_TYPE                        = "vnd.android.cursor.dir/vnd.iosched2014.session";
        public static final String CONTENT_ITEM_TYPE                   = "vnd.android.cursor.item/vnd.iosched2014.session";
        public static final String ROOM_ID                             = "room_id";
        public static final String SEARCH_SNIPPET                      = "search_snippet";
        public static final String HAS_GIVEN_FEEDBACK                  = "has_given_feedback";
        public static final String SORT_BY_TYPE_THEN_TIME              = SESSION_GROUPING_ORDER + " ASC," + SESSION_START + " ASC," + SESSION_TITLE + " COLLATE NOCASE ASC";
        public static final String SORT_BY_TIME                        = SESSION_START + " ASC," + SESSION_TITLE + " COLLATE NOCASE ASC";
        public static final String LIVESTREAM_SELECTION                = SESSION_LIVESTREAM_URL + " is not null AND " + SESSION_LIVESTREAM_URL + "!=''";
        public static final String STARTING_AT_TIME_INTERVAL_SELECTION = SESSION_START + " >= ? and " + SESSION_START + " <= ?";
        public static final String AT_TIME_SELECTION                   = SESSION_START + " <= ? and " + SESSION_END + " >= ?";
        public static final String UPCOMING_LIVE_SELECTION             = SESSION_START + " > ?";
        
        public static String[] buildAtTimeIntervalArgs(long intervalStart, long intervalEnd) {
            return new String[] {String.valueOf(intervalStart), String.valueOf(intervalEnd)};
        }

        public static String[] buildAtTimeSelectionArgs(long time) {
            final String timeString = String.valueOf(time);
            return new String[] {timeString, timeString};
        }

        public static String[] buildUpcomingSelectionArgs(long minTime) {
            return new String[] {String.valueOf(minTime)};
        }

        public static Uri buildSessionUri(String sessionId) {
            return CONTENT_URI.buildUpon().appendPath(sessionId).build();
        }

        public static Uri buildSpeakersDirUri(String sessionId) {
            return CONTENT_URI.buildUpon().appendPath(sessionId).appendPath(PATH_SPEAKERS).build();
        }

        public static Uri buildTagsDirUri(String sessionId) {
            return CONTENT_URI.buildUpon().appendPath(sessionId).appendPath(PATH_TAGS).build();
        }

        public static Uri buildSearchUri(String query) {
            if (null == query) {
                query = "";
            }
            query = query.replaceAll(" +", " *") + "*";
            return CONTENT_URI.buildUpon().appendPath(PATH_SEARCH).appendPath(query).build();
        }

        public static boolean isSearchUri(Uri uri) {
            List<String> pathSegments = uri.getPathSegments();
            return pathSegments.size() >= 2 && PATH_SEARCH.equals(pathSegments.get(1));
        }

        public static Uri buildSessionsInRoomAfterUri(String room, long time) {
            return CONTENT_URI.buildUpon().appendPath(PATH_ROOM).appendPath(room).appendPath(PATH_AFTER).appendPath(String.valueOf(time)).build();
        }

        public static Uri buildUnscheduledSessionsInInterval(long start, long end) {
            String interval = start + "-" + end;
            return CONTENT_URI.buildUpon().appendPath(PATH_UNSCHEDULED).appendPath(interval).build();
        }

        public static boolean isUnscheduledSessionsInInterval(Uri uri) {
            return uri != null && uri.toString().startsWith(CONTENT_URI.buildUpon().appendPath(PATH_UNSCHEDULED).toString());
        }

        public static long[] getInterval(Uri uri) {
            if (uri == null) {
                return null;
            }
            List<String> segments = uri.getPathSegments();
            if (segments.size() == 3 && segments.get(2).indexOf('-') > 0) {
                String[] interval = segments.get(2).split("-");
                return new long[] {Long.parseLong(interval[0]), Long.parseLong(interval[1])};
            }
            return null;
        }

        public static String getRoom(Uri uri) {
            return uri.getPathSegments().get(2);
        }

        public static String getAfter(Uri uri) {
            return uri.getPathSegments().get(4);
        }

        public static String getSessionId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getSearchQuery(Uri uri) {
            List<String> segments = uri.getPathSegments();
            if (2 < segments.size()) {
                return segments.get(2);
            }
            return null;
        }

        public static boolean hasFilterParam(Uri uri) {
            return uri != null && uri.getQueryParameter(QUERY_PARAMETER_TAG_FILTER) != null;
        }

        public static Uri buildTagFilterUri(String[] requiredTags) {
            StringBuilder sb = new StringBuilder();
            for (String tag : requiredTags) {
                if (TextUtils.isEmpty(tag))
                    continue;
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(tag.trim());
            }
            if (sb.length() == 0) {
                return CONTENT_URI;
            } else {
                return CONTENT_URI.buildUpon().appendQueryParameter(QUERY_PARAMETER_TAG_FILTER, sb.toString()).build();
            }
        }

        public static Uri buildCounterByIntervalUri() {
            return CONTENT_URI.buildUpon().appendPath(PATH_SESSIONS_COUNTER).build();
        }
    }

    public static class Speakers implements SpeakersColumns, SyncColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SPEAKERS).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.speaker";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.speaker";
        public static final String DEFAULT_SORT      = SpeakersColumns.SPEAKER_NAME + " COLLATE NOCASE ASC";

        public static Uri buildSpeakerUri(String speakerId) {
            return CONTENT_URI.buildUpon().appendPath(speakerId).build();
        }
        
        public static Uri buildSessionsDirUri(String speakerId) {
            return CONTENT_URI.buildUpon().appendPath(speakerId).appendPath(PATH_SESSIONS).build();
        }

        public static String getSpeakerId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Announcements implements AnnouncementsColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ANNOUNCEMENTS).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.announcement";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.announcement";
        public static final String DEFAULT_SORT      = AnnouncementsColumns.ANNOUNCEMENT_DATE + " COLLATE NOCASE DESC";

        public static Uri buildAnnouncementUri(String announcementId) {
            return CONTENT_URI.buildUpon().appendPath(announcementId).build();
        }

        public static String getAnnouncementId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class MapTiles implements MapTileColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MAP_TILES).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.maptiles";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.maptiles";

        public static final String DEFAULT_SORT      = MapTileColumns.TILE_FLOOR + " ASC";

        public static Uri buildUri() {
            return CONTENT_URI;
        }

        public static Uri buildFloorUri(String floor) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(floor)).build();
        }

        public static String getFloorId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class MapMarkers implements MapMarkerColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MAP_MARKERS).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.mapmarker";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.mapmarker";

        public static final String DEFAULT_SORT      = MapMarkerColumns.MARKER_FLOOR + " ASC, "+ MapMarkerColumns.MARKER_ID + " ASC";

        public static Uri buildMarkerUri(String markerId) {
            return CONTENT_URI.buildUpon().appendPath(markerId).build();
        }

        public static Uri buildMarkerUri() {
            return CONTENT_URI;
        }

        public static Uri buildFloorUri(int floor) {
            return CONTENT_URI.buildUpon().appendPath(PATH_MAP_FLOOR).appendPath("" + floor).build();
        }

        public static String getMarkerId(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static String getMarkerFloor(Uri uri) {
            return uri.getPathSegments().get(2);
        }

    }

    public static class Hashtags implements HashtagColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_HASHTAGS).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.hashtags";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.hashtags";

        public static Uri buildHashtagUri(String hashtag) {
            return CONTENT_URI.buildUpon().appendPath(hashtag).build();
        }

        public static String getHashtagName(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static class Videos implements VideoColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_VIDEOS).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.videos";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.videos";
        public static final String DEFAULT_SORT      = VideoColumns.VIDEO_YEAR + " DESC, " + VideoColumns.VIDEO_TOPIC + " ASC, " + VideoColumns.VIDEO_TITLE + " ASC";
        
        public static Uri buildVideoUri(String videoId) {
            return CONTENT_URI.buildUpon().appendPath(videoId).build();
        }

        public static String getVideoId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class SearchSuggest {
        
        public static final Uri    CONTENT_URI  = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEARCH_SUGGEST).build();
        public static final String DEFAULT_SORT = SearchManager.SUGGEST_COLUMN_TEXT_1 + " COLLATE NOCASE ASC";
        
    }

    public static class SearchIndex {
        
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SEARCH_INDEX).build();
        
    }

    public static class Experts implements ExpertsColumns, SyncColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_EXPERTS).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.expert";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.expert";

        public static final String DEFAULT_SORT      = ExpertsColumns.EXPERT_NAME + " COLLATE NOCASE ASC";

        public static Uri buildExpertUri(String expertId) {
            return CONTENT_URI.buildUpon().appendPath(expertId).build();
        }

        public static String getExpertId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Partners implements PartnersColumns, SyncColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PARTNERS).build();
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.partner";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.partner";

        public static Uri buildPartnerUri(String partnerId) {
            return CONTENT_URI.buildUpon().appendPath(partnerId).build();
        }

        public static String getPartnerId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class PeopleIveMet implements PeopleIveMetColumns, BaseColumns {
        
        public static final Uri    CONTENT_URI       = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PEOPLE_IVE_MET).build();
        public static final String DEFAULT_SORT      = PeopleIveMetColumns.PERSON_TIMESTAMP + " DESC";
        public static final String CONTENT_TYPE      = "vnd.android.cursor.dir/vnd.iosched2014.people_ive_met";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.iosched2014.people_ive_met";

        public static Uri buildPersonUri(String personId) {
            return CONTENT_URI.buildUpon().appendPath(personId).build();
        }

        public static String getPersonId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static Uri addCallerIsSyncAdapterParameter(Uri uri) {
        
        return uri.buildUpon().appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build();
        
    }

    public static boolean hasCallerIsSyncAdapterParameter(Uri uri) {
        
        return TextUtils.equals("true", uri.getQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER));
        
    }

    public static Uri addOverrideAccountName(Uri uri, String accountName) {
        return uri.buildUpon().appendQueryParameter(OVERRIDE_ACCOUNTNAME_PARAMETER, accountName).build();
    }

    public static String getOverrideAccountName(Uri uri) {
        return uri.getQueryParameter(OVERRIDE_ACCOUNTNAME_PARAMETER);
    }

   
}
