import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader ;
import java.io.InputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

class SSHWorker implements Runnable{

    ConcurrentLinkedQueue<String> nodes;
    String cassandra_path;
    String output_path;
    String cassandra_data_path;

    public SSHWorker(ConcurrentLinkedQueue<String> nodes,String cassandra_path,String output_path,String cassandra_data_path){
        this.nodes=nodes;
	this.cassandra_path=cassandra_path;
	this.output_path=output_path;
	this.cassandra_data_path=cassandra_data_path;
    }

    public void run(){
	String nodename=nodes.poll();
        ProcessBuilder pb = new ProcessBuilder("ssh",
                                               nodename,
                                               "hostname;",
					       cassandra_path+"./flush2sstables LogData User;",
					       cassandra_path+"./alljson "+cassandra_data_path +" "+output_path+"LogData_User."+nodename
					       );
        pb.redirectErrorStream(true);
        try{
            Process process = pb.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = reader.readLine())!= null) {
                System.out.println(nodename+"  "+line);
            }
            process.waitFor();
        }
        catch(IOException e){
        }
        catch(InterruptedException s){
        }


    }
}