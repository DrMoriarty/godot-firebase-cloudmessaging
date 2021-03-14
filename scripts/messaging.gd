extends Node

signal token_received(token)
signal message_received(message)

var _ms = null
var _token = null

func _ready():
    if Engine.has_singleton('FirebaseCloudMessaging'):
        _ms = Engine.get_singleton('FirebaseCloudMessaging')
        _ms.connect('token', self, '_on_token')
        _ms.connect('message', self, '_on_message')
        _on_token()
        print('FirebaseCloudMessaging inited')
    else:
        push_warning('FirebaseCloudMessaging module not found!')

func _on_token():
    var token = _ms.get_token()
    if typeof(token) == TYPE_STRING and token.length() > 0:
        _token = token
        print('FCM token: %s'%token)
        emit_signal("token_received", _token)

func _on_message():
    var message = _ms.get_message()
    if typeof(message) == TYPE_DICTIONARY:
        print('FCM message: %s'%var2str(message))
        emit_signal("message_received", message)

func get_token():
    return _token
