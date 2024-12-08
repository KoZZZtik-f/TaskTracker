package test;

import tasktracker.controller.FileBackedTaskManager;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager>{

    public FileBackedTaskManagerTest() {
        super(new FileBackedTaskManager());
    }

}
