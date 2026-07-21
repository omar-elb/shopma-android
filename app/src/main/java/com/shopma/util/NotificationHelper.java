package com.shopma.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class NotificationHelper {

    private static final String CHANNEL_ID = "shopma_orders";
    private static final int NOTIFICATION_ID_ORDER = 1001;

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Confirmations de commande", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notifications envoyées après une commande");
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(channel);
        }
    }

    public static void showOrderConfirmation(Context context, double total) {
        boolean hasPermission = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
                || ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED;

        if (!hasPermission) return;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Commande confirmée")
                .setContentText(String.format(Locale.getDefault(),
                        "Votre commande de %.2f$ a été enregistrée.", total))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_ORDER, builder.build());
    }
}
