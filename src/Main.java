import tasktracker.model.Epic;
import tasktracker.model.Status;
import tasktracker.model.Subtask;
import tasktracker.model.Task;
import tasktracker.taskmanager.InMemoryTaskManager;
import tasktracker.taskmanager.Managers;
import tasktracker.taskmanager.TaskManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        int epicId = 443;

        taskManager.addTask(new Task("Побегать на стадионе", "", Status.NEW));
        taskManager.addTask(new Task("Купить молоко", "Сделать это в пятерочке", Status.NEW));
        taskManager.addTask(epicId, new Epic("Собрать комп", "", Status.NEW));
        taskManager.addTask(new Subtask("Купить видюху", "Мегамаркет", Status.NEW, (Epic) taskManager.getTask(epicId)));
        taskManager.addTask(new Subtask("Купить клаву", "DNS", Status.NEW, (Epic) taskManager.getTask(epicId)));


//        for (Task task : taskManager.getAllTasks()) {
//            System.out.println(task.toString());
//        }
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getTask(epicId);

        iterPrint(taskManager.history(), taskManager);
        System.out.println();

        taskManager.getTask(3);
        taskManager.getTask(4);

        iterPrint(taskManager.history(), taskManager);



    }

    static void iterPrint(List<Integer> list, TaskManager taskManager) {
        for (Integer i : new ArrayList<>(list)) {
            System.out.println(taskManager.getTask(i).toString());
        }
    }
}