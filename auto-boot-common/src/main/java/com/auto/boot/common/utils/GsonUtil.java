package com.auto.boot.common.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * gson 工具类
 *
 * @author zhaohaifan
 */
@Slf4j
public class GsonUtil {

    public static Gson getParamInstance() {
        return GsonUtil.GosnParamHolder;
    }

    public static Gson getLogInstance() {
        return GsonUtil.GosnLogHolder;
    }

    public static boolean canSerialize(Object object) {
        //预处理序列化的对象
        Class cls = object.getClass();
        Field[] fields = cls.getDeclaredFields();
        try{
            for(int i=0; i<fields.length; i++){
                Field f = fields[i];
                f.setAccessible(true);
                if(f.get(object) instanceof MultipartFile){
                    return false;
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private static final Gson GosnLogHolder = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .registerTypeAdapter(String.class, new GosnStringAdapter())
            .registerTypeAdapter(byte[].class, new GosnByteAdapter())
            .create();

    private static final Gson GosnParamHolder = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();


    public static class GosnStringAdapter extends TypeAdapter<String> {
        @Override
        public void write(JsonWriter jsonWriter, String s) throws IOException {
            if (!Objects.isNull(s) && s.length() > 50) {
                jsonWriter.value(s.substring(0, 50) + "......");
            } else {
                jsonWriter.value(s);
            }
        }

        @Override
        public String read(JsonReader jsonReader) throws IOException {
            return jsonReader.nextString();
        }
    }


    public static class GosnByteAdapter extends TypeAdapter<byte[]> {
        @Override
        public void write(JsonWriter jsonWriter, byte[] s) throws IOException {
            jsonWriter.value("bytes:......");
        }

        @Override
        public byte[] read(JsonReader jsonReader) throws IOException {
            return new byte[0];
        }
    }
}
