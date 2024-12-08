package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import tasktracker.exceptions.AddTaskException;
import tasktracker.model.Status;
import tasktracker.model.Task;
import tasktracker.controller.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    public InMemoryTaskManagerTest() {
        super(new InMemoryTaskManager());
    }

    @Test
    public void shouldThrowWhenTasksIntersect() {
        Task task1 = new Task("name1", "desc1", Status.NEW);
        Task task2 = new Task("name2", "desc2", Status.NEW);

        // task2StartTime = task1StartTime + 5 min
        task1.setStartTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 0, 0));
        task2.setStartTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 0, 5));
        // durations are equal
        task1.setDuration(Duration.ofDays(2));
        task2.setDuration(Duration.ofDays(2));

        // should be intersection
        taskManager.addTask(task1);
        Assertions.assertThrows(AddTaskException.class,
                new Executable() {
                    @Override
                    public void execute() throws Throwable {
                        taskManager.addTask(task2);
                    }
                });
    }

    @Test
    public void shouldNotThrowWhenTasksNotIntersect() {
        Task task1 = new Task("name1", "desc1", Status.NEW);
        Task task2 = new Task("name2", "desc2", Status.NEW);

        // task2StartTime = task1StartTime + 20 years
        task1.setStartTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 0, 0));
        task2.setStartTime(task1.getStartTime().get().plusYears(20));
        // durations are equal
        task1.setDuration(Duration.ofDays(2));
        task2.setDuration(Duration.ofDays(2));

        // should not be intersection
        taskManager.addTask(task1);
        Assertions.assertDoesNotThrow(
                new Executable() {
                    @Override
                    public void execute() throws Throwable {
                        taskManager.addTask(task2);
                    }
                });
    }

}
