package designModel.adapterModel.springmvc;

public interface Controller {

}

class HttpController implements Controller{
    public void doHttpHanlder(){
        System.out.println("Http......");
    }
}

class SimpleController implements Controller{
    public void doSimpleHanlder(){
        System.out.println("Simple......");
    }
}

class AnnotationController implements Controller{
    public void doAnnotationHanlder(){
        System.out.println("Annotation......");
    }
}
