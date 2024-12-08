import tasktracker.controller.KVServer;
import tasktracker.controller.KVTaskClient;
import tasktracker.model.Epic;
import tasktracker.model.Status;
import tasktracker.model.Subtask;
import tasktracker.controller.Managers;
import tasktracker.controller.TaskManager;
import tasktracker.model.Task;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws IOException {
        new KVServer().start();
        TaskManager taskManager = Managers.getDefault();
        taskManager.getTask(1);
        taskManager.addTask(new Task("task1", "desc1", Status.NEW));

//        taskManager.addTask(new Task("T", "d", Status.NEW));
//        KVTaskClient taskClient = new KVTaskClient("http://localhost:8078");
//        taskClient.put("1", "One");
//        String loadedStr = taskClient.load("1");
//        System.out.println(loadedStr);

    }
}