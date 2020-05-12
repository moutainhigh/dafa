package designModel.adapterModel.springmvc;

import java.util.ArrayList;
import java.util.List;

public class DispatchServlet {

   List<HandlerAdapter> handlerAdapters = new ArrayList<>();

   public DispatchServlet(){
       handlerAdapters.add(new HttpHandlerAdapter());
       handlerAdapters.add(new SimpleHandlerAdapter());
       handlerAdapters.add(new AnnotationAdapter());
   }

   public void doDispatch(){
       //AnnotationController controller = new AnnotationController();
       SimpleController controller = new SimpleController();

       HandlerAdapter adapter = getHandler(controller);
       adapter.hanler(controller);
   }

   public HandlerAdapter getHandler(Controller controller){
       for (HandlerAdapter handlerAdapter : handlerAdapters){
           if(handlerAdapter.supports(controller)){
               return handlerAdapter;
           }
       }
       return null;
   }

   public static void main(String[] args) {
           new DispatchServlet().doDispatch();
   }

}
