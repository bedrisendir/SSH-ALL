import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader ;
import java.io.InputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

class SSHWorker implements Runnable{

    ConcurrentLinkedQueue<String> nodes;

    public SSHWorker(ConcurrentLinkedQueue<String> nodes){
        this.nodes=nodes;
    }

    public void run(){
        ProcessBuilder pb = new ProcessBuilder("ssh",
                                               nodes.poll(),
                                               "hostname");
        pb.redirectErrorStream();
        try{
            Process process = pb.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = reader.readLine())!= null) {
                System.out.println(line);
            }
            process.waitFor();
        }
        catch(IOException e){
        }
        catch(InterruptedException s){
        }


    }
}