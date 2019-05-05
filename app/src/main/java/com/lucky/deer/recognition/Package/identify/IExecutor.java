package com.lucky.deer.recognition.Package.identify;

import com.lucky.model.response.ResponseData;

import java.io.File;
import java.lang.reflect.Type;

import io.reactivex.Observable;

public interface IExecutor {


    <T> Observable<ResponseData<T>> executor(String idCardSide, String obtainName, String filePath, Type type);

    <T> Observable executor(String idCardSide, String obtainName, File file, Type type);

    <T> Observable<ResponseData<T>> executor(String obtainName, String filePath, Type type);

    <T> Observable<ResponseData<T>> executor(String obtainName, File file, Type type);

}
