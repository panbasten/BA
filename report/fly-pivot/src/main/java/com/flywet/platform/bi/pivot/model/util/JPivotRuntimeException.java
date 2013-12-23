package com.flywet.platform.bi.pivot.model.util;

/** 
 * Base class for all JPivot exceptions that are RuntimeExceptions. 
 * 
 * @author Richard M. Emberson
 * @since Jun 11 2007
 * @version $Id: JPivotRuntimeException.java,v 1.1 2007/07/09 16:17:10 remberson Exp $
 */
public class JPivotRuntimeException extends RuntimeException {
    public JPivotRuntimeException() {
        super();
    }
    public JPivotRuntimeException(final String msg) {
        super(msg);
    }
    public JPivotRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }
    public JPivotRuntimeException(final Throwable cause) {
        super(cause);
    }
}
