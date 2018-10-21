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

/**
 * The Interface Cache.
 *
 * @author Guillermo Facundo Colunga
 * @version 201806081225
 * @param <T> the generic type
 */
public interface GenericCache<T> {

	/**
	 * Contain.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean contains( String key );

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the cache record
	 */
	public T get( String key );

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty();

	/**
	 * Removes the.
	 *
	 * @param key the key
	 */
	public void remove( String key );

	/**
	 * Removes the all.
	 */
	public void removeAll();

	/**
	 * Save.
	 *
	 * @param key the key
	 * @param content the content
	 * @param ttl the ttl
	 * @param onRemoveFunction the on remove function
	 */
	public void save( String key, T content, long ttl, Runnable onRemoveFunction );

	/**
	 * Size.
	 *
	 * @return the long
	 */
	public long size();

}
