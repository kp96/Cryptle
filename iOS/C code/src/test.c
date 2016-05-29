#include <pebble.h>
#define KEY_BUTTON_DOWN 1
 #define KEY_CHOICE      0
#define CHOICE_WAITING  3
  static bool send_to_phone_multi(int quote_key, char *s_symbol);
static int s_choice = CHOICE_WAITING;

static TextLayer *hello_text_layer;
static Window *first_window;

 static void inbox_received_callback(DictionaryIterator *iterator, void *context) {
  APP_LOG(APP_LOG_LEVEL_INFO, "Message received!");
    Tuple *t = dict_read_first(iterator);
 
  while (t != NULL) {
    // Long lived buffer
        static char s_buffer[64];
        APP_LOG(APP_LOG_LEVEL_INFO, "Message ready to get!");
        snprintf(s_buffer, sizeof(s_buffer), "'%s'", t->value->cstring);
        text_layer_set_text(hello_text_layer, s_buffer);
    // Get next pair, if any
    t = dict_read_next(iterator);
  }
}
//dict_write_cstring(&iter, SOME_STRING_KEY, string);
static void inbox_dropped_callback(AppMessageResult reason, void *context) {
  APP_LOG(APP_LOG_LEVEL_ERROR, "Message dropped!");
}

static void outbox_sent_callback(DictionaryIterator *iterator, void *context) {
    text_layer_set_text(hello_text_layer, "Sending.......");
    APP_LOG(APP_LOG_LEVEL_INFO, "Outbox send success!");
}
static void down_click_handler(ClickRecognizerRef recognizer, void *context) {
  APP_LOG(APP_LOG_LEVEL_INFO, "down pressed");
  text_layer_set_text(hello_text_layer, "Down pressed!");
   app_message_register_outbox_sent(outbox_sent_callback);
  static char *message="karthi";
   bool queued =send_to_phone_multi(0, message);
  if(queued)
    {
      APP_LOG(APP_LOG_LEVEL_INFO, "Data send");
      text_layer_set_text(hello_text_layer, "Sent");

  }
  else
    {
          APP_LOG(APP_LOG_LEVEL_INFO, "Data not send");

  }
}
static bool send_to_phone_multi(int quote_key, char *s_symbol) {
  if ((quote_key == -1) && (s_symbol == NULL)) {
    APP_LOG(APP_LOG_LEVEL_DEBUG, "no data to send");
    // well, the "nothing" that was sent to us was queued, anyway ...
    return true;
  }

  DictionaryIterator *iter;
  app_message_outbox_begin(&iter);
  if (iter == NULL) {
    APP_LOG(APP_LOG_LEVEL_DEBUG, "null iter");
    return false;
  }

  Tuplet tuple = (s_symbol == NULL)
                      ? TupletInteger(quote_key, 1)
                      : TupletCString(quote_key, s_symbol);
  dict_write_tuplet(iter, &tuple);
  dict_write_end(iter);

  app_message_outbox_send();
  return true;
}

static void click_config_provider(void *context) {
  // Register the ClickHandlers
  window_single_click_subscribe(KEY_BUTTON_DOWN, down_click_handler);
}
int main(void) {
  // registering message received from iOS or receiving
  app_message_register_inbox_received(inbox_received_callback);
  app_message_register_inbox_dropped(inbox_dropped_callback);
  
  //send data to iOS
//    app_message_register_outbox_sent(outbox_sent_callback);
  
  //the maimum size of send and receive
  app_message_open(app_message_inbox_size_maximum(), app_message_outbox_size_maximum());

  //window creation code
  first_window = window_create();
  
  //text layer creation code
  hello_text_layer = text_layer_create(GRect(10, 10, 124, 148));
  text_layer_set_text(hello_text_layer, "Welcome");
  layer_add_child(window_get_root_layer(first_window), text_layer_get_layer(hello_text_layer));
 
  // register events to window
    window_set_click_config_provider(first_window, click_config_provider);
  //to show the window
 window_stack_push(first_window, true);
  app_event_loop();
 
  //remove the windows and its sub layers
  text_layer_destroy(hello_text_layer);
  window_destroy(first_window);
}


