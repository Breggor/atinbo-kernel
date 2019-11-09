package com.atinbo.common.log;

import org.slf4j.Logger;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * LogUtils
 *
 * @author breggor
 */
public final class Logs {

    private static final Logs LOG_UTIL = new Logs();

    private Logs() {

    }

    /**
     * getInstance.
     *
     * @return LogUtils
     */
    public static Logs getInstance() {
        return LOG_UTIL;
    }


    /**
     * debug log.
     *
     * @param logger   logger
     * @param format   format
     * @param supplier {@linkplain Supplier}
     */
    public static void debug(final Logger logger, final String format, final Supplier<Object> supplier) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, supplier.get());
        }
    }

    /**
     * debug log.
     *
     * @param logger   logger
     * @param supplier {@linkplain Supplier}
     */
    public static void debug(final Logger logger, final Supplier<Object> supplier) {
        if (logger.isDebugEnabled()) {
            logger.debug(Objects.toString(supplier.get()));
        }
    }

    /**
     * info log.
     *
     * @param logger   logger
     * @param format   format
     * @param supplier {@linkplain Supplier}
     */
    public static void info(final Logger logger, final String format, final Supplier<Object> supplier) {
        if (logger.isInfoEnabled()) {
            logger.info(format, supplier.get());
        }
    }

    /**
     * info log.
     *
     * @param logger   logger
     * @param supplier {@linkplain Supplier}
     */
    public static void info(final Logger logger, final Supplier<Object> supplier) {
        if (logger.isInfoEnabled()) {
            logger.info(Objects.toString(supplier.get()));
        }
    }

    /**
     * error log.
     *
     * @param logger   logger
     * @param format   format
     * @param supplier {@linkplain Supplier}
     */
    public static void error(final Logger logger, final String format, final Supplier<Object> supplier) {
        if (logger.isErrorEnabled()) {
            logger.error(format, supplier.get());
        }
    }

    /**
     * error log.
     *
     * @param logger   logger
     * @param supplier {@linkplain Supplier}
     */
    public static void error(final Logger logger, final Supplier<Object> supplier) {
        if (logger.isErrorEnabled()) {
            logger.error(Objects.toString(supplier.get()));
        }
    }

    /**
     * warn log.
     *
     * @param logger   logger
     * @param format   format
     * @param supplier {@linkplain Supplier}
     */
    public static void warn(final Logger logger, final String format, final Supplier<Object> supplier) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, supplier.get());
        }
    }

    /**
     * warn log.
     *
     * @param logger   logger
     * @param supplier {@linkplain Supplier}
     */
    public static void warn(final Logger logger, final Supplier<Object> supplier) {
        if (logger.isWarnEnabled()) {
            logger.warn(Objects.toString(supplier.get()));
        }
    }
}
