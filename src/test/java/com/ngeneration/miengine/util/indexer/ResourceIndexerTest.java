package com.ngeneration.miengine.util.indexer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ngeneration.miengine.graphics.TextureRegion;
import com.ngeneration.miengine.util.Util;

class ResourceIndexerTest {

	// do not have a context to load texture ?????
	static ResourceIndexer indexer = new ResourceIndexer();

	@BeforeAll
	static void xd() throws IOException {
		indexer.loadIndex(Util.readText(ResourceIndexerTest.class.getResourceAsStream("/indexerTest")));
	}

	@Test
	void test() {
//		System.out.println("xd");
//		TextureRegion region = indexer.get("2", TextureRegion.class);
//		assertNotNull(region);
	}

}
