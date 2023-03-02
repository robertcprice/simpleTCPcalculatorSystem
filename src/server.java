import java.io.*;
import java.net.*;
import java.util.Vector;

public class server {

    public static void main(String[] argv) throws Exception{
        
        String clientSentence, resultString;
        int result;
        boolean termCheck = false;

        ServerSocket welcomeSocket = new ServerSocket(Integer.valueOf(argv[0]));
                  
        
        while(true){
                
            Socket connectionSocket = welcomeSocket.accept();
           
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
 
            System.out.println("get: connection from ... " + connectionSocket.getRemoteSocketAddress().toString());
            outToClient.writeBytes("hello!\n");
           
            boolean connected = true;
            while(connected){

                clientSentence = inFromClient.readLine();
                System.out.print("get: " + clientSentence + ", ");

                Vector<String> tokens = new Vector<String>(3);
                
                String hold = "";
                clientSentence += " ";

                for(int i = 0; i  < clientSentence.length(); i++){

                        char c = clientSentence.charAt(i);

                        if(Character.isWhitespace(c)){

                            tokens.add(hold);
                            hold = "";

                        } else{

                            hold += c;

                        }

                }

                if(!tokens.get(0).equalsIgnoreCase("terminate") && !tokens.get(0).equalsIgnoreCase("bye") && !tokens.get(0).equalsIgnoreCase("add") && !tokens.get(0).equalsIgnoreCase("subtract") && !tokens.get(0).equalsIgnoreCase("multiply")){

                    System.out.print("return: " + "-1" + '\n');
                    outToClient.writeBytes("-1\n");
                    continue;

                } else 
                if(tokens.size() < 3 && !tokens.get(0).equalsIgnoreCase("bye") && !tokens.get(0).equalsIgnoreCase("terminate")){

                    System.out.print("return: " + "-2" + '\n');
                    outToClient.writeBytes("-2\n");
                    continue;

                } else
                if(tokens.size() > 5){

                    System.out.print("return: " + "-3" + '\n');
                    outToClient.writeBytes("-3\n");
                    continue;

                } else if(tokens.get(0).equals("bye") || tokens.get(0).equals("terminate")){

                    System.out.print("return: " + -5 + '\n');
                    outToClient.writeBytes("-5\n");

                    connected = false;
                    if(tokens.get(0).equals("terminate")){

                        termCheck = true; 
                        break;

                    }
                    continue;

                }else if(tokens.get(0).equals("add")){

                    result = 0;
                    boolean checkBroke = false;
                    for(int c = 1; c < tokens.size(); c++){

                        try{

                            result += Integer.parseInt(tokens.get(c));

                        } catch(NumberFormatException e){

                            System.out.print("return: " + "-4" + '\n');
                            outToClient.writeBytes("-4\n");
                            checkBroke = true;
                            break;

                        }

                    }

                    if(checkBroke){

                        checkBroke = false;
                        continue;

                    } else{

                        System.out.print("return: " + result + '\n');
                        outToClient.writeBytes(String.valueOf(result) + "\n");

                    }

                } else if(tokens.get(0).equals("subtract")){
           
                    boolean checkBroke = false;
                    
                    try{

                        
                        result = Integer.parseInt(tokens.get(1));
                        
                    } catch(NumberFormatException e){

                        System.out.print("return: " + "-4" + '\n');
                        outToClient.writeBytes("-4\n");
                        
                        checkBroke = true;
                        continue;

                    }

                    
                    for(int c = 2; c < tokens.size(); c++){
                        
                        try{

                            result -= Integer.parseInt(tokens.get(c));
                        } catch(NumberFormatException e){

                            outToClient.writeBytes("-4\n");
                            checkBroke = true;
                            break;

                        }
                    }

                    if(checkBroke){

                        checkBroke = false;
                        continue;

                    } else{

                        System.out.print("return: " + result + '\n');
                        outToClient.writeBytes(String.valueOf(result) + '\n');

                    }


                }else if(tokens.get(0).equals("multiply")){

                    result = 1;
                    boolean checkBroke = false;
                    for(int c = 1; c < tokens.size(); c++){

                        try{

                            result *= Integer.parseInt(tokens.get(c));

                        } catch(NumberFormatException e){

                            outToClient.writeBytes("-4\n");
                            checkBroke = true;
                            break;

                        }

                    }

                    if(checkBroke){

                        checkBroke = false;
                        continue;

                    } else{

                        System.out.print("return: " + result + '\n');
                        outToClient.writeBytes(String.valueOf(result)  + '\n');

                    }


                }

                
                
            }

            if(termCheck){

                break;

            }

        }

    }

}
