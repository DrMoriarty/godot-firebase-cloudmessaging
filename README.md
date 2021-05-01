# Firebase Cloud Messaging plugin for Godot engine. 

## Installation

Use [NativeLib Addon](https://github.com/DrMoriarty/nativelib) or [NativeLib-CLI](https://github.com/DrMoriarty/nativelib-cli) for installation.

Don't forget to put file `google-services.json` in folder `android/build` before exporting project.

For iOS:
- ensure that plugin NativeLib-Export installed and enabled
- add `GoogleService-Info.plist` in `addons/nativelib-export/iOS`

## Setup custom notification icon for Android

1. After NativeLib FCM setup notifications will show up without your desired "notification_icon.png" but a white square ot something you didn't want.

2. Add the white notification icon notification_icon.png under yourgodotproject/android/build/res/mipmap/.

3. In the `AndroidManifest.xml` under yourgodotproject/android/build/ inside :
```
<meta-data android:name="com.google.firebase.messaging.default_notification_icon" android:resource="@mipmap/notification_icon" />
```

## Usage

Subscribe for two signals:
```
    messaging.connect('token_received', self, '_on_token_received')
    messaging.connect('message_received', self, '_on_message_received')

func _on_token_received(token: String) -> void:
    pass

func _on_message_received(message: Dictionary) -> void:
    pass
```

Also you can check token during app initialization:
```
token = messaging.get_token()
```
