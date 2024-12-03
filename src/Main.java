import tasktracker.model.Epic;
import tasktracker.model.Status;
import tasktracker.model.Subtask;
import tasktracker.model.Task;
import tasktracker.taskmanager.InMemoryTaskManager;
import tasktracker.taskmanager.Managers;
import tasktracker.taskmanager.TaskManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Epic epic = new Epic("Epic2", "", Status.NEW);
        Subtask subtask1 = new Subtask("sub21", "", Status.NEW, epic);
        Subtask subtask2 = new Subtask("sub22", "", Status.NEW, epic);

        System.out.println(taskManager.toString());

//        taskManager.addTask(subtask1);
//        taskManager.addTask(subtask2);
//        taskManager.addTask(epic);


//        taskManager.addTask(subtask1);
//        taskManager.addTask(subtask2);
//        taskManager.addTask(epic);




//        System.out.println(Arrays.toString(taskManager.history().toArray()));

//

//

    }

//    static void iterPrint(List<Integer> list, TaskManager taskManager) {
//        for (Integer i : new ArrayList<>(list)) {
//            System.out.println(taskManager.getTask(i).toString());
//        }
//    }
//    static void iterPrint(List<Task> list) {
//        for (Task task : new ArrayList<>(list)) {
//            System.out.println(task.toString());
//        }
//    }
}