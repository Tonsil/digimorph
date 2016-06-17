package jstojava;
import java.applet.Applet;

public class MathApplet extends Applet{

	private static final long serialVersionUID = -7802537029755314335L;

	public String userName = null;
         
    public String getGreeting() {
        return "Hello " + userName;
    }
    
    public Calculator getCalculator() {
        return new Calculator();
    } 
    
    public DateHelper getDateHelper() {
        return new DateHelper();
    }
    
    public void printOut(String text) {
        System.out.println(text);
    }
}