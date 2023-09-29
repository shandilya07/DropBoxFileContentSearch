package com.shandilya.hevo.service;

@FunctionalInterface
public interface DropBoxActionHandler<T> {
    T perform() throws Exception;
}