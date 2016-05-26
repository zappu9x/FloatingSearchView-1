## FloatingSearch

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-FloatingSearch-lightgrey.svg?style=flat)](http://floatingsearch.osslab.online/) [![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html) [![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14) [![OSSLab](https://img.shields.io/badge/OSSLab-开源软件实验室-blue.svg?style=flat)](http://osslab.online/)


可以显示搜索建议和搜索历史的悬浮搜索框，你可以在基于该组件快速实现语音搜索、分类检索以及个性化设置。

<img src="./preview/preview.gif" alt="JellyRefresh" title="JellyRefresh" width="300" height="auto" align="right" vspace="52" />


## 使用方法

首先，你需要导入模块项目或者添加依赖类库：

```Gradle
dependencies {
    compile 'online.osslab:FloatingSearch:1.0.0'
}
```


#### 布局文件
```xml
<online.osslab.FloatingSearchView
            android:id="@+id/floating_search_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:dismissOnOutsideTouch="true"
            app:leftActionMode="showHamburger"
            app:searchBarMarginLeft="@dimen/search_view_inset"
            app:searchBarMarginRight="@dimen/search_view_inset"
            app:searchBarMarginTop="@dimen/search_view_inset"
            app:searchHint="搜索..."
            app:searchMenu="@menu/menu"
            app:showSearchKey="false" />
```


#### 回调函数

```java
SearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
              @Override
              public void onSearchTextChanged(String oldQuery, final String newQuery) {

                  //get suggestions based on newQuery

                  //pass them on to the search view
                  SearchView.swapSuggestions(newSuggestions);
              }
          });
```

##### **快捷操作**

```xml
   app:leftActionMode="[options]"
```

<table>
    <tr>
        <td>showHamburger</td>
        <td><img src="./preview/vf2oi.gif"/></td>
    </tr>
    <tr>
       <td>showSearch</td>
       <td><img src="./preview/vf91i.gif"/></td>
    <tr>
        <td>showHome</td>
        <td><img src="./preview/vf9cp.gif"/></td>
    </tr>
    <tr>
        <td>none</td>
        <td><img src="./preview/vf2ii.gif"/></td>
    </tr>
</table>

 - 监听 **汉堡菜单** 事件：

```java
 searchView.setOnLeftMenuClickListener(
        new FloatingSearchView.OnLeftMenuClickListener() { ...} );
```

 - 监听 **返回按钮** 事件：

```java
  searchView.setOnHomeActionClickListener(
         new FloatingSearchView.OnHomeActionClickListener() { ... });
```


##### **搜索菜单**

```xml
    app:searchMenu="@menu/menu_main"
```

设置菜单项显示方式：```app:showAsAction="[options]"```

<table>
    <tr>
        <td>never</td>
        <td>隐藏选项</td>
    </tr>
    <tr>
       <td>ifRoom</td>
       <td>满足条件显示：
       1. 搜索框未获得焦点；
       2. 搜索框有足够空间；
       </td>
    </tr>
    <tr>
        <td>always</td>
        <td>始终显示</td>
    </tr>
</table>

 - 监听 **菜单按钮** 事件：

```java
   searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
      @Override
      public void onMenuItemSelected(MenuItem item) {

      }
   });
```

<br/>


##### **搜索建议**

首先，你需要实现 `SearchSuggestion` 接口。

 - 回调函数（可选）：

```java
   searchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(IconImageView leftIcon, BodyTextView bodyText, SearchSuggestion item, int itemPosition) {

                       //here you can set some attributes for the suggestion's left icon and text. For example,
                       //you can choose your favorite image-loading library for setting the left icon's image.
            }

        });
```

<br/>

##### **个性设置**

<img src="./preview/device-2015-12-08-123103.png"/>

```xml
   <style name="SearchView">
           <item name="backgroundColor"></item>
           <item name="viewTextColor"></item>
           <item name="hintTextColor"></item>
           <item name="dividerColor"></item>
           <item name="clearBtnColor"></item>
           <item name="leftActionColor"></item>
           <item name="menuItemIconColor"></item>
           <item name="suggestionRightIconColor"></item>
           <item name="actionMenuOverflowColor"></item>
    </style>
```


## 关于作者

- [wall-e@live.cn](mailto:wall-e@live.cn)
- [开源软件实验室](http://osslab.online/)


## 许可协议

    Copyright 2015 Arlib

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


