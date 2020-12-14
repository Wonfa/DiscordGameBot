package DiscordGameBot.event_handlers;

public interface EventHandler {
	default void onMessageReceived() {}
}
