public class NewClass {

    
    public ExampleClass exampleMethod(){
        object = new ExampleClass();
        return object;
    }

    public void newMethod(){

        exampleMethod().method();
    }


    ExampleClass object;

    //creates a new object and returns it
    public ExampleClass createObject(){ 
        ExampleClass object = new ExampleClass();
        return object;
        
    }
    //sets newObject to the object returned in createObject()
    ExampleClass newObject = createObject();

    //returns the object. The object must be declared somewhere to avoid an error
    public ExampleClass getObject(){
        return object;
    }


    public void test(){
        System.out.println("lol this works as well");
    }

    public NewClass(){
        System.out.println("lol this works");
    }
    
}
