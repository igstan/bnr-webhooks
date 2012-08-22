<#-- @ftlvariable name="" type="ro.bnr.hooks.views.HooksView" -->
<html>
<style type="text/css">
  body {
    padding: 20px;
  }

  h3 {
    font-weight: normal;
  }
</style>
<body>

<form action="/hooks/new" method="post">
  <label for="hook">Hook:</label>
  <input type="text" name="hook" id="hook" style="width: 50%"/>
  <button type="submit">Send</button>
</form>

<h3>registered hooks</h3>
<ul>
<#list hooks?values as hook>
  <li>${hook}</li>
</#list>
</ul>

</body>
</html>
