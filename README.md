# Firebase Cloud Messaging plugin for Godot engine. 

## Installation

Use [NativeLib Addon](https://github.com/DrMoriarty/nativelib) or [NativeLib-CLI](https://github.com/DrMoriarty/nativelib-cli) for installation.


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
