package com.example.taskManager.constants;

public final class TasksUsersMessages {

    private TasksUsersMessages(){}

    public static final String TASK_CLOSED = "Não é possível adicionar usuários a uma tarefa com status 'CLOSE'.";
    public static final String USER_NOT_HAVE_TASKS = "Esse usuário não possuí tarefas";
    public static final String TASK_NOT_HAVE_USERS = "Essa tarefa não possuí usuários";
    public static final String USER_NOT_FOUND = "Usuário não encontrado";
    public static final String TASK_NOT_FOUND = "Tarefa não encontrada";
    public static final String PERMISSION_DENIED_DELEGATE_TASK = "Apenas usuários com permissão MANAGER ou ADMIN podem delegar tarefas";


}
