package bytecode;

public class Loop {
    private static int [] numbers = { 111,222,333};
    public static void main(String[]args){
        int sum = 0;
        for ( int number : numbers ) {
            sum += number;
        }
        System.out.println(sum);
    }
}