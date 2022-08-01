package com.jb.traps;

class Sup{
	 int methodOfA()
	    {
	        return (true ? null : 0);
	    }
	  void method(int i)
	    {
	        //method(int)
	    }
}
class Sub extends Sup{
	
	    void method(Integer i)
	    {
	        //method(Integer)
	    }
}

class A
{
    static int i = 1111;
 
    static
    {
        i = i-- - --i;
    }
 
    {
        i = i++ + ++i;
    }
}
 
class B extends A
{
    static
    {
        i = --i - i--;
    }
 
    {
        i = ++i + i++;
    }
}



public class SubClasses {

	public static void main(String[] args) {
		Sup a= new Sup();
		Sub b = new Sub();
		a=b;
		b=(Sub) a;
		a = new Sub();
		//b= (Sub) new Sup();
		 B g = new B();
		 Sup t= new Sup();
	     System.out.println(g.i );
		

	}

}
