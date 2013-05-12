package com.plywet.platform.bi.el;

import java.text.MessageFormat;

/**
 * <p>
 * Utility class for generating parameterized messages.
 * </p>
 * 
 */

public class MessageUtil {

	/**
	 * <p>
	 * Returns a formatted message based on the provided template and a single
	 * parameter.
	 * </p>
	 * 
	 * @param pTemplate
	 *            the base message
	 * @param pArg0
	 *            parameter
	 * @return Returns a formatted message based on the provided template and a
	 *         single parameter.
	 */
	public static String getMessageWithArgs(String pTemplate, Object pArg0) {
		return MessageFormat.format(pTemplate, new Object[] { "" + pArg0 });
	}

	/**
	 * <p>
	 * Returns a formatted message based on the provided template and provided
	 * parameter.
	 * </p>
	 * 
	 * @param pTemplate
	 *            the base message
	 * @param pArg0
	 *            parameter 1
	 * @param pArg1
	 *            parameter 2
	 * @return Returns a formatted message based on the provided template and
	 *         provided parameter
	 */
	public static String getMessageWithArgs(String pTemplate, Object pArg0,
			Object pArg1) {
		return MessageFormat.format(pTemplate, new Object[] { "" + pArg0,
				"" + pArg1 });
	}

	/**
	 * <p>
	 * Returns a formatted message based on the provided template and provided
	 * parameter.
	 * </p>
	 * 
	 * @param pTemplate
	 *            the base message
	 * @param pArg0
	 *            parameter 1
	 * @param pArg1
	 *            parameter 2
	 * @param pArg2
	 *            parameter 3
	 * @return Returns a formatted message based on the provided template and
	 *         provided parameter
	 */
	public static String getMessageWithArgs(String pTemplate, Object pArg0,
			Object pArg1, Object pArg2) {
		return MessageFormat.format(pTemplate, new Object[] { "" + pArg0,
				"" + pArg1, "" + pArg2 });
	}

	/**
	 * <p>
	 * Returns a formatted message based on the provided template and provided
	 * parameter.
	 * </p>
	 * 
	 * @param pTemplate
	 *            the base message
	 * @param pArg0
	 *            parameter 1
	 * @param pArg1
	 *            parameter 2
	 * @param pArg2
	 *            parameter 3
	 * @param pArg3
	 *            parameter 4
	 * @return Returns a formatted message based on the provided template and
	 *         provided parameter
	 */
	public static String getMessageWithArgs(String pTemplate, Object pArg0,
			Object pArg1, Object pArg2, Object pArg3) {
		return MessageFormat.format(pTemplate, new Object[] { "" + pArg0,
				"" + pArg1, "" + pArg2, "" + pArg3 });
	}

	/**
	 * <p>
	 * Returns a formatted message based on the provided template and provided
	 * parameter.
	 * </p>
	 * 
	 * @param pTemplate
	 *            the base message
	 * @param pArg0
	 *            parameter 1
	 * @param pArg1
	 *            parameter 2
	 * @param pArg2
	 *            parameter 3
	 * @param pArg3
	 *            parameter 4
	 * @param pArg4
	 *            parameter 5
	 * @return Returns a formatted message based on the provided template and
	 *         provided parameter
	 */
	public static String getMessageWithArgs(String pTemplate, Object pArg0,
			Object pArg1, Object pArg2, Object pArg3, Object pArg4) {
		return MessageFormat.format(pTemplate, new Object[] { "" + pArg0,
				"" + pArg1, "" + pArg2, "" + pArg3, "" + pArg4 });
	}

	/**
	 * <p>
	 * Returns a formatted message based on the provided template and provided
	 * parameter.
	 * </p>
	 * 
	 * @param pTemplate
	 *            the base message
	 * @param pArg0
	 *            parameter 1
	 * @param pArg1
	 *            parameter 2
	 * @param pArg2
	 *            parameter 3
	 * @param pArg3
	 *            parameter 4
	 * @param pArg4
	 *            parameter 5
	 * @param pArg5
	 *            parameter 6
	 * @return Returns a formatted message based on the provided template and
	 *         provided parameter
	 */
	public static String getMessageWithArgs(String pTemplate, Object pArg0,
			Object pArg1, Object pArg2, Object pArg3, Object pArg4, Object pArg5) {
		return MessageFormat.format(pTemplate, new Object[] { "" + pArg0,
				"" + pArg1, "" + pArg2, "" + pArg3, "" + pArg4, "" + pArg5 });
	}
}
