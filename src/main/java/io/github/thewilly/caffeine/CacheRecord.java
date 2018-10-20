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

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The Class CacheRecord.
 *
 * @author Guillermo Facundo Colunga
 * @version 201806081225
 */
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
final class CacheRecord {

	String key;

	/** The content. */
	private Object content;

	/** The on remove function. */
	private Runnable onRemoveFunction;

	/** The ttl. */
	private long ttl;

	/**
	 * Instantiates a new cache record.
	 *
	 * @param content the content
	 * @param ttl the ttl in ms.
	 * @param onRemoveFunction the on remove function
	 */
	protected CacheRecord( String key, Object content, long ttl, Runnable onRemoveFunction,
			CacheImpl parent ) {
		this.content = content;
		this.ttl = ttl;
		this.onRemoveFunction = onRemoveFunction;

		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor( 1 );
		executor.schedule(
				new Runnable() {

					@Override
					public void run() {
						delete( onRemoveFunction, parent );
					}

				}, ttl, TimeUnit.MILLISECONDS );
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	protected Object getContent() {
		return content;
	}

	/**
	 * Gets the on remove function.
	 *
	 * @return the onRemoveFunction
	 */
	protected Runnable getOnRemoveFunction() {
		return onRemoveFunction;
	}

	/**
	 * Gets the ttl.
	 *
	 * @return the ttl
	 */
	protected long getTtl() {
		return ttl;
	}

	private void delete( Runnable onRemoveFunction, CacheImpl parent ) {
		onRemoveFunction.run();
		parent.remove( this );
	}

	@Override
	public String toString() {
		return this.content.toString() + " " + this.ttl;
	}
}
