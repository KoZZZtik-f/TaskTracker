# 🗂️ TaskTracker

**TaskTracker** — backend-приложение для управления задачами на Java. Поддерживает создание, хранение и синхронизацию задач, подзадач и эпиков. Это pet-проект, демонстрирующий архитектурные подходы к построению Java-приложений: от in-memory логики до HTTP API и сериализации.

## 📦 Структура проекта

src/
└── tasktracker
  ├── config # Конфигурация (например, ключи, настройки)
  ├── controller # Основная логика: менеджеры, HTTP, сериализация 
  ├── converter/ # JSON (де)сериализация задач 
  ├── exceptions/ # Пользовательские исключения 
  ├── model/ # Модели: Task, Epic, Subtask и т.д. 
  ├── resources/ # Ресурсы, например директории для хранения данных 
  └── util/ # Вспомогательные классы: SaveHelper, Validator


## ⚙️ Основные компоненты

### 📌 Model
- `Task`, `Epic`, `Subtask`, `Status`, `Type` — базовые сущности трекера задач.

### 🧠 Controller
- `TaskManager`, `InMemoryTaskManager`, `HistoryManager` — логика работы с задачами и историей.
- `FileBackedTaskManager` — сериализация задач в файл.
- `HTTPTaskManager`, `KVServer`, `KVTaskClient` — удалённое хранилище через Key-Value протокол.

### 🔁 Converter
- `TaskAdapter` — адаптеры для JSON сериализации через GSON.

### 🧰 Util
- `SaveHelper` — сохранение и загрузка задач.
- `Validator` — валидация входных данных и бизнес-правил.

### ⚠️ Exceptions
- `AddTaskException`, `InvalidFileException`, `ManagerSaveException` — кастомные исключения для обработки ошибок.

## 🧪 Примеры использования

**Создание задачи:**
```
Task task = new Task("Fix bug", "Fix login crash", Status.NEW, LocalDateTime.now(), Duration.ofHours(2));
taskManager.addTask(task);
Получение всех задач:


List<Task> allTasks = taskManager.getAllTasks();
Сохранение в файл:


FileBackedTaskManager manager = new FileBackedTaskManager(Paths.get("resources/data/tasks.csv"));
manager.save();
Запуск KVServer для HTTP-синхронизации:

KVServer server = new KVServer();
server.start();

💡 Возможности
Добавление, удаление, обновление задач и подзадач

Поддержка Epic-задач с вложенными Subtask

История просмотров задач

Сериализация в файл (CSV)

Передача данных через HTTP (KV-сервер)

Обработка ошибок с использованием пользовательских исключений

🛠️ Стек
Java 17

GSON (для JSON сериализации)

Java HTTP Server

JUnit (для тестов, если используется)

🚀 Планируемые улучшения
GUI/веб-клиент (возможно)

Аутентификация для HTTP API

Автоархивация задач

Расширенные тесты

🧠 Назначение проекта
Проект создаётся в образовательных и экспериментальных целях. Он демонстрирует:

архитектуру приложений на Java без фреймворков,

работу с сериализацией, файлами, HTTP и коллекциями,

реализацию кастомных менеджеров, валидаторов, адаптеров и исключений.

---
