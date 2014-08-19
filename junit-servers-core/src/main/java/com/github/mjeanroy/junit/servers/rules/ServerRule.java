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

package com.github.mjeanroy.junit.servers.rules;

import org.junit.rules.ExternalResource;

import com.github.mjeanroy.junit.servers.servers.EmbeddedServer;

/**
 * Rule that can be used to start and stop embedded server.
 */
public class ServerRule extends ExternalResource {

	/** Embedded server that will be start and stopped. */
	private final EmbeddedServer server;

	/**
	 * Create rule.
	 *
	 * @param server Embedded server.
	 */
	public ServerRule(EmbeddedServer server) {
		this.server = server;
	}

	@Override
	protected void before() {
		start();
	}

	@Override
	protected void after() {
		stop();
	}

	/**
	 * Start embedded server.
	 *
	 * @see com.github.mjeanroy.junit.servers.servers.EmbeddedServer#start()
	 */
	public void start() {
		server.start();
	}

	/**
	 * Stop embedded server.
	 *
	 * @see com.github.mjeanroy.junit.servers.servers.EmbeddedServer#stop()
	 */
	public void stop() {
		server.stop();
	}

	/**
	 * Restart embedded server.
	 *
	 * @see com.github.mjeanroy.junit.servers.servers.EmbeddedServer#restart()
	 */
	public void restart() {
		server.restart();
	}

	/**
	 * Check if embedded server is started.
	 *
	 * @return True if embedded server is started, false otherwise.
	 * @see com.github.mjeanroy.junit.servers.servers.EmbeddedServer#isStarted()
	 */
	public boolean isStarted() {
		return server.isStarted();
	}

	/**
	 * Get port used by embedded server.
	 *
	 * @return Port.
	 * @see com.github.mjeanroy.junit.servers.servers.EmbeddedServer#getPort()
	 */
	public int getPort() {
		return server.getPort();
	}

	/**
	 * Get embedded server.
	 *
	 * @return Server.
	 */
	public EmbeddedServer getServer() {
		return server;
	}
}
