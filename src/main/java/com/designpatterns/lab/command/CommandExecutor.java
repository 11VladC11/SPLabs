package com.designpatterns.lab.command;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class CommandExecutor {
    private final ExecutorService executorService;
    private final Map<String, Future<?>> pendingCommands = new ConcurrentHashMap<>();
    private final Map<String, Object> completedResults = new ConcurrentHashMap<>();
    private final Map<String, Exception> failedCommands = new ConcurrentHashMap<>();

    public CommandExecutor() {
        this.executorService = Executors.newFixedThreadPool(5);
    }

    public <T> T executeSync(Command<T> command) {
        return command.execute();
    }

    public <T> String executeAsync(Command<T> command) {
        String requestId = UUID.randomUUID().toString();
        
        Future<T> future = executorService.submit(() -> {
            try {
                T result = command.execute();
                completedResults.put(requestId, result);
                return result;
            } catch (Exception e) {
                failedCommands.put(requestId, e);
                throw e;
            } finally {
                pendingCommands.remove(requestId);
            }
        });
        
        pendingCommands.put(requestId, future);
        return requestId;
    }

    public CommandStatus getStatus(String requestId) {
        if (completedResults.containsKey(requestId)) {
            return new CommandStatus(requestId, "COMPLETED", completedResults.get(requestId), null);
        } else if (failedCommands.containsKey(requestId)) {
            return new CommandStatus(requestId, "FAILED", null, failedCommands.get(requestId).getMessage());
        } else if (pendingCommands.containsKey(requestId)) {
            return new CommandStatus(requestId, "PENDING", null, null);
        } else {
            return new CommandStatus(requestId, "NOT_FOUND", null, null);
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }

    public static class CommandStatus {
        private final String requestId;
        private final String status;
        private final Object result;
        private final String error;

        public CommandStatus(String requestId, String status, Object result, String error) {
            this.requestId = requestId;
            this.status = status;
            this.result = result;
            this.error = error;
        }

        public String getRequestId() {
            return requestId;
        }

        public String getStatus() {
            return status;
        }

        public Object getResult() {
            return result;
        }

        public String getError() {
            return error;
        }
    }
}