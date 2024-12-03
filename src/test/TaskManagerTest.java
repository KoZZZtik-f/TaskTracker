package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasktracker.model.Epic;
import tasktracker.model.Status;
import tasktracker.model.Subtask;
import tasktracker.model.Task;
import tasktracker.taskmanager.TaskManager;

public abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;
    Task taskForTest;

    @BeforeEach
    public void beforeEach() {
        taskManager.clearAll();
        taskForTest = new Task("TaskForTest", "describe", Status.NEW);
    }

    @Test
    public void checkSubtasksEpic() {
        //init + work
        int subtaskId = 5;
        Epic epic = new Epic("name", "", Status.NEW);
        for (int i = 0; i < 5; i++) {
            Subtask subtask = new Subtask("name", "", Status.NEW, epic);
            taskManager.addTask(subtask);
        }
        taskManager.addTask(epic);

        //res
        Subtask randomSubtask = (Subtask) taskManager.getTask(subtaskId);
        Epic epicLink = randomSubtask.getEpic();

        Assertions.assertEquals(epicLink, epic);
    }

    @Test
    public void checkEpicStatus() {

    }

    // All the methods
    @Test
    public void testAddTaskStandart() {
        Task[] expected = new Task[10];

        for (int i = 0; i < 5; i++) {
            Task task = new Task("name" + i, "desc" + i, Status.NEW);
            taskManager.addTask(task);
            expected[i] = task;
        }
        for (int i = 0; i < 5; i++) {
            Task task = new Task("name" + i, "desc" + i, Status.NEW);
            taskManager.addTask(i + 10,task);
            expected[i+5] = task;
        }

        Task[] res = (Task[]) taskManager.getAllTasks().toArray();
        Assertions.assertArrayEquals(expected, res);
    }

//    @Test
//    public void testAddTaskIssue() {
//
//    }

    @Test
    public void testDeleteTaskEmpty() {
        Task task = new Task("name", "desc", Status.NEW);
        Task task2 = new Task("name2", "desc2", Status.NEW);

        taskManager.addTask(task);
        taskManager.addTask(task2);
        taskManager.deleteTask(task.getId());

        Task[] expected = new Task[]{task2}; // empty
        Task[] res = taskManager.getAllTasks().toArray(new Task[0]);
        Assertions.assertArrayEquals(expected, res);
    }

    @Test
    public void testUpdateTask() {
        Task taskForUpdate = new Task("ForUpd", "", Status.NEW);
        Task newTask = taskForTest;

        taskManager.addTask(taskForUpdate);
        taskManager.updateTask(taskForUpdate.getId(), newTask);

        Task[] expected = new Task[] {newTask};
        Task[] res = taskManager.getAllTasks().toArray(new Task[0]);

        Assertions.assertArrayEquals(expected, res);
    }

    @Test
    public void testGetHistory() {
        Task task1 = new Task("name", "desc", Status.NEW);
        Task task2 = new Task("name", "desc", Status.NEW);
        Task task3 = new Task("name", "desc", Status.NEW);

        task1.setId(1);
        task2.setId(2);
        task3.setId(3);


        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.getTask(2);
        taskManager.getTask(3);
        taskManager.getTask(1);

        Integer[] expected = new Integer[] {2, 3, 1};
        Integer[] res = (Integer[]) taskManager.history().toArray();

        Assertions.assertArrayEquals(expected, res);
    }

}
