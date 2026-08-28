package cc.hongz.ztools.script;

import cc.hongz.ztools.core.exceptions.ExceptionUtil;
import cc.hongz.ztools.core.util.StrUtil;

import javax.script.ScriptException;

/**
 * 脚本运行时异常.
 *
 * @author xiaoleilu
 */
public class ScriptRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 8247610319171014183L;

    private String fileName;
    private int lineNumber = -1;
    private int columnNumber = -1;

    /**
     * 构造.
     */
    public ScriptRuntimeException(final Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    /**
     * 构造.
     */
    public ScriptRuntimeException(final String message) {
        super(message);
    }

    /**
     * 构造.
     */
    public ScriptRuntimeException(final String messageTemplate, final Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    /**
     * 构造.
     */
    public ScriptRuntimeException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    /**
     * 构造.
     */
    public ScriptRuntimeException(final String message, final Throwable throwable,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    /**
     * 构造.
     */
    public ScriptRuntimeException(final Throwable throwable, final String messageTemplate, final Object... params) {
        super(StrUtil.format(messageTemplate, params), throwable);
    }

    /**
     * Creates a {@code ScriptException} with message, filename and linenumber to be used in error messages.
     *
     * @param message    The string to use in the message
     * @param fileName   The file or resource name describing the location of a script error causing the
 *                   {@code ScriptException} to be thrown.
     * @param lineNumber A line number describing the location of a script error causing the
 *                   {@code ScriptException} to be thrown.
     */
    public ScriptRuntimeException(final String message, final String fileName, final int lineNumber) {
        super(message);
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    /**
     * {@code ScriptException} constructor specifying message, filename, line number and column number.
     *
     * @param message      The message.
     * @param fileName     The filename
     * @param lineNumber   the line number.
     * @param columnNumber the column number.
     */
    public ScriptRuntimeException(final String message, final String fileName,
            final int lineNumber, final int columnNumber) {
        super(message);
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    /**
     * 构造.
     */
    public ScriptRuntimeException(final ScriptException e) {
        super(e);
        this.fileName = e.getFileName();
        this.lineNumber = e.getLineNumber();
        this.columnNumber = e.getColumnNumber();
    }

    /**
     * Returns a message containing the String passed to a constructor as well as line and column numbers
 * and filename if any of these are known.
     *
     * @return The error message.
     */
    @Override
    public String getMessage() {
        StringBuilder ret = new StringBuilder().append(super.getMessage());
        if (fileName != null) {
            ret.append(" in ").append(fileName);
            if (lineNumber != -1) {
                ret.append(" at line number ").append(lineNumber);
            }

            if (columnNumber != -1) {
                ret.append(" at column number ").append(columnNumber);
            }
        }

        return ret.toString();
    }

    /**
     * Get the line number on which an error occurred.
     *
     * @return The line number. Returns -1 if a line number is unavailable.
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Get the column number on which an error occurred.
     *
     * @return The column number. Returns -1 if a column number is unavailable.
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * Get the source of the script causing the error.
     *
     * @return The file name of the script or some other string describing the script source. May return
 *         some implementation-defined string such as <i>&lt;unknown&gt;</i> if a description of the
     * source is unavailable.
     */
    public String getFileName() {
        return fileName;
    }
}
