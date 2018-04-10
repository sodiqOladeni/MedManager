//package com.africanego.med_manager.provider;
//
//import android.content.ContentProvider;
//import android.content.ContentProviderOperation;
//import android.content.ContentProviderResult;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.OperationApplicationException;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
//import com.africanego.med_manager.db.ReminderEntity;
//import com.africanego.med_manager.db.UserDao;
//import com.africanego.med_manager.db.UserDatabase;
//import com.africanego.med_manager.db.UserDetails;
//
//import java.util.ArrayList;
//
//
///**
// * A {@link ContentProvider} based on a Room database.
// *
// * <p>Note that you don't need to implement a ContentProvider unless you want to expose the data
// * outside your process or your application already uses a ContentProvider.</p>
// */
//public class AlarmReminderProvider extends ContentProvider {
//
//    /** The authority of this content provider. */
//    public static final String AUTHORITY = "com.africanego.med_manager.provider";
//
//    /** The URI for the Cheese table. */
//    public static final Uri URI_MENU  = Uri.parse(
//            "content://" + AUTHORITY + "/" + ReminderEntity.TABLE_NAME);
//
//    /** The match code for some items in the Cheese table. */
//    private static final int TABLE_ITEMS = 1;
//
//
//    /** The match code for an item in the Cheese table. */
//    private static final int TABLE_SINGLE_ITEM = 2;
//
//    /** The URI matcher. */
//    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
//
//    static {
//        MATCHER.addURI(AUTHORITY, UserDetails.TABLE_NAME, TABLE_ITEMS);
//        MATCHER.addURI(AUTHORITY, UserDetails.TABLE_NAME + "/*", TABLE_SINGLE_ITEM);
//    }
//
//    @Override
//    public boolean onCreate() {
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
//                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
//        final int code = MATCHER.match(uri);
//        if (code == TABLE_ITEMS || code == TABLE_SINGLE_ITEM) {
//            final Context context = getContext();
//            if (context == null) {
//                return null;
//            }
//            UserDao reminderSet = UserDatabase.getInstance(context).userDao();
//            final Cursor cursor;
//            if (code == TABLE_ITEMS) {
//                cursor = reminderSet.getDrugReminders();
//            } else {
//                cursor = reminderSet.getDrugRemindersById(ContentUris.parseId(uri));
//            }
//            cursor.setNotificationUri(context.getContentResolver(), uri);
//            return cursor;
//        } else {
//            throw new IllegalArgumentException("Unknown URI: " + uri);
//        }
//    }
//
//    @Nullable
//    @Override
//    public String getType(@NonNull Uri uri) {
//        switch (MATCHER.match(uri)) {
//            case TABLE_ITEMS:
//                return "vnd.android.cursor.dir/" + AUTHORITY + "." + ReminderEntity.TABLE_NAME;
//            case TABLE_SINGLE_ITEM:
//                return "vnd.android.cursor.item/" + AUTHORITY + "." + ReminderEntity.TABLE_NAME;
//            default:
//                throw new IllegalArgumentException("Unknown URI: " + uri);
//        }
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
//        switch (MATCHER.match(uri)) {
//            case TABLE_ITEMS:
//                final Context context = getContext();
//                if (context == null) {
//                    return null;
//                }
//                final long id = UserDatabase.getInstance(context).userDao()
//                        .insertNewDrugReminder(ReminderEntity.fromContentValues(values));
//                context.getContentResolver().notifyChange(uri, null);
//                return ContentUris.withAppendedId(uri, id);
//            case TABLE_SINGLE_ITEM:
//                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
//            default:
//                throw new IllegalArgumentException("Unknown URI: " + uri);
//        }
//    }
//
//    @Override
//    public int delete(@NonNull Uri uri, @Nullable String selection,
//                      @Nullable String[] selectionArgs) {
//        switch (MATCHER.match(uri)) {
//            case TABLE_ITEMS:
//                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
//            case TABLE_SINGLE_ITEM:
//                final Context context = getContext();
//                if (context == null) {
//                    return 0;
//                }
//                final int count = UserDatabase.getInstance(context).userDao()
//                        .deleteDrugReminderById(ContentUris.parseId(uri));
//                context.getContentResolver().notifyChange(uri, null);
//                return count;
//            default:
//                throw new IllegalArgumentException("Unknown URI: " + uri);
//        }
//    }
//
//    @Override
//    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
//                      @Nullable String[] selectionArgs) {
//        switch (MATCHER.match(uri)) {
//            case TABLE_ITEMS:
//                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
//            case TABLE_SINGLE_ITEM:
//                final Context context = getContext();
//                if (context == null) {
//                    return 0;
//                }
//                final ReminderEntity reminder = ReminderEntity.fromContentValues(values);
//                reminder.id = ContentUris.parseId(uri);
//                final int count = UserDatabase.getInstance(context).userDao()
//                        .updateDrugReminder(reminder);
//                context.getContentResolver().notifyChange(uri, null);
//                return count;
//            default:
//                throw new IllegalArgumentException("Unknown URI: " + uri);
//        }
//    }
//
//    @NonNull
//    @Override
//    public ContentProviderResult[] applyBatch(
//            @NonNull ArrayList<ContentProviderOperation> operations)
//            throws OperationApplicationException {
//        final Context context = getContext();
//        if (context == null) {
//            return new ContentProviderResult[0];
//        }
//        final UserDatabase database = UserDatabase.getInstance(context);
//        database.beginTransaction();
//        try {
//            final ContentProviderResult[] result = super.applyBatch(operations);
//            database.setTransactionSuccessful();
//            return result;
//        } finally {
//            database.endTransaction();
//        }
//    }
//
////    @Override
////    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
////        switch (MATCHER.match(uri)) {
////            case TABLE_ITEMS:
////                final Context context = getContext();
////                if (context == null) {
////                    return 0;
////                }
////                final UserDatabase database = UserDatabase.getInstance(context);
////                final Cheese[] cheeses = new Cheese[valuesArray.length];
////                for (int i = 0; i < valuesArray.length; i++) {
////                    cheeses[i] = Cheese.fromContentValues(valuesArray[i]);
////                }
////                return database.cheese().insertAll(cheeses).length;
////            case TABLE_SINGLE_ITEM:
////                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
////            default:
////                throw new IllegalArgumentException("Unknown URI: " + uri);
////        }
////    }
//
//}