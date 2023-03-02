import java.io.*;
import java.net.*;
public class client {
    
    public static void main(String argv[]) throws Exception{

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        
        //int port = Integer.valueOf(argv[1]);
            
        Socket clientSocket = new Socket(argv[0], Integer.valueOf(argv[1]));
        
        

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        String result;
        int resultInt;

        System.out.println("receive: " + inFromServer.readLine());
        
        while(true){
        
            inputLine = inFromUser.readLine();

            outToServer.writeBytes(inputLine + '\n');

            result = inFromServer.readLine();
            resultInt = Integer.parseInt(result);
            
            if(resultInt < 0){

                if(resultInt == -5){

                    System.out.println("receive: exit.");
                    break;

                } else if(resultInt == -4){

                    System.out.println("receive: one or more of the inputs contain(s) non-number(s).");
                    continue;

                } else if(resultInt == -3){

                    System.out.println("receive: number of inputs is more than four.");
                    continue;

                } else if(resultInt == -2){

                    System.out.println("receive: number of inputs is less than two.");
                    continue;

                } else if(resultInt == -1){

                    System.out.println("receive: incorrect operation command.");
                    continue;

                }

            } else{

                System.out.println("receive: " + result);

            }
        }
        
        clientSocket.close();

    }

}
