package com.test.xzha.reader.dir;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class DirReader {
	private static final Logger LOG = Logger.getLogger(DirReader.class);

	/**
	 * Find all prefixes matched to phone
	 * @param dirPath root dir path
	 * @param phone String
	 * @return List<String[]> all records matched to given phone
	 * @throws IOException
	 */
	public List<String[]> searchPrefixes(String dirPath, String phone) throws IOException {

		// put your implementation here, please
		//metoda na rekurzivne ziskanie vsetkych paths -> ziskam vsetky subory na porovnavanie
		for
		//kazdy subor zoradim od najdlhsieho po najkratsi
		// v kazdom subore vratim prvy mozny vyskyt do totalMatches

		return totalMatches;
    }

    /**
     * Shutdown ExecutorService
     */
	public void shutdown(){
        executor.shutdown();
    }

}
