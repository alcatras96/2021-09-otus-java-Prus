package ru.otus.homework.reflections.model;

public class TestResult {

    private String className;
    private String methodName;
    private boolean successful;
    private Throwable cause;

    public TestResult(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        String resultMessage = String.format("%s -> test method %s %s\n", className, methodName,
                successful ? "PASSED" : "FAILED");
        if (!successful) {
            resultMessage += "Cause: " + cause + "\n";
        }

        return resultMessage;
    }
}
