
/*
 * Created by Curtis Getz on 11/6/18 10:02 AM
 * Last modified 11/4/18 1:33 AM
 */

package com.curtisgetz.marsexplorer.data.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.curtisgetz.marsexplorer.R;
import com.curtisgetz.marsexplorer.data.MarsRepository;
import com.curtisgetz.marsexplorer.data.Tweet;
import com.curtisgetz.marsexplorer.ui.explore_detail.ExploreDetailActivity;
import com.curtisgetz.marsexplorer.ui.main.MainActivity;
import com.curtisgetz.marsexplorer.utils.HelperUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Class for handling Firebase Cloud Messages. Will also create Tweet object from that message and
 * save that Tweet into database.
 *
 */

public class MarsFirebaseMessagingService extends FirebaseMessagingService {

    /*todo Tweets are being sent manually through the Firebase Console. Need to develop a backend
    * solution to automatically grab Tweets from Twitter and send the message
    */



    public final static String JSON_KEY_TWEET_ID = "tweet_id";
    public final static String JSON_KEY_USER_ID = "user_id";
    public final static String JSON_KEY_USER_NAME = "user_name";
    public final static String JSON_KEY_TWEET_DATE = "date";
    public final static String JSON_KEY_TWEET_TEXT = "tweet_text";
    public final static String JSON_KEY_TWEET_PHOTO = "tweet_photo";
    private final static CharSequence MESSAGE = "New Tweet From Mars Rover!";
    private final static String NOTIFICATION_CHANNEL_ID = "rover_tweet";
    private final static String NOTIFICATION_CHANNEL_NAME = "Tweets From Rover";



    /**
     * Notification messages only received here when app is in foreground
     * Create notification and save tweet into database. Will need to be changed when backend
     * is set up
     */

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> messageData = remoteMessage.getData();

        if (messageData.size() > 0){
            //attempt to parse ints from message
            int tweetId;
            int userId;
            try {
                tweetId = Integer.parseInt(messageData.get(JSON_KEY_TWEET_ID));
                userId = Integer.parseInt(messageData.get(JSON_KEY_USER_ID));
            }catch (NumberFormatException e){
                e.printStackTrace();
                return;
            }
            String userName = messageData.get(JSON_KEY_USER_NAME);
            String tweetDate = messageData.get(JSON_KEY_TWEET_DATE);
            String tweetText = messageData.get(JSON_KEY_TWEET_TEXT);
            String tweetPhoto = messageData.get(JSON_KEY_TWEET_PHOTO);

            Tweet tweet = new Tweet(tweetId, userId, userName, tweetDate, tweetText);
            //add photo url if there is one
            if(tweetPhoto != null && !tweetPhoto.isEmpty()){
                tweet.setTweetPhotoUrl(tweetPhoto);
            }

            displayNotification();
            insertTweet(tweet);
        }
    }

    /**
     * Inset Tweet into database
     * @param tweet the tweet object to save
     */
    private void insertTweet(Tweet tweet) {
        MarsRepository repository = MarsRepository.getInstance(getApplication());
        repository.insertTweet(tweet);
    }

    /**
     * Create and display notification for when app is in foreground and notification is not displayed
     * by default
     */
    private void displayNotification() {
        Intent intent = new Intent(this, ExploreDetailActivity.class);
        intent.putExtra(getString(R.string.explore_index_extra_key), HelperUtils.ROVER_TWEETS_CAT_INDEX);
        intent.putExtra(getString(R.string.parent_activity_tag_extra), MainActivity.class.getSimpleName());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //create pending intent to launch activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(notificationManager == null) return;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel tweetChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(tweetChannel);
        }


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.marsexplorericon)
                .setContentTitle(MESSAGE)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        notificationManager.notify(0, notificationBuilder.build());

    }


}
