package io.github.mickey.context;

/**
 * @author mickey
 * @date 2/11/21 19:50
 */
public class ContextHolder {


    private static final ThreadLocal<Context> hl = new ThreadLocal<>();

    public static Context get(){
        return hl.get();
    }

    public static  void set(Context c){
        hl.set(c);
    }

    public static void remove() {
        hl.remove();
    }
}
