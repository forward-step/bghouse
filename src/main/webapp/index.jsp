<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>test upload file</title>
</head>
<body>
<h1>html input</h1>
<form
        method="post"
        action="http://localhost:8080/admin/room/add"
        enctype="multipart/form-data"
>
    <input
            type="text"
            name="token"
            value="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoie1wiaWRcIjpudWxsLFwidXNlcm5hbWVcIjpcInN1cHlwXCIsXCJwd2RcIjpcIjEyMzEyM1wiLFwicm9sZWlkXCI6bnVsbCxcInN0YXR1c1wiOm51bGwsXCJyZWFsbmFtZVwiOm51bGwsXCJpZGNhcmRcIjpudWxsLFwicGhvbmVcIjpudWxsLFwic2FsYXJ5XCI6bnVsbH0iLCJleHAiOjE2MDY2NDg2ODUsImlhdCI6MTYwNjA0Mzg4NX0.beqN0C9LsRBvzOn6HDn8EuSADb0wT92Yd2KgQ1rpA7o"
    />
    <input type="file" name="upload" />
    <input type="submit" value="submit" />
</form>
<h1>ajax</h1>
<form id="ajax">
    <input
            type="text"
            name="token"
            value="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoie1wiaWRcIjpudWxsLFwidXNlcm5hbWVcIjpcInN1cHlwXCIsXCJwd2RcIjpcIjEyMzEyM1wiLFwicm9sZWlkXCI6bnVsbCxcInN0YXR1c1wiOm51bGwsXCJyZWFsbmFtZVwiOm51bGwsXCJpZGNhcmRcIjpudWxsLFwicGhvbmVcIjpudWxsLFwic2FsYXJ5XCI6bnVsbH0iLCJleHAiOjE2MDY2NDg2ODUsImlhdCI6MTYwNjA0Mzg4NX0.beqN0C9LsRBvzOn6HDn8EuSADb0wT92Yd2KgQ1rpA7o"
    />
    <input type="file" name="upload" />
    <input type="button" onclick="handleClick()" value="submit" />
</form>
<h1>axios</h1>
<form id="axios">
    <input
            type="text"
            name="token"
            value="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoie1wiaWRcIjpudWxsLFwidXNlcm5hbWVcIjpcInN1cHlwXCIsXCJwd2RcIjpcIjEyMzEyM1wiLFwicm9sZWlkXCI6bnVsbCxcInN0YXR1c1wiOm51bGwsXCJyZWFsbmFtZVwiOm51bGwsXCJpZGNhcmRcIjpudWxsLFwicGhvbmVcIjpudWxsLFwic2FsYXJ5XCI6bnVsbH0iLCJleHAiOjE2MDY2NDg2ODUsImlhdCI6MTYwNjA0Mzg4NX0.beqN0C9LsRBvzOn6HDn8EuSADb0wT92Yd2KgQ1rpA7o"
    />
    <input id="file2" type="file" name="upload" />
    <input type="button" onclick="handleClick2()" value="submit" />
</form>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios@0.21.0/dist/axios.min.js"></script>
<script>
    function handleClick(e) {
        var frm = document.getElementById('ajax');
        var fd = new FormData(frm);//快速收集表单数据

        fd.append("upload",$("input[type=file]")[1].files[0]);

        $.ajax({
            url: 'http://localhost:8080/admin/room/add',
            type: 'POST',
            data: fd,
            contentType: false,
            // 告诉jQuery不要去设置Content-Type请求头
            processData: false,
            // 告诉jQuery不要去处理发送的数据
            success: function (data) {
                console.log(data)
            }
        })
    }

    function handleClick2() {
        var frm = document.getElementById('axios');
        var fd = new FormData(frm);//快速收集表单数据

        fd.append("upload",$("#file2")[0].files[0]);

        axios.post('http://localhost:8080/admin/room/add',fd,{
        })
            .then(function (value) {
                console.log(value)
            })
    }
</script>
</body>
</html>
