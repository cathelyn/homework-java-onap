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


	public void addTree(File file, Collection<File> allFiles) {

		File[] children = file.listFiles();
		if (children != null) {
			for (File child : children) {
				allFiles.add(child);
				addTree(child, allFiles);
			}
		}
	}


	public Collection<File> searchFiles(String dirPath) throws IOException {

		Collection<File> allFiles = new ArrayList<File>();
		addTree(new File(dirPath), allFiles);

		return allFiles;

	}


	/**
	 * Find all prefixes matched to phone
	 * @param dirPath root dir path
	 * @param phone String
	 * @return List<String[]> all records matched to given phone
	 * @throws IOException
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
