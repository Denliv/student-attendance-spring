package student_attendance.exception.repository_exception;

public class NotFoundRepository extends RepositoryException {
    public NotFoundRepository() {
    }

    public NotFoundRepository(String message) {
        super(message);
    }

    public NotFoundRepository(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundRepository(Throwable cause) {
        super(cause);
    }

    public NotFoundRepository(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
