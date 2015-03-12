package com.doppleMl.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.Properties;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
/**
 * This Class watching directory to any file is newly created or
 *  modified in that directory
 * @author ubuntu
 *
 */
public class FileWatch {

	/** change this as appropriate for your file system structure. */
	//public static final String USER_NAME = getUserName();
	public static final String PROPERTY_FILE_PATH = "/opt/filewatcher/config.properties";
	public static final String DESTINATION_PATH = readProperties("DESTINATION_PATH");
	public static final String ARCHIVE_PATH = readProperties("ARCHIVE_PATH");
	public static final String DIRECTORY_TO_WATCH = readProperties("LOGS_PATH");
	public static final String DATE_ENABLED = readProperties("DATE.ENABLED");
	/*watching directory to any file is newly created or
	 *  modified in that directory
	 * 
	 */
	public static void main(String[] args) throws Exception {
		// get the directory we want to watch, using the Paths singleton class
		//*********************
		System.err.println("DATE.ENABLED"+DATE_ENABLED);

		File watchPath = new File(DIRECTORY_TO_WATCH);
		File destPath = new File(DESTINATION_PATH);
		File archivePath = new File(ARCHIVE_PATH);
		if(!watchPath.isDirectory()){
			watchPath.mkdir();
		}
		if(!destPath.isDirectory()){
			destPath.mkdir();
		}
		if(!archivePath.isDirectory()){
			archivePath.mkdir();
		}

		Path toWatch = Paths.get(DIRECTORY_TO_WATCH);
		if(toWatch == null) {
			throw new UnsupportedOperationException("Directory not found");
		}

		// make a new watch service that we can register interest in
		// directories and files with.
		WatchService myWatcher = toWatch.getFileSystem().newWatchService();

		// start the file watcher thread below
		MyWatchQueueReader fileWatcher = new MyWatchQueueReader(myWatcher);
		Thread th = new Thread(fileWatcher, "FileWatcher");
		th.start();

		// register a file
		// toWatch.register(myWatcher, ENTRY_CREATE, ENTRY_MODIFY);
		toWatch.register(myWatcher, ENTRY_CREATE,ENTRY_MODIFY);
		th.join();

	}
	/**
	 * This Runnable is used to constantly attempt to take from the watch
	 * queue, and will receive all events that are registered with the
	 * fileWatcher it is associated. In this sample for simplicity we
	 * just output the kind of event and name of the file affected to
	 * standard out.
	 */
	private static class MyWatchQueueReader implements Runnable {

		/** the watchService that is passed in from above */
		private WatchService myWatcher;
		public MyWatchQueueReader(WatchService myWatcher) {
			this.myWatcher = myWatcher;
		}

		/**
		 * In order to implement a file watcher, we loop forever
		 * ensuring requesting to take the next item from the file
		 * watchers queue.
		 */
		@Override
		public void run() {
			try {
				// get the first event before looping
				WatchKey key = myWatcher.take();
				while(key != null) {
					// we have a polled event, now we traverse it and
					// receive all the states from it

					for (WatchEvent event : key.pollEvents()) {
						System.out.printf("Received %s event for file: %s\n",event.kind(), event.context() );                  
						if(event.context().toString().contains(".zip") && event.kind().equals(ENTRY_MODIFY)){
							ExtractFiles extractFiles = new ExtractFiles();
							extractFiles.ExtractAllFilesWithInputStreams(DIRECTORY_TO_WATCH+"/"+event.context(),DESTINATION_PATH,ARCHIVE_PATH);
						}else if(event.kind().equals(ENTRY_MODIFY)){

							File outFile = new File(DIRECTORY_TO_WATCH+"/"+event.context());
							//Check file is a directory
							if(outFile.isDirectory()){
								showFiles(outFile.listFiles());

							}else {
								String outFileName = null;
								if(DATE_ENABLED.equals("true")){
									FindDate findDate = new FindDate();
									outFileName = findDate.file(outFile);
									//file.renameTo(new File(outFileName));
								}else {
									outFileName = DESTINATION_PATH + System.getProperty("file.separator")+outFile.getName();
								}
								fileCopy(outFileName, outFile);
							}
						}
					}
					key.reset();
					key = myWatcher.take();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Stopping thread");
		}

		//Iterate Directory  by directory and extract files
		public static void showFiles(File[] files) {
			for (File file : files) {
				if (file.isDirectory()) {
					showFiles(file.listFiles()); // Calls same method again.
				} else {
					if(file.getName().contains(".zip")){
						ExtractFiles extractFiles = new ExtractFiles();				
						extractFiles.ExtractAllFilesWithInputStreams(file.getAbsolutePath(),DESTINATION_PATH,ARCHIVE_PATH);
					}else{
						System.err.println("In");
						String outFileName = null;
						if(DATE_ENABLED.equals("true")){
							FindDate findDate = new FindDate();
							outFileName = findDate.file(file);
							//file.renameTo(new File(outFileName));
						}else {
							outFileName =  file.getName();
						}
						fileCopy(outFileName, file);
					}

				}
			}
		}

	}
	//Get system username
	public static String getUserName(){

		return System.getProperty("user.name");
	}

	/*
	 * 	 * Read the value from property file 
	 * @return
	 */
	public static String readProperties(String path){
		Properties prop = new Properties();
		InputStream input = null;
		String result = null;
		try {
			input = new FileInputStream(PROPERTY_FILE_PATH);
			// load a properties file
			prop.load(input);

			// get the property value and print it out
			result = prop.getProperty(path);
			System.err.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method copy file form input directory to destination directory
	 * @param fileName
	 * @param file
	 */
	public static void fileCopy(String fileName,File file){
		InputStream inStream = null;
		OutputStream outStream = null;
		try{
			File outfile =new File(fileName);

			inStream = new FileInputStream(file);
			outStream = new FileOutputStream(outfile);

			byte[] buffer = new byte[1024];

			int length;
			//copy the file content in bytes 
			while ((length = inStream.read(buffer)) > 0){

				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();

			System.out.println("File is copied successful!");

		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
