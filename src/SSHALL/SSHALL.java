import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader ;
import java.io.InputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue; 

class SSHALL{

    public static void main(String args[]) {
	BufferedReader br = null;
	System.out.println("Filename : "+args[0]);
	System.out.println("Number of Nodes : "+args[1]);
	System.out.println("Path For the Scripts : "+args[2]);
	System.out.println("Output Path : "+args[3]);

	String cassandra_path=args[2];
	int num_of_nodes=Integer.valueOf(args[1]);
	String output_path=args[3];
	String cassandra_data_path=args[4];

	ConcurrentLinkedQueue<String> nodes = new ConcurrentLinkedQueue <String>();
	try{
	    br = new BufferedReader(new FileReader(args[0]));
	    while (nodes.size() != num_of_nodes) {
		nodes.add(br.readLine());
	    }
	}
	catch(IOException e){
	
	}
	SSHWorker worker= new SSHWorker(nodes,cassandra_path,output_path,cassandra_data_path);
	Thread[] t= new Thread[num_of_nodes];
	for (int c = 0; c < num_of_nodes; c++) {
	    t[c] = new Thread(worker);
	    t[c].start();
	}
    }
}