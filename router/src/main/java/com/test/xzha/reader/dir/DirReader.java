package com.test.xzha.reader.dir;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DirReader {
	private static final Logger LOG = Logger.getLogger(DirReader.class);
	private ExecutorService executor = Executors.newSingleThreadExecutor();

	/**
	 * Method for adding files and directories of given tree to given collection
	 * @param file root directory
	 * @param allFiles Collection<File> stores all files and directories of given root
	 */
	public void addTree(File file, Collection<File> allFiles) {

		File[] children = file.listFiles();
		if (children != null) {
			for (File child : children) {
				allFiles.add(child);
				addTree(child, allFiles);
			}
		}
	}

	/**
	 * Recursive method for finding files and directories in tree of directories
	 * @param dirPath root dir path
	 * @return Collection<File> all files and directories in given tree of directories
	 */
	public Collection<File> searchFiles(String dirPath) {

		Collection<File> allFiles = new ArrayList<>();
		addTree(new File(dirPath), allFiles);

		return allFiles;

	}

	/**
	 * Find all prefixes matched to phone
	 * @param dirPath root dir path
	 * @param phone String
	 * @return List<String[]> all records matched to given phone
	 * @throws IOException is thrown if there is an error with accessing files
	 */
	public List<String[]> searchPrefixes(String dirPath, String phone) throws IOException {
		List<String[]> totalMatches = new ArrayList<>();
		Collection<File> files = searchFiles(dirPath);
		files = files.stream().filter(File::isFile).collect(Collectors.toList());
		for (File file : files) {
			String[] entry = new String[3];
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = br.readLine()) != null) {
					if (line.length() > 0 && phone.startsWith(line.split("\\s+")[0])) {
						entry[0] = line.split("\\s+")[0];
						entry[1] = line.split("\\s+")[1];
						if (file.getName().contains(".")) {
							entry[2] = file.getName().split("\\.")[0];
						} else {
							entry[2] = file.getName();
						}
						totalMatches.add(entry);
					}
				}
			} catch (IOError e) {
				System.err.println("File reading error occurred.");
			}
		}
		return totalMatches;
	}

	/**
	 * Shutdown ExecutorService
	 */
	public void shutdown(){
		executor.shutdown();
	}

}
