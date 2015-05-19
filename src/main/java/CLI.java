import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * 
 */

/**
 * @author dhruvil
 *
 */
public class CLI {

	/**
	 * @param args
	 */
	//variables
	
	private String[] args =null;
	private Options options = new Options();	
	private static final Logger log = Logger.getLogger(CLI.class.getName());

	
	public CLI(String args[]){
		
		
		
		this.args=args;
		
		options.addOption(OptionBuilder.withArgName("param1> <param2").withValueSeparator(' ').hasArgs(2).create('s'));
		options.addOption(OptionBuilder.withArgName("param1> <param2").withValueSeparator(' ').hasArgs(2).create('a'));
		options.addOption(OptionBuilder.withArgName("param1> <param2").withValueSeparator(' ').hasArgs(2).create('d'));
		options.addOption(OptionBuilder.withArgName("param1> <param2").withValueSeparator(' ').hasArgs(2).create('m'));
		//options.addOption("a","add", true, "Addition");
//		options.addOption("m","mul", true, "multiplication");
//		options.addOption("d","div", true, "division");
	//	options.addOption("s","sub", true, "Subtraction");
		options.addOption("h","help", false, "helppp!!!");
		options.addOption("t","time", false, "time please..");
		//Option add = OptionBuilder.withArgName("param1> <param2")
	//		.withValueSeparator(' ').hasArgs(2).create('t');
		
	}
	
	
	public void parse(){
		
		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = null;
		
		try {
			commandLine = parser.parse(options, args);
			
			
			if(commandLine.hasOption("a")){
				log.log(Level.INFO, "Using cli argument -v=" + commandLine.getOptionValue("a"));

				String[] searchArgs = commandLine.getOptionValues('a');
				add(Integer.parseInt(searchArgs[0]),Integer.parseInt(searchArgs[1]));				
			}
			if(commandLine.hasOption("s")){
				String[] searchArgs = commandLine.getOptionValues('s');
				sub(Integer.parseInt(searchArgs[0]),Integer.parseInt(searchArgs[1]));
			}
			if(commandLine.hasOption("d")){
				String[] searchArgs = commandLine.getOptionValues('d');
				div(Integer.parseInt(searchArgs[0]),Integer.parseInt(searchArgs[1]));
			}
			if(commandLine.hasOption("m")){
				String[] searchArgs = commandLine.getOptionValues('m');
				mul(Integer.parseInt(searchArgs[0]),Integer.parseInt(searchArgs[1]));
			}
			if(commandLine.hasOption("h")){
				help();
			}
			if(commandLine.hasOption("t")){
				time();
			}
			
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.log(Level.SEVERE, "Failed to parse comand line properties", e);

		}
			
		
	}


	private void time() {
		// TODO Auto-generated method stub
		System.out.println("time...."+ new Date());
	}


	private void help() {
		System.out.println("helpping out....wait :D");
		
	}


	private void mul(int i, int j) {
		// TODO Auto-generated method stub
		System.out.println("Answer  " + (i*j));

	}


	private void div(int i, int j) {
		// TODO Auto-generated method stub
		if(j!=0)
		System.out.println("Answer  " + i/j);
		else
			System.out.println("cant divide by 0");
	}


	private void sub(int i, int j) {
		// TODO Auto-generated method stub
		System.out.println("Answer  " + (i-j));

	}


	private void add(int i, int j) {
		System.out.println("Answer  " + (i+j));
		
		
	}
	

}
