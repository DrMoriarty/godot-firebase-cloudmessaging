package ru.mobilap.firebasecloudmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.Context;
import android.os.Bundle;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.util.DisplayMetrics;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.view.Display;
import android.view.View;
import java.math.BigDecimal;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Locale;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.lang.Exception;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.FirebaseMessaging;

import org.godotengine.godot.Godot;
import org.godotengine.godot.Dictionary;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

public class FirebaseCloudMessaging extends GodotPlugin {

    private final String TAG = FirebaseCloudMessaging.class.getName();
    static private FirebaseCloudMessaging _instance = null;
    final private SignalInfo tokenSignal = new SignalInfo("token");
    final private SignalInfo messageSignal = new SignalInfo("message");
    private String _token = "";
    private Dictionary _message = new Dictionary();

    public FirebaseCloudMessaging(Godot godot) 
    {
        super(godot);
        _instance = this;
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        _token = task.getResult();
                        Log.d(TAG, "FCM token: "+_token);
                        emitSignal(tokenSignal.getName());
                    }
                });
    }

    @Override
    public String getPluginName() {
        return "FirebaseCloudMessaging";
    }

    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList("get_token", "get_message");
    }

    @Override
    public Set<SignalInfo> getPluginSignals() {
        return new HashSet<SignalInfo>(Arrays.asList(tokenSignal, messageSignal));
    }

    @Override
    public View onMainCreate(Activity activity) {
        return null;
    }

    public static FirebaseCloudMessaging instance() {
        return _instance;
    }

    public void set_token(final String t) {
        _token = t;
        emitSignal(tokenSignal.getName());
    }

    public void set_message(RemoteMessage rt) {
        _message = new Dictionary();
        _message.put("from", rt.getFrom());
        _message.put("to", rt.getTo());
        _message.put("senderId", rt.getSenderId());
        _message.put("messageId", rt.getMessageId());
        _message.put("messageType", rt.getMessageType());
        if(rt.getNotification() != null) {
            Dictionary notif = new Dictionary();
            if(rt.getNotification().getBody() != null)
                notif.put("body", rt.getNotification().getBody());
            if(rt.getNotification().getTitle() != null)
                notif.put("title", rt.getNotification().getTitle());
            _message.put("notification", notif);
        }
        if (rt.getData().size() > 0) {
            Dictionary data = new Dictionary();
            for(String key: rt.getData().keySet()) {
                data.put(key, rt.getData().get(key));
            }
            _message.put("data", data);
        }
        emitSignal(messageSignal.getName());
    }

    // Public methods

    public String get_token() {
        return _token;
    }

    public Dictionary get_message() {
        return _message;
    }
}
