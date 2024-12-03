package tasktracker.taskmanager;

import tasktracker.config.Config;
import tasktracker.model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    List<Integer> history;

    public InMemoryHistoryManager() {
        history = new LinkedList<>();
    }

    @Override
    public void addToHistory(int id) {
        if (history.size() >= Config.HISTORY_MAX_LEN) {
            history.removeLast();
        }
        history.addFirst(id);
    }

    @Override
    public List<Integer> getHistory() {
        return history;
    }

    @Override
    public void clearHistory() {
        history.clear();
    }

    @Override
    public void removeFromHistory(int id) {
        try {
            history.remove(id);
        } catch (Throwable throwable) {
         //nothing
        }
    }


}
