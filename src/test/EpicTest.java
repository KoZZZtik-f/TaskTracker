package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasktracker.model.Epic;
import tasktracker.model.Status;
import tasktracker.model.Subtask;

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

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    // e
    @Test
    public void onlyInProgressSubtasks() {
        for (int i = 0; i < 5; i++) {
            epic.addSubtask(new Subtask("Sub" + i, "", Status.IN_PROGRESS, epic));
        }

        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }




}