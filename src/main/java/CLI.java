import java.io.IOException;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
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

	// variables

	private String[] args = null;
	private Options options = new Options();
	private static final Logger LOGGER = Logger.getLogger(CLI.class.getName());

	public CLI(String args[]) throws SecurityException, IOException {

		// to add logging.properties file......

		LogManager logMan = LogManager.getLogManager();
		logMan.readConfiguration(Thread.currentThread().getClass()
				.getResourceAsStream("/logging.properties"));

		// LOGGER.setLevel(Level.ALL);// filtering
		// LOGGER.info("info message from " + CLI.class.getName());

		// adding handlers to logrecord
		// LOGGER.addHandler(new FileHandler());

		// filtering the logs
		// LOGGER.setFilter(new Filter() {
		//
		// public boolean isLoggable(LogRecord arg0) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		// });

		this.args = args;

		options.addOption(OptionBuilder.withArgName("param1> <param2")
				.withValueSeparator(' ').hasArgs(2).create('s'));
		options.addOption(OptionBuilder.withArgName("param1> <param2")
				.withValueSeparator(' ').hasArgs(2).create('a'));
		options.addOption(OptionBuilder.withArgName("param1> <param2")
				.withValueSeparator(' ').hasArgs(2).create('d'));
		options.addOption(OptionBuilder.withArgName("param1> <param2")
				.withValueSeparator(' ').hasArgs(2).create('m'));

		options.addOption("h", "help", false, "helppp!!!");
		options.addOption("t", "time", false, "time please..");

	}

	public void parse() {

		LOGGER.entering(getClass().getName(), "parse()");
		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = null;

		try {
			commandLine = parser.parse(options, args);

			if (commandLine.hasOption("a")) {
				LOGGER.log(Level.INFO, "Using cli argument -a=");

				String[] searchArgs = commandLine.getOptionValues('a');
				add(Integer.parseInt(searchArgs[0]),
						Integer.parseInt(searchArgs[1]));
			}
			if (commandLine.hasOption("s")) {
				LOGGER.log(Level.INFO, "Using cli argument -=");

				String[] searchArgs = commandLine.getOptionValues('s');
				sub(Integer.parseInt(searchArgs[0]),
						Integer.parseInt(searchArgs[1]));
			}
			if (commandLine.hasOption("d")) {
				LOGGER.log(Level.INFO, "Using cli argument -d=");

				String[] searchArgs = commandLine.getOptionValues('d');
				div(Integer.parseInt(searchArgs[0]),
						Integer.parseInt(searchArgs[1]));
			}
			if (commandLine.hasOption("m")) {
				LOGGER.log(Level.INFO, "Using cli argument -m=");

				String[] searchArgs = commandLine.getOptionValues('m');
				mul(Integer.parseInt(searchArgs[0]),
						Integer.parseInt(searchArgs[1]));
			}
			if (commandLine.hasOption("h")) {
				LOGGER.log(Level.INFO, "Using cli argument -h=");

				help();
			}
			if (commandLine.hasOption("t")) {
				LOGGER.log(Level.INFO, "Using cli argument -t=");

				time();
			}

			LOGGER.exiting(getClass().getName(), "parse()");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Failed to parse comand line properties",
					e);

		}

	}

	private void time() {
		// TODO Auto-generated method stub

		LOGGER.entering(getClass().getName(), "time()");
		System.out.println("time...." + new Date());
		LOGGER.exiting(getClass().getName(), "time()");
	}

	private void help() {

		LOGGER.entering(getClass().getName(), "help()");
		System.out.println("helpping out....wait :D");
		LOGGER.exiting(getClass().getName(), "help()");
	}

	private void mul(int i, int j) {

		LOGGER.entering(getClass().getName(), "mul()");
		System.out.println("Answer  " + (i * j));
		LOGGER.exiting(getClass().getName(), "mul()");

	}

	private void div(int i, int j) {

		LOGGER.entering(getClass().getName(), "div()");
		if (j != 0) {
			System.out.println("Answer  " + i / j);
		} else {
			LOGGER.log(Level.SEVERE, "Denominater is 0, try changing paramter");
			System.out.println("cant divide by 0");
		}

		LOGGER.exiting(getClass().getName(), "div()");

	}

	private void sub(int i, int j) {

		LOGGER.entering(getClass().getName(), "sub()");
		System.out.println("Answer  " + (i - j));
		LOGGER.exiting(getClass().getName(), "sub()");

	}

	private void add(int i, int j) {

		LOGGER.entering(getClass().getName(), "add()");
		System.out.println("Answer  " + (i + j));
		LOGGER.exiting(getClass().getName(), "add()");

	}

}
