package com.scm.payload;

public class ResourceNotFoundException extends RuntimeException {
   

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException() {
        super("resource Not Found");
         
    }



}
