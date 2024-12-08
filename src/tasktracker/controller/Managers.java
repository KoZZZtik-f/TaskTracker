package tasktracker.controller;

import tasktracker.config.Config;

public class Managers {

    public static TaskManager getDefault() {
        return new HTTPTaskManager(Config.URI_STRING);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
