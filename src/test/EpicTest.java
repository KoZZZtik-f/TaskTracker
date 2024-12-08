package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasktracker.model.Epic;
import tasktracker.model.Status;
import tasktracker.model.Subtask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    Epic epic;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("Name", "Description", Status.NEW);
    }

    // Status tests
    // a
    @Test
    public void emptySubtaskList() {
        assertEquals(Status.NEW, epic.getStatus());
    }

    // b
    @Test
    public void onlyNewSubtasks() {
        for (int i = 0; i < 5; i++) {
            epic.addSubtask(new Subtask("Sub" + i, "", Status.NEW, epic));
        }

        assertEquals(Status.NEW, epic.getStatus());
    }


    // c
    @Test
    public void onlyDoneSubtasks() {
        for (int i = 0; i < 5; i++) {
            epic.addSubtask(new Subtask("Sub" + i, "", Status.DONE, epic));
        }

        assertEquals(Status.DONE, epic.getStatus());
    }

    // d
    @Test
    public void onlyNewAndDoneSubtasks() {
        for (int i = 0; i < 5; i++) {
            epic.addSubtask(new Subtask("Sub" + i, "", Status.NEW, epic));
        }
        for (int i = 5; i < 10; i++) {
            epic.addSubtask(new Subtask("Sub" + i, "", Status.DONE, epic));
        }

        assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус должен быть IN_PROGRESS");
    }

    // e
    @Test
    public void onlyInProgressSubtasks() {
        for (int i = 0; i < 5; i++) {
            epic.addSubtask(new Subtask("Sub" + i, "", Status.IN_PROGRESS, epic));
        }

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    // private void updateEpicEndTime()
    @Test
    public void shouldReturnCorrectEpicEndTimeWithSubtasks() {
        var epicStartTime = LocalDateTime.of(2000, Month.DECEMBER, 1, 0, 0);
        epic.setStartTime(epicStartTime);

        Subtask subtask1 = new Subtask("sub1", "desc1", Status.NEW, epic);
        Subtask subtask2 = new Subtask("sub2", "desc2", Status.NEW, epic);
        Subtask subtask3 = new Subtask("sub3", "desc3", Status.NEW, epic);

        subtask1.setDuration(Duration.ofHours(2));
        subtask2.setDuration(Duration.ofDays(2));
        subtask3.setDuration(Duration.ofMinutes(2));

        epic.addSubtask(subtask1);
        epic.addSubtask(subtask2);
        epic.addSubtask(subtask3);

        LocalDateTime res = epic.getEndTime().get();
        LocalDateTime expected = epicStartTime.plus(subtask1.getDuration())
                .plus(subtask2.getDuration())
                .plus(subtask3.getDuration());

        Assertions.assertEquals(expected, res);



    }




}