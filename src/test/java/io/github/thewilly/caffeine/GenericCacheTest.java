/* 
 * MIT License
 * 
 * Copyright (c) 2018 Guillermo Facundo Colunga
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.thewilly.caffeine;

/* 
 * MIT License
 * 
 * Copyright (c) 2018 Guillermo Facundo Colunga
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

/**
 * The Class LibraryTest.
 *
 * @author Guillermo Facundo Colunga
 * @version 201806081225
 */
public class GenericCacheTest {

	/** The cache. */
	private GenericCache<String> cache;

	@Test
	public void constructorTest() {

		// Checking the the cache is not null, from the setUp method.
		assertNotEquals( null, cache );

		// Hard coding it to null
		cache = null;

		// Checking that is null and creating a cache from the constructor.
		assertEquals( null, cache );
		cache = new GenericCacheImpl<String>();

		// Checking that now the cache is not null.
		assertNotEquals( null, cache );

		// Checking some properties.
		assertEquals( 0, cache.size() );
		assertEquals( true, cache.isEmpty() );
	}

	@Test
	public void isEmptyTest() {
		assertEquals( true, cache.isEmpty() );

		cache.save( "a", "", 200, null );
		assertEquals( false, cache.isEmpty() );

		cache.remove( "a" );
		assertEquals( true, cache.isEmpty() );

		cache.save( "a", "", 200, null );
		cache.save( "b", "", 200, null );
		assertEquals( false, cache.isEmpty() );

		cache.removeAll();
		assertEquals( true, cache.isEmpty() );

		cache.save( "a", "", 200, null );
		cache.save( "b", "", 200, null );
		assertEquals( false, cache.isEmpty() );
		cache = new GenericCacheImpl<String>();
		assertEquals( true, cache.isEmpty() );
	}

	@Test
	public void removeTest() {
		for (int i = 0; i < 100; i++) {
			cache.save( Integer.toString( i ), "a", 200, null );
			assertEquals( 1 + i, cache.size() );
		}

		assertEquals( 100, cache.size() );

		for (int i = 100; i >= 0; i--) {
			cache.remove( Integer.toString( i ) );
			assertEquals( i, cache.size() );
		}

		assertEquals( 0, cache.size() );
	}

	@Test
	public void saveTest() {
		cache.save( "a", "aa", 200, null );
		assertEquals( "aa", cache.get( "a" ) );

		cache.save( "a", "ab", 200, null );
		assertEquals( "ab", cache.get( "a" ) );

		cache.save( "b", "bb", 200, null );
		assertEquals( "bb", cache.get( "b" ) );
	}

	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		this.cache = new GenericCacheImpl<String>();
	}

	@Test
	public void sizeTest() {
		assertEquals( 0, cache.size() );

		cache.save( "", "", 200, null );
		assertEquals( 1, cache.size() );

		for (int i = 0; i < 100; i++) {
			cache.save( Integer.toString( i ), "a", 200, null );
			assertEquals( 2 + i, cache.size() );
		}
		assertEquals( 101, cache.size() );
	}

	/**
	 * Test.
	 */
	@Test
	public void ttlTest() {
		cache.save( "sadf", "Hola me llamo guillermo", 200, () -> {
			System.err.println( "Goodbie" );
		} );

		assertEquals( "Hola me llamo guillermo", cache.get( "sadf" ) );

		while (cache.get( "sadf" ) != null) {
			System.out.println( "Waiting..." );
		}

		assertEquals( null, cache.get( "sadf" ) );
	}
}
