package Test01;

import java.util.Scanner;

public class Test03 {

	
	
	    public static void main(String[] args){
	        Scanner s = new Scanner(System.in);
	        String votes = s.nextLine();
	        String[] A = votes.split("B");
	        String[] B = votes.split("A");
	        int a = A.length;
	        System.out.println(a);
	        System.out.println(A);
	        for(String data: A) {
	        	System.out.print(data + ",");
	        }
	        System.out.println("----------");
	        
	        int b = B.length;
	        System.out.println(b);
	        System.out.println(B);
	        for(String data: B) {
	        	System.out.print(data + " ");
	        }
	        System.out.println("-------");
	        
	        if(a > b){
	            System.out.println("A");
	        }else if(b > a){
	            System.out.println("B");
	        }else{
	            System.out.println("E");
	        }
	    }
	

}
