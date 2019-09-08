IconWrapText
======

Use flexbox to create layout of icon with wrap text 

![image](https://github.com/murmurmuk/android-pratice/blob/master/IconWrapText/result.png)
======

1. set app:flexWrap="nowrap" in parent FlexboxLayout
2. a wrap_content TextView with maxLines="1" and ellipsize="end"
3. set app:layout_flexShrink="0" for items which should always show
4. add a Framelayout and set app:layout_flexGrow="1" as the last child
