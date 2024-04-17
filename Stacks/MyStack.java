/*
author: Leonardo de Farias

Very basic implementation of a Stack ds from scratch.
It supports the following basic operations:
---> push an element
---> remove the top

This is an array-based implementation. An LL one is straightforward.
*/
public class MyStack<T> {

    private int cur;
    private Object stack[]; //auxilliary stack to be used.

    public MyStack() {
        cur = 0;
        stack = new Object[1000];
    }
    
    public MyStack(int MAX_SZ) {
        cur = 0;
        stack = new Object[MAX_SZ];
    }

    //basic push/add method for a stack
    public void push(T val) {
        if (cur == stack.length) {
            this.twoFold();
        }
        stack[cur++] = val;
    }

    //basic remove/pop method for a stack
    public void pop() {
        T temp = this.poll();
    }

    @SuppressWarnings("unchecked")
    public T poll() {
        if (cur < 0) {return null;}
        return (T)stack[--cur];
    }

    //have a nice visualization of the stack
    public String toString() {
        String ret = "Behold the stack! ";
        int temp = cur-1;
        while (temp >= 0) {
            ret += stack[temp--];
            ret += "\n";
        }
        ret += "___";
        return ret;
    }

    //double the size of the stack in case it get's filled
    public void twoFold() {
        Object[] newStack = new Object[stack.length*2];
        for (int i=0; i<stack.length; i++) {
            newStack[i] = stack[i];
        }
        stack = newStack;
    }

    public static void main(String[] args) {
        MyStack<String> stack = new MyStack<String>();
        String[] names = new String[] {
            "Leonardo", "Rafael", "Donatello", "Michelangelo",
            "Splinter", "April", "Shredder", "Brain"
        };
        for (String NAME : names) {
            stack.push(NAME);
        }
        System.out.println(stack);
        System.out.println(stack.poll() + " is the leader");
        stack.pop();
        stack.pop();
        System.out.println(stack);
        stack.push("Turtle1");
        stack.push("Turtle2");
        stack.push("Leonardo");
        System.out.println(stack);
    }
}