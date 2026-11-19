package com.probity.echawdi.config;

import java.util.Optional;
import java.util.Stack;

import com.probity.echawdi.utils.DataSourceType;

public final class DatabaseContextHolder {

    private static final ThreadLocal<Stack<DataSourceType>> ctx = new ThreadLocal<>();

    public static void setCtx(DataSourceType dataSourceType) {
        getCtx().push(dataSourceType);
    }

    public static void restoreCtx() {
        final Stack<DataSourceType> ctx = getCtx();
    	if (!ctx.isEmpty()) {
    		for(int i=0;i<=ctx.size();i++) {
    			ctx.pop();
            }
        }
    }

    public static Optional<DataSourceType> peekDataSource() {
        final Stack<DataSourceType> ctx = getCtx();

        if (ctx.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ctx.peek());
    }

    private static Stack<DataSourceType> getCtx() {
        if (ctx.get() == null) {
            ctx.set(new Stack<>());
        }
        return ctx.get();
    }
}