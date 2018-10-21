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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The Class CacheImpl.
 *
 * @author Guillermo Facundo Colunga
 * @version 201806081225
 * @param <T> the generic type
 */
public class GenericCacheImpl<T> implements GenericCache<T> {

	/** The memmory table. */
	private ConcurrentMap<String, GenericCacheRecord<T>> memmoryTable;

	/**
	 * Instantiates a new cache impl.
	 */
	public GenericCacheImpl() {
		this.memmoryTable = new ConcurrentHashMap<String, GenericCacheRecord<T>>();
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.thewilly.caffeine.GenericCache#contains(java.lang.String)
	 */
	@Override
	public boolean contains( String key ) {
		return this.get( key ) != null;
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.thewilly.caffeine.GenericCache#get(java.lang.String)
	 */
	@Override
	public T get( String key ) {
		return this.memmoryTable.get( key ) != null ? this.memmoryTable.get( key ).getContent()
				: null;
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.thewilly.caffeine.GenericCache#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.memmoryTable.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.thewilly.caffeine.GenericCache#remove(java.lang.String)
	 */
	@Override
	public void remove( String key ) {
		this.memmoryTable.remove( key );
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.thewilly.caffeine.GenericCache#removeAll()
	 */
	@Override
	public void removeAll() {
		this.memmoryTable = new ConcurrentHashMap<String, GenericCacheRecord<T>>();
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.thewilly.caffeine.GenericCache#save(java.lang.String,
	 * java.lang.Object, long, java.lang.Runnable)
	 */
	@Override
	public void save( String key, T content, long ttl, Runnable onRemoveFunction ) {
		this.memmoryTable.put( key, new GenericCacheRecord<T>( content, ttl, onRemoveFunction ) );
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor( 4 );
		executor.schedule(
				new Runnable() {

					@Override
					public void run() {
						memmoryTable.remove( key );

						if (onRemoveFunction != null)
							onRemoveFunction.run();
					}

				}, ttl, TimeUnit.MILLISECONDS );
	}

	/*
	 * (non-Javadoc)
	 * @see io.github.thewilly.caffeine.GenericCache#size()
	 */
	@Override
	public long size() {
		return this.memmoryTable.size();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (GenericCacheRecord<T> cr : memmoryTable.values()) {
			sb.append( cr.toString() );
		}

		return sb.toString();
	}

}
