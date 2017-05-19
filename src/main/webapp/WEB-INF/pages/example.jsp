<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<script src="<c:url value="/js/jquery-3.2.1.min.js" />"></script>
<script src="<c:url value="/js/jquery-ui.min.js" />"></script>
<link href="<c:url value="/css/main.css" />" rel="stylesheet">
</head>
<body>
  <h2>Spring MVC + jQuery + Autocomplete example</h2>

  <div>
	<input type="text"  id="w-input-search" value="">
	<span>
	  <button id="button-id" type="button">Search</button>
	</span>
  </div>

  <script>
  $(document).ready(function() {

	$('#w-input-search').autocomplete({
	  serviceUrl: '${pageContext.request.contextPath}/searchCustomers',
	  paramName: "keyword",
	  delimiter: ",",
	  transformResult: function(response) {
		return {
		  //must convert json to javascript object before process
		  suggestions: $.map($.parseJSON(response), function(item) {
		      return { value: item.tagName, id: item.id };
		  })
		};
      }
    });

  });
  </script>
</body>
</html>