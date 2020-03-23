

##UrClearEditText 

####描述：加带删除图标的输入框
  
``` 
 <com.fubufan.widget.textview.UrClearEditText
        android:id="@+id/edit_text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:background="@android:color/transparent"
        android:hint="请输入内容"
        android:padding="14dp"
        android:singleLine="true"
        android:textColor="#333333"
        android:textColorHint="#999999"
        app:clearInputType="phone"
        app:isAutoSpace="true"
        app:isDisplayDeleteIcon="false"
        app:textSizeDisplay="17sp"
        app:textSizeHint="14sp" />
        
  //输入类型
  app:clearInputType="phone"
  //是否自动空格，仅phone、bank_card、id_card 类型有效
  app:isAutoSpace="true"
  //是否显示删除图标
  app:isDisplayDeleteIcon="false"
  //设置显示文本字体大小 默认为14
  app:textSizeDisplay="17sp"
  //设置提示文字字体大小
  app:textSizeHint="14sp"
  
``` 
