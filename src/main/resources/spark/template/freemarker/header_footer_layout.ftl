<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SparkBlog</title>
    <link rel="stylesheet" href="/bower/bootstrap/dist/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="/bower/bootstrap-fileinput/css/fileinput.min.css" type="text/css">
    <link rel="stylesheet" href="/bower/font-awesome/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="/bower/datatables.net-bs/css/dataTables.bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="/css/landing-page.css" type="text/css">
    <link rel="stylesheet" href="/css/custom.css" type="text/css">
</head>
<body>
<div id="wrapper">
<#include 'nav.ftl'/>
<div id="content">
<#if template_name??>
    <#include template_name>
</#if>
</div>
<#include 'footer.ftl'/>
</div>
<script src="/bower/jquery/dist/jquery.min.js"></script>
<script src="/bower/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="/bower/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/bower/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="/bower/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<script src="/js/custom.js"></script>
</body>
</html>