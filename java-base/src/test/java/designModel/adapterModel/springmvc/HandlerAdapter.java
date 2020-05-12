package designModel.adapterModel.springmvc;

public interface HandlerAdapter {

    boolean supports(Object handler);

    void hanler(Object handler);

}

class SimpleHandlerAdapter implements HandlerAdapter{

    @Override
    public boolean supports(Object handler) {
        return handler instanceof SimpleController;
    }

    @Override
    public void hanler(Object handler) {
        ((SimpleController)handler).doSimpleHanlder();
    }
}

class HttpHandlerAdapter implements HandlerAdapter{

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HttpController;
    }

    @Override
    public void hanler(Object handler) {
        ((HttpController)handler).doHttpHanlder();
        }
}

class AnnotationAdapter implements HandlerAdapter{

    @Override
    public boolean supports(Object handler) {
        return handler instanceof AnnotationController;
    }

    @Override
    public void hanler(Object handler) {
        ((AnnotationController)handler).doAnnotationHanlder();
    }
}
