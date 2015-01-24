/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.junit.servers.client;

/**
 * Http response.
 * An http response is defined by:
 * - A status code: http return code (i.e 200, 400, 500 etc.).
 * - A response body: this is the body of the http response as textual representation.
 * - A set of headers.
 * - Duration: time to produce http response.
 */
public interface HttpResponse {

	/**
	 * Get duration of request execution in nano seconds.
	 *
	 * @return Request execution duration.
	 */
	long getRequestDuration();

	/**
	 * Get duration of request execution in milli seconds.
	 *
	 * @return Request execution duration.
	 */
	long getRequestDurationInMillis();

	/**
	 * Http status code.
	 *
	 * @return Status code.
	 */
	int status();

	/**
	 * Http response body.
	 *
	 * @return Body.
	 */
	String body();

	/**
	 * Check that given is available.
	 *
	 * @param name Header name.
	 * @return True if header is in response, false otherwise.
	 */
	boolean containsHeader(String name);

	/**
	 * Get header from http response.
	 *
	 * @param name Header name.
	 * @return Header value, null if getHeader is not in http request.
	 * @throws NullPointerException if name is null.
	 */
	HttpHeader getHeader(String name);

	/**
	 * Check that http has ETag header.
	 *
	 * @return True if http response contains ETag header, false otherwise.
	 */
	boolean hasETagHeader();

	/**
	 * Get ETag header from http response.
	 *
	 * @return ETag header.
	 */
	HttpHeader getETag();

	/**
	 * Get Content-Type header from http response.
	 *
	 * @return Content-Type header.
	 */
	HttpHeader getContentType();

	/**
	 * Get Content-Encoding header from http response.
	 *
	 * @return Content-Encoding header.
	 */
	HttpHeader getContentEncoding();

	/**
	 * Get Location header from http response.
	 *
	 * @return Location header.
	 */
	HttpHeader getLocation();

	/**
	 * Get Cache-Control header from http response.
	 *
	 * @return Cache-Control header.
	 */
	HttpHeader getCacheControl();
}
