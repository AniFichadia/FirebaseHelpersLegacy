package com.aniruddhfichadia.firebasehelpers;

import android.support.annotation.NonNull;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;

/**
 * Created by Aniruddh Fichadia on 30/06/2016.
 */
public abstract class TypeChildEventListener<T>
        implements ChildEventListener {
    @NonNull
    private final FirebaseGsonSerializer firebaseSerializer;
    @NonNull
    private final Class<T>               deserializedClass;

    public TypeChildEventListener(@NonNull FirebaseGsonSerializer firebaseSerializer,
                                  @NonNull Class<T> deserializedClass) {
        this.firebaseSerializer = firebaseSerializer;
        this.deserializedClass = deserializedClass;
    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        onChildAdded(firebaseSerializer.deserialize(dataSnapshot, deserializedClass), s);
    }

    public abstract void onChildAdded(T value, String s);


    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        onChildChanged(firebaseSerializer.deserialize(dataSnapshot, deserializedClass), s);
    }

    public abstract void onChildChanged(T value, String s);


    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        onChildRemoved(firebaseSerializer.deserialize(dataSnapshot, deserializedClass));
    }

    public abstract void onChildRemoved(T value);


    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        onChildMoved(firebaseSerializer.deserialize(dataSnapshot, deserializedClass), s);
    }

    public abstract void onChildMoved(T value, String s);
}