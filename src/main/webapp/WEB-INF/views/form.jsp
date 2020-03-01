<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>파일 업로드</h1>
<form action="/upload" method="post" enctype="multipart/form-data">
    <input type="file" value="파일 선택" name="file"/>
    <input type="submit" value="업로드"/>
</form>