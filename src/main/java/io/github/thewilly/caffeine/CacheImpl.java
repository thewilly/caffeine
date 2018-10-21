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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The Class CacheImpl.
 *
 * @author Guillermo Facundo Colunga
 * @version 201806081225
 */
public class CacheImpl {
	
	/** The memmory table. */
	private Map<String, CacheRecord> memmoryTable;
	
	/**
	 * Instantiates a new cache impl.
	 */
	public CacheImpl() {
		this.memmoryTable = new HashMap<String, CacheRecord>();
	}
	
	/**
	 * Save.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void save(String key, Object value) {
		this.memmoryTable.put( key, new CacheRecord( value, -1, null ) );
	}
	
	/**
	 * Save.
	 *
	 * @param key the key
	 * @param value the value
	 * @param ttl the ttl
	 * @param onRemoveFunction the on remove function
	 */
	public void save(String key, Object value, long ttl, Runnable onRemoveFunction) {
		this.memmoryTable.put( key, new CacheRecord(value, ttl, onRemoveFunction ) );
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor( 4 );
		executor.schedule(
				new Runnable() {

					@Override
					public void run() {
						memmoryTable.remove( key );
						onRemoveFunction.run();
					}

				}, ttl, TimeUnit.MILLISECONDS );
	}
	
	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the object
	 */
	public Object get( String key ) {
		return this.memmoryTable.get( key ) != null ? this.memmoryTable.get( key ).getContent() : null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(CacheRecord cr : memmoryTable.values()) {
			sb.append( cr.toString() );
		}
		
		return sb.toString();
	}

}
