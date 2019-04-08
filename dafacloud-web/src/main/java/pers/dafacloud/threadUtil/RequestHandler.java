package pers.dafacloud.threadUtil;

public interface RequestHandler<T>  {
    public void handle(T result);
}
