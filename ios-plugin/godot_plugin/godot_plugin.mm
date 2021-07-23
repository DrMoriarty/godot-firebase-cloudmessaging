
#import <Foundation/Foundation.h>
#import "godot_plugin.h"
#import "godot_plugin_class.h"
#import "core/engine.h"

FirebaseCloudMessaging *plugin;

void firebase_cloud_messaging_init() {
    plugin = memnew(FirebaseCloudMessaging);
    Engine::get_singleton()->add_singleton(Engine::Singleton("FirebaseCloudMessaging", plugin));
}

void firebase_cloud_messaging_deinit() {
   if (plugin) {
       memdelete(plugin);
   }
}
