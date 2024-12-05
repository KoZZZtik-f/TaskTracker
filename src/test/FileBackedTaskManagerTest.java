package test;

import tasktracker.taskmanager.FileBackedTaskManager;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager>{

    public FileBackedTaskManagerTest() {
        super(new FileBackedTaskManager());
    }

}
