package tasktracker.taskmanager;

import tasktracker.model.Task;

import java.util.List;

public interface HistoryManager {

    void addToHistory(int index);

    List<Integer> getHistory();

    void clearHistory();

    void removeFromHistory(int id);

}
