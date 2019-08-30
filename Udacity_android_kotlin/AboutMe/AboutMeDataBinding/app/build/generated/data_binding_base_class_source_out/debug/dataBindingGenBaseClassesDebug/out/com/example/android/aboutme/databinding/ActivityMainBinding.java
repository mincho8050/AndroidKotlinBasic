package com.example.android.aboutme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.example.android.aboutme.MyName;

public abstract class ActivityMainBinding extends ViewDataBinding {
  @NonNull
  public final ScrollView bioScroll;

  @NonNull
  public final TextView bioText;

  @NonNull
  public final Button doneButton;

  @NonNull
  public final TextView nameText;

  @NonNull
  public final EditText nicknameEdit;

  @NonNull
  public final TextView nicknameText;

  @NonNull
  public final ImageView starImage;

  @Bindable
  protected MyName mMyName;

  protected ActivityMainBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, ScrollView bioScroll, TextView bioText, Button doneButton,
      TextView nameText, EditText nicknameEdit, TextView nicknameText, ImageView starImage) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bioScroll = bioScroll;
    this.bioText = bioText;
    this.doneButton = doneButton;
    this.nameText = nameText;
    this.nicknameEdit = nicknameEdit;
    this.nicknameText = nicknameText;
    this.starImage = starImage;
  }

  public abstract void setMyName(@Nullable MyName myName);

  @Nullable
  public MyName getMyName() {
    return mMyName;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityMainBinding>inflate(inflater, com.example.android.aboutme.R.layout.activity_main, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityMainBinding>inflate(inflater, com.example.android.aboutme.R.layout.activity_main, null, false, component);
  }

  public static ActivityMainBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityMainBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityMainBinding)bind(component, view, com.example.android.aboutme.R.layout.activity_main);
  }
}
