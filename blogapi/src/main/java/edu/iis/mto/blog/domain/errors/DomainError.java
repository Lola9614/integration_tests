package edu.iis.mto.blog.domain.errors;

public class DomainError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String USER_NOT_FOUND = "unknown user";
    public static final String POST_NOT_FOUND = "unknown post";
    public static final String SELF_LIKE = "cannot like own post";
    public static final String UNCONFIRMED_USER = "cannot like post if user has not confirmed status";
    public static final String UNCONFIRMED_USER_TRY_CREATE_POST = "cannot create user if status is not confirmed";

    public DomainError(String msg) {
        super(msg);
    }

}
