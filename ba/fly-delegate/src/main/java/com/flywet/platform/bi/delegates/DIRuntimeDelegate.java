package com.flywet.platform.bi.delegates;

import java.util.concurrent.atomic.AtomicBoolean;

public class DIRuntimeDelegate {
	private static DIRuntimeDelegate delegate;

	private AtomicBoolean init = new AtomicBoolean(false);
}
