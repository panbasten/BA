package com.flywet.platform.bi.pivot.model.util;

/** 
 * Base class for all JPivot exceptions that are not RuntimeExceptions. 
 * 
 * @author Richard M. Emberson
 * @since Jun 11 2007
 * @version $Id: JPivotException.java,v 1.1 2007/07/09 16:17:10 remberson Exp $
 */
public class JPivotException extends Exception {
    public JPivotException() {
        super();
    }
    public JPivotException(final String msg) {
        super(msg);
    }
    public JPivotException(final String message, final Throwable cause) {
        super(message, cause);
    }
    public JPivotException(final Throwable cause) {
        super(cause);
    }
}
