package com.aniruddhfichadia.firebasehelpers;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.firebase.client.DataSnapshot;
import com.firebase.client.annotations.Nullable;
import com.firebase.client.utilities.encoding.JsonHelpers;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Jackson sucks, it's not as performant and assumes you use lower camel case naming for attributes. This is way too
 * strict and can cause deserialization issues with Firebase.
 * <p/>
 * This class wraps the serialization and deserialization of {@link DataSnapshot}'s and uses {@link Gson} to (un)
 * marshall objects
 * <p/>
 * Created by Aniruddh Fichadia on 1/05/2016.
 */
public class FirebaseGsonSerializer {
    /**
     * {@link Gson} instance use for (de)serializing your data.
     */
    private final Gson gson;

    public FirebaseGsonSerializer(@NonNull Gson gson) {
        this.gson = gson;
    }

    /**
     * Custom de-serializer that uses a similar approach to {@link DataSnapshot#getValue(Class)}, but uses {@link Gson}
     * instead.
     *
     * @param <T>
     *         Type of object to return
     * @param snapshot
     *         The {@link DataSnapshot} provided by a Firebase event callback
     * @param clazz
     *         Class of object to deserialize
     *
     * @return
     */
    @Nullable
    @SuppressWarnings("Unchecked")
    public <T> T deserialize(DataSnapshot snapshot, Class<T> clazz) {
        T deserialized = null;

        Map<String, String> value = (Map<String, String>) snapshot.getValue();

        if (value != null) {
            try {
                // JSON stringify the snapshot value using Firebase's mapper
                String jsonStr = JsonHelpers.getMapper().writeValueAsString(value);

                deserialized = gson.fromJson(jsonStr, clazz);
            } catch (JsonProcessingException e) {
                // Swallow error
            }
        }

        return deserialized;
    }


    /**
     * This is easier then it seems...
     * <p>
     * Converts an object to a {@link Map} using {@link Gson}. Other objects that are members will also be serialized to
     * a {@link Map}.
     */
    public Map serialize(Object obj) {
        return gson.fromJson(gson.toJson(obj), Map.class);
    }
}