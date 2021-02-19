package com.tutorialsbuzz.notificationimgload;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button notifyBtn = findViewById(R.id.notifyBtn);

        notifyBtn.setOnClickListener(v -> {

            createNotification(
                    getResources().getString(R.string.notification_title),
                    getResources().getString(R.string.notification_content),
                    getResources().getString(R.string.notification_channel)
            );

        });

    }

    /**
     * Create Notification
     * Param
     * 1. title
     * 2. content
     * 3. channelId
     * 4.priorty
     * 5. notificationId
     */

    private void createNotification(String title, String content,
                                    String channedId) {


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext(), channedId)
                        .setSmallIcon(R.drawable.ic_notifications_active)
                        .setAutoCancel(true)
                        .setLights(Color.BLUE, 500, 500)
                        .setVibrate(new long[]{500, 500, 500})
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);


        // Since android Oreo notification channel is needed.
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channedId,
                    channedId,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(channel);
        }

        String imageUrl = "https://raw.githubusercontent.com/TutorialsBuzz/cdn/main/android.jpg";

        GlideApp.with(getApplicationContext())
                .asBitmap()
                .load(imageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //largeIcon
                        notificationBuilder.setLargeIcon(resource);
                        //Big Picture
                        notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(resource));

                        Notification notification = notificationBuilder.build();
                        notificationManager.notify(NotificationID.getID(), notification);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }
                });
    }

    static class NotificationID {
        private final static AtomicInteger c = new AtomicInteger(100);

        public static int getID() {
            return c.incrementAndGet();
        }
    }

}