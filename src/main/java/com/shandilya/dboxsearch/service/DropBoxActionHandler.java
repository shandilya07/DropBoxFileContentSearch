package com.shandilya.dboxsearch.service;

@FunctionalInterface
public interface DropBoxActionHandler<T> {
    T perform() throws Exception;
}